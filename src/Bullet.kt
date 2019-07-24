import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class Bullet(
    override var x: Double,
    override var y: Double,
    override var speedX: Int
) : Drawable, Moving {

    override val width: Double = 4.0
    override val height: Double = 4.0
    override var speedY: Int = 0

    override fun draw(graphics: GraphicsContext, offset: Int) {
        graphics.fill = Color.WHITE
        graphics.fillOval(x - offset, y, width, height)
    }

    fun detectCollisionsWithVillains(villains: Set<Villain>): Villain? {
        villains.forEach{
            if (collidesDownwards(it)
                || collidesUpwards(it)
                || collidesToTheRight(it)
                || collidesToTheLeft(it))
                return it
        }
        return null
    }

}
