import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class Villain(
    override var x: Double,
    override var y: Double,
    override val width: Double,
    override val height: Double): Drawable, Moving, Solid {

    override var speedY = 0
    override var speedX = -1

    override fun draw(graphics: GraphicsContext, offset: Int) {
        graphics.fill = Color.ORANGE
        graphics.fillRect(x - offset, y, width, height)
    }

    fun detectCollisions(obstacles: Set<Block>) {
        obstacles.forEach{
            if (collidesDownwards(it) || collidesUpwards(it))
                speedY = 0

            if (collidesToTheRight(it) || collidesToTheLeft(it)) {
                speedX = speedX * -1
                println("Collision left/right $this $it")
            }
        }
    }

    fun checkGround(obstacles: Set<Block>) {
        if (!standingOnGround(obstacles))
            speedX = speedX * -1

    }

}
