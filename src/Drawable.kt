import javafx.scene.canvas.GraphicsContext

interface Drawable {

    fun draw(graphics: GraphicsContext, offset: Int)
}