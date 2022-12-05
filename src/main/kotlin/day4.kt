private fun day4parse() =
    getResourceAsText("/day4/${if (testMode) "test" else "input"}.txt")?.trim()?.split("\\r?\\n".toRegex()).orEmpty()
        .map {
            val match = "(\\d+)-(\\d+),(\\d+)-(\\d+)".toRegex().matchEntire(it)
            if (match != null) {
                val (s1, e1, s2, e2) = match!!.destructured
                Pair(IntRange(s1.toInt(), e1.toInt()), IntRange(s2.toInt(), e2.toInt()))
            } else null
        }.filterNotNull()

fun day4p1() {
    val contained = day4parse().filter {
        (it.first.contains(it.second.first) && it.first.contains(it.second.last)) ||
                (it.second.contains(it.first.first) && it.second.contains(it.first.last))
    }
    if (testMode) println(contained)
    println(contained.size)
}

fun day4p2() {
    val contained = day4parse().filter {
        (it.first.contains(it.second.first) || it.first.contains(it.second.last)) || it.second.contains(it.first.first) || it.second.contains(
            it.first.last
        )
    }
    if (testMode) println(contained)
    println(contained.size)
}
