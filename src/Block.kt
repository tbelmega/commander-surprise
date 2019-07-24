import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class Block(
    override val x: Double,
    override val y: Double,
    override val width: Double,
    override val height: Double
): Drawable, Solid {
    override fun draw(graphics: GraphicsContext, offset: Int) {
        graphics.fill = Color.BROWN
        graphics.fillRect(x - offset, y, width, height)
    }
}