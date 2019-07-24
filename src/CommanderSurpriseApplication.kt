import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.input.KeyCode
import javafx.scene.paint.Color
import javafx.stage.Stage

class CommanderSurpriseApplication: Application() {

    var game = Game()

    // Prepare the window
    val canvas = Canvas(800.0, 600.0)
    val scene = Scene(Group(canvas), 800.0, 600.0, Color.BLACK)
    val graphics = canvas.graphicsContext2D

    val maxOffsetFacingRight = 250
    val maxOffsetFacingLeft = 550
    var currentOffset = maxOffsetFacingRight

    override fun start(primaryStage: Stage) {
        Thread(game).start()

        showWindow(primaryStage)

        renderGameGraphicsEveryFrame()

        handleInput()
    }

    private fun mapKeyToAction(key: KeyCode): Input? {
        return when (key) {
            KeyCode.LEFT -> Input.LEFT
            KeyCode.A -> Input.LEFT
            KeyCode.RIGHT -> Input.RIGHT
            KeyCode.D -> Input.RIGHT
            KeyCode.CONTROL -> Input.JUMP
            KeyCode.W -> Input.JUMP
            KeyCode.SPACE -> Input.FIRE
            else -> null
        }
    }

    private fun handleInput() {
        scene.setOnKeyPressed { event ->
            mapKeyToAction(event.code)?.let {
                game.addInput(it)
            }

            if (!game.running && event.code == KeyCode.ESCAPE) {
                game = Game()
                Thread(game).start()
            }

        }

        scene.setOnKeyReleased { event ->
            mapKeyToAction(event.code)?.let {
                game.removeInput(it)
            }
        }
    }

    private fun renderGameGraphicsEveryFrame() {
        // Re-render the game objects in every graphical frame
        val animationTimer = object : AnimationTimer() {
            override fun handle(now: Long) {
                graphics.clearRect(0.0, 0.0, scene.width, scene.height)

                val offset = calculateOffset()
                game.drawables.forEach { it.draw(graphics,  offset)}
            }
        }
        animationTimer.start()
    }

    private fun calculateOffset(): Int {
        val maxOffset = if (game.hero.isFacingLeft()) maxOffsetFacingRight else maxOffsetFacingLeft

        if (currentOffset > maxOffset) currentOffset -= 10
        else if (currentOffset < maxOffset) currentOffset += 10

        val offset = game.hero.x.toInt() - currentOffset
        return offset
    }

    private fun showWindow(primaryStage: Stage) {
        primaryStage.scene = scene
        primaryStage.title = "Commander Surprise"
        primaryStage.setOnCloseRequest { game.stop() }
        primaryStage.show()
    }

    fun main() {
        launch()
    }
}