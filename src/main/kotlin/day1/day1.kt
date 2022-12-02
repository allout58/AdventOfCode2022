package day1

import getResourceAsText
import testMode

private fun day1parseInput(): List<Int>? =
    getResourceAsText("/day1/${if (testMode) "test" else "input"}.txt")
        ?.split("(?:\\r?\\n){2}".toRegex())
        ?.map { inventory -> inventory.split("\\r?\n".toRegex()).filterNot { it == "" }.sumOf { it.toInt() } }

fun day1p1() {
    val input= day1parseInput()
    val highest = input?.max()
    println("Highest is $highest")
}

fun day1p2() {
    val input = day1parseInput()
    val top3 = input?.sortedDescending()?.subList(0, 3)
    println("Top3 Sum is ${top3?.sum()}")
}
