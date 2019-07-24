interface Solid {

    val x: Double
    val y: Double
    val width: Double
    val height: Double

    fun xDimension() = x..(x+width-1)

    fun yDimension() = y..(y+height-1)
}
