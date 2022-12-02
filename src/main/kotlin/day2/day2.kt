package day2

import getResourceAsText
import testMode

enum class RPS(val score: Int) {
    Rock(1), Paper(2), Scissors(3);

    companion object {
        fun get(input: String): RPS? = when (input.lowercase()) {
            "a" -> Rock
            "b" -> Paper
            "c" -> Scissors
            "x" -> Rock
            "y" -> Paper
            "z" -> Scissors
            else -> null
        }
    }

    fun beats() = when (this) {
        Rock -> Scissors
        Paper -> Rock
        Scissors -> Paper
    }

    fun beatenBy() = when (this) {
        Rock -> Paper
        Paper -> Scissors
        Scissors -> Rock
    }

    fun beatsScore(other: RPS): Int = if (this == other) 3 else if (this.beats() == other) 6 else 0
}

enum class WLD {
    Win, Lose, Draw;

    companion object {
        fun get(input: String): WLD? = when (input.lowercase()) {
            "x" -> Lose
            "y" -> Draw
            "z" -> Win
            else -> null
        }
    }

    fun getRps(opponent: RPS): RPS = when (this) {
        Lose -> opponent.beats()
        Draw -> opponent
        Win -> opponent.beatenBy()
    }
}

data class Round(val mine: RPS, val theirs: RPS) {
    fun calcScore(): Int = mine.score + mine.beatsScore(theirs)
}

private fun day2parse() =
    getResourceAsText("/day2/${if (testMode) "test" else "input"}.txt")?.trim()?.split("\\r?\\n".toRegex())

fun day2p1() {
    val parsed = day2parse()?.map {
        val split = it.split(" ")
        val mine = RPS.get(split[1])
        val theirs = RPS.get(split[0])
        if (mine == null || theirs == null) {
            println("Unable to parse round: ($it)")
            null
        } else Round(mine, theirs)
    }
    if (testMode) parsed?.forEachIndexed { index, round -> println("$index: ${round?.mine}->${round?.theirs}\t:: ${round?.calcScore()}") }
    println(parsed?.filterNotNull()?.sumOf { it.calcScore() })
}

fun day2p2() {
//    println(WLD.Win.getRps(RPS.Rock).toString() + "==" + RPS.Paper)
//    println(WLD.Draw.getRps(RPS.Rock).toString() + "==" + RPS.Rock)
//    println(WLD.Lose.getRps(RPS.Rock).toString() + "==" + RPS.Scissors)
    val parsed = day2parse()?.map {
        val split = it.split(" ")
        val myStrat = WLD.get(split[1])
        val theirs = RPS.get(split[0])
        if (myStrat == null || theirs == null) {
            println("Unable to parse round: ($it)")
            null
        } else {
//            println(myStrat.toString() + ": " + theirs + " -> " + myStrat.getRps(theirs))
            Round(myStrat.getRps(theirs), theirs)
        }
    }
    if (testMode) parsed?.forEachIndexed { index, round -> println("$index: ${round?.mine}->${round?.theirs}\t:: ${round?.calcScore()}") }
    println(parsed?.filterNotNull()?.sumOf { it.calcScore() })
}
