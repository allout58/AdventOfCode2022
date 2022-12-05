package day3

import getResourceAsText
import testMode

class Sack(input: String) {
    val first: List<String>
    val second: List<String>
    val intersection: Set<String>

    init {
        val halfSize = input.length / 2
        val h1 = input.subSequence(0, halfSize)
        val h2 = input.subSequence(halfSize, input.length)
        first = h1.trim().split("")
        second = h2.trim().split("")
        intersection = first.filter { second.contains(it) && it != "" }.toSet()
    }
}

private fun day3Parse() =
    getResourceAsText("/day3/${if (testMode) "test" else "input"}.txt")?.trim()?.split("\\r?\\n".toRegex()).orEmpty()

private fun priority(s: Char): Int = if (s.category == CharCategory.LOWERCASE_LETTER) s.code - 'a'.code + 1 else s.code - 'A'.code + 27

fun day3p1() {
    val sacks = day3Parse().map { Sack(it) }
    if(testMode) sacks.forEachIndexed { ndx, sack -> println("Sack #$ndx: ${sack.first.joinToString("")} ${sack.second.joinToString("")} int: ${sack.intersection}") }
    val priorities = sacks.flatMap { it.intersection.map { c -> priority(c[0]) } }
    if(testMode) println(priorities)
    println("D3P1: ${priorities.sum()}")
}

fun day3p2() {
    val sacks = day3Parse().map { it.trim().split("").toSet() }
    var prioitySum = 0
    for (i in sacks.indices step 3) {
        val group = sacks.subList(i, i+3)
        val int1and2 = group[0].filter { group[1].contains(it) && it != "" }
        val finalInt = group[2].filter { int1and2.contains(it) && it != "" }
        if(testMode) println("Group ${i/3}: $finalInt")
        finalInt.forEach { prioitySum += priority(it[0]) }
    }
    println("D3P2: $prioitySum")
}
