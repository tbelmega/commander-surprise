import javafx.geometry.VPos
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.TextAlignment

class Game : Runnable {

    val level = Level()

    val bullets = mutableSetOf<Bullet>()
    val drawables = level.getDrawables()
    val moving = level.getMovingObjects()
    val villains = level.villains
    val hero = level.hero

    var running = true

    val inputs = mutableSetOf<Input>()

    override fun run() {
        var lastFrame = System.currentTimeMillis()

        while (running) {
            // Frame timing
            val timePassed = lastFrame - System.currentTimeMillis()

            if (timePassed < millisPerFrame)
                Thread.sleep(millisPerFrame - timePassed)
            lastFrame = System.currentTimeMillis()

            calculateFrameForGameObjects()
        }
    }

    private fun calculateFrameForGameObjects() {
        if (inputs.contains(Input.FIRE))
            spawnBullet(hero)

        bullets.forEach {
            val hitTarget = it.detectCollisionsWithVillains(villains)
            hitTarget?.let {
                drawables.remove(it)
                villains.remove(it)
                moving.remove(it)
            }
        }

        hero.bulletCooldown--
        hero.handleInputs(inputs, level.obstacles)
        hero.detectCollisions(level.obstacles)
        if (hero.detectDeadlyCollisions(villains))
            gameOver()

        villains.forEach {
            it.detectCollisions(level.obstacles)
            it.checkGround(level.obstacles)
        }

        moving.forEach { it.move() }
    }

    private fun spawnBullet(hero: Hero) {
        if (hero.bulletCooldown > 0) return

        val bullet = Bullet(
            hero.x + hero.width,
            hero.y + hero.height / 4,
            hero.signX * 6
        )
        drawables.add(bullet)
        moving.add(bullet)
        bullets.add(bullet)
        hero.bulletCooldown = 60
    }

    fun gameOver() {
        running = false
        drawables.add(object : Drawable{
            override fun draw(graphics: GraphicsContext, offset: Int) {
                graphics.fill = Color.RED
                graphics.textAlign = TextAlignment.CENTER
                graphics.textBaseline = VPos.BASELINE
                graphics.font = Font("sans", 36.0)
                graphics.fillText("Game over!", 400.0, 200.0)
                graphics.font = Font("sans", 24.0)
                graphics.fillText("Press ESCAPE key to try again.", 400.0, 300.0)
            }
        })
    }

    fun stop() {
        running = false
    }

    fun addInput(input: Input) {
        inputs.add(input)
    }

    fun removeInput(input: Input) {
        inputs.remove(input)
    }

    companion object {
        val millisPerFrame: Long = 1000 / 60
    }

}

enum class Input {
    LEFT, RIGHT, JUMP, FIRE
}
