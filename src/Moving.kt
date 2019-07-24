interface Moving {

    var x: Double
    var y: Double
    val width: Double
    val height: Double
    var speedX: Int
    var speedY: Int

    fun move() {
        x += speedX
        y += speedY
    }

    fun collidesToTheRight(it: Solid): Boolean {
        if (speedX <= 0) return false

        val nextX = x + width + speedX
        val b = nextX in it.xDimension() && (y in it.yDimension() || (y + height -1) in it.yDimension())
        return b
    }

    fun collidesToTheLeft(it: Solid): Boolean {
        if (speedX >= 0) return false

        val nextX = x + speedX
        val b = nextX in it.xDimension() && (y in it.yDimension() || (y + height -1) in it.yDimension())
        return b
    }

    fun collidesDownwards(it: Solid): Boolean {
        val nextY = y + height + speedY
        val b = nextY in it.yDimension() && (x in it.xDimension() || x + width -1 in it.xDimension())
        return b
    }

    fun collidesUpwards(it: Solid): Boolean {
        val nextY = y + speedY
        val b = nextY in it.yDimension() && (x in it.xDimension() || x + width -1 in it.xDimension())
        return b
    }


    fun standingOnGround(obstacles: Set<Block>): Boolean {
        obstacles.forEach {
            val nextY = y + height + gravity
            val standingOnThis = nextY in it.yDimension() && (x in it.xDimension() || x + width in it.xDimension())
            if (standingOnThis) return standingOnThis
        }
        return false
    }

    companion object {
        val gravity = 3
    }
}
