import Moving.Companion.gravity
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class Hero(
    override var x: Double,
    override var y: Double,
    override val width: Double,
    override val height: Double
): Drawable, Moving {

    val maxJumpFrames = 60
    override var speedY = gravity
    override var speedX = 0
    var isJumpingForFrames = 0
    var signX = 1
    var bulletCooldown = 0

    override fun draw(graphics: GraphicsContext, offset: Int) {
        graphics.fill = Color.LIGHTBLUE

        val offsetHeroX = x - offset
        graphics.fillRect(offsetHeroX, y, width, height)

        val gunPosY = y + height/4
        val gunWidth = width/2
        val gunHeight = 5.0

        val offsetGunPosX = if (isFacingLeft()) offsetHeroX + width - 5.0
            else offsetHeroX - width/2 + 5.0

        graphics.fill = Color.GREY
        graphics.fillRect(offsetGunPosX,  gunPosY, gunWidth,gunHeight )
    }

    fun isFacingLeft(): Boolean {
        return signX > 0
    }

    fun detectCollisions(obstacles: Set<Block>) {
        obstacles.forEach{
            if (collidesDownwards(it) || collidesUpwards(it))
                speedY = 0

            if (collidesToTheRight(it) || collidesToTheLeft(it))
                speedX = 0
        }
    }


    fun detectDeadlyCollisions(deadlies: Set<Villain>): Boolean {
        deadlies.forEach {
            if (collidesDownwards(it)
                || collidesUpwards(it)
                || collidesToTheRight(it)
                || collidesToTheLeft(it))
                return true
        }
        return false
    }



    fun handleInputs(inputs: Set<Input>, obstacles: Set<Block>) {
        if (inputs.contains(Input.LEFT)) {
            speedX = -3
            signX = -1
        } else if (inputs.contains(Input.RIGHT))  {
            speedX = 3
            signX = 1
        } else
            speedX = 0


        if (inputs.contains(Input.JUMP) && standingOnGround(obstacles)) {
            isJumpingForFrames = 0
            speedY = -3
        } else if (isJumpingForFrames >= maxJumpFrames)
            speedY = 3
        else if (isJumpingForFrames > 40) {
            val framesSinceStoppedAscending = isJumpingForFrames - 50

            speedY = Math.min(framesSinceStoppedAscending / 4, gravity)
            isJumpingForFrames++
        } else
            isJumpingForFrames++
    }


}