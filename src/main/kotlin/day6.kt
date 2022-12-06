private fun day6Parse() =
    getResourceAsText("/day6/${if (testMode) "test" else "input"}.txt")?.trimEnd()?.split("\\r?\\n".toRegex()).orEmpty()

fun day6p1() {
    day6Parse().forEachIndexed { ndx, line ->
        val splitLine = line.split("")
        val window = ArrayList<String>()
        window.addAll(splitLine.subList(1, 4))
        for (i in 4 until splitLine.size) {

            window.add(splitLine[i])
            if(window.distinct().size == 4) {
                println("line #$ndx distinct at position $i [${window.joinToString("")}]")
                return@forEachIndexed
            }
            window.removeAt(0)
        }
    }
}

fun day6p2() {
    day6Parse().forEachIndexed { ndx, line ->
        val splitLine = line.split("")
        val window = ArrayList<String>()
        window.addAll(splitLine.subList(1, 14))
        for (i in 14 until splitLine.size) {

            window.add(splitLine[i])
            if(window.distinct().size == 14) {
                println("line #$ndx distinct at position $i [${window.joinToString("")}]")
                return@forEachIndexed
            }
            window.removeAt(0)
        }
    }
}
