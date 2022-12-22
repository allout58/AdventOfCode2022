import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

private fun day9Parse() =
    getResourceAsText("/day9/${if (testMode) "test2" else "input"}.txt")?.trimEnd()?.split("\\r?\\n".toRegex()).orEmpty()
        .map { Ins(it) }

class Ins(ins: String) {
    val dir: String
    val count: Int

    init {
        val split = ins.split(" ")
        dir = split[0]
        count = split[1].toInt()
    }

    operator fun component1() = dir
    operator fun component2() = count
}

data class Coord(val x: Int, val y: Int) {

    fun distance(other: Coord): Int =
        sqrt((this.x - other.x).toDouble().pow(2) + (this.y - other.y).toDouble().pow(2)).roundToInt()

    fun moveByDir(dir: String): Coord = when (dir.lowercase()) {
        "r" -> Coord(x + 1, y)
        "l" -> Coord(x - 1, y)
        "u" -> Coord(x, y + 1)
        "d" -> Coord(x, y - 1)
        else -> {
            println("Unknown direction '$dir'")
            this
        }
    }

    fun moveToward(other: Coord): Coord {
        if (other.x == this.x) {
            return if (other.y > this.y + 1) {
                Coord(x, y + 1)
            } else if (other.y < this.y - 1) {
                Coord(x, y - 1)
            } else {
                this
            }
        } else if (other.y == this.y) {
            return if (other.x > this.x + 1) {
                Coord(x + 1, y)
            } else if (other.x < this.x - 1) {
                Coord(x - 1, y)
            } else {
                this
            }
        }
        // Diagonals
        else {
            if (distance(other) <= 1) return this
            // TR
            val newX = if (other.x > this.x) x + 1 else if (other.x < this.x) x - 1 else x
            val newY = if (other.y > this.y) y + 1 else if (other.y < this.y) y - 1 else y
            return Coord(newX, newY)
        }
    }
}

fun day9p1() {
    val instructions = day9Parse()
    var headPos = Coord(0, 0)
    var tailPos = Coord(0, 0)
    val tailPosRecord = HashSet<Coord>()
    tailPosRecord.add(tailPos)

    if (testMode) println("Head: $headPos; Tail: $tailPos")

    for (ins in instructions) {
        val (dir, count) = ins
        if (testMode) println("== $dir $count ==")
        for (s in count downTo 1) {
            headPos = headPos.moveByDir(dir)
            tailPos = tailPos.moveToward(headPos)
            tailPosRecord.add(tailPos)
            if (testMode) println("Head: $headPos; Tail: $tailPos")
        }
    }
    println("Positions: ${tailPosRecord.size}")
}

fun day9p2() {
    val instructions = day9Parse()
    var headPos = Coord(0, 0)
    val knots = ArrayList<Coord>()
    for (i in 1..9) {
        knots.add(Coord(0, 0))
    }
    val tailPosRecord = HashSet<Coord>()
    tailPosRecord.add(headPos)

    if (testMode) println("Head: $headPos; ${knots.mapIndexed { index, coord -> "$index: $coord" }.joinToString("; ")}")

    for (ins in instructions) {
        val (dir, count) = ins
        if (testMode) println("== $dir $count ==")
        for (s in count downTo 1) {
            headPos = headPos.moveByDir(dir)
            for ((ndx, it) in knots.withIndex()) {
                knots[ndx] = if (ndx == 0) it.moveToward(headPos) else it.moveToward(knots[ndx - 1])
            }
            tailPosRecord.add(knots.last())
        }
        if(testMode) println("Head: $headPos; ${knots.mapIndexed { index, coord -> "$index: $coord" }.joinToString("; ")}")
    }
    println("Positions: ${tailPosRecord.size}")
}
