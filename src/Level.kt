class Level {

    val levelLayout =
            """
BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB
........................................................................................................................
........................V...............................................................................................
..................BBBBBBBB..............................................................................................
........................................................................................................................
........................................................................................................................
BBBBBBBBB...............................................................................................................
........................................................................................................................
.............BB.......V.................................................................................................
....................BBBBB..............V................................................................................
..H...................................BB..............BB................................................................
.......BB......V....................................BBBB................................................................
BBBBBBBBBBBBBBBBBBBBBBB..BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB...BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB
BBBBBBBBBBBBBBBBBBBBBBB..BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB...BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB
BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB
            """.trimIndent()


    lateinit var hero: Hero
    val leftBorder = Block(-10.0, 0.0, 10.0, 600.0)

    val villains = mutableSetOf<Villain>()
    val obstacles = mutableSetOf(leftBorder)

    init {
        val rows = levelLayout.split("\n")

        for (y in 0 until rows.size) {
            for (x in 0 until rows[0].length) {
                val char: Char = rows[y][x]
                when (char) {
                    'B' -> addBlock(x,y)
                    'H' -> placeHero(x,y)
                    'V' -> addVillain(x,y)
                }
            }
            println()
        }
    }

    private fun addVillain(x: Int, y: Int) {
        villains.add(Villain(x * 30.0, y *40.0, 30.0, 40.0))
    }

    private fun placeHero(x: Int, y: Int) {
        hero = Hero(x * 30.0, y * 40.0, 30.0, 40.0)
    }

    private fun addBlock(x: Int, y: Int) {
        obstacles.add(Block(x * 30.0, y *40.0, 30.0, 40.0))
    }

    fun getMovingObjects(): MutableSet<Moving> {
        val moving = mutableSetOf<Moving>(hero)
        moving.addAll(villains)
        return moving
    }

    fun getDrawables(): MutableSet<Drawable> {
        return  (obstacles + hero + villains).toMutableSet()
    }
}