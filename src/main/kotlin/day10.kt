private fun day10Parse() =
    getResourceAsText("/day10/${if (testMode) "test" else "input"}.txt")?.trimEnd()?.split("\\r?\\n".toRegex())
        .orEmpty()

fun day10p1() {
    var cycle = 1
    var reg = 1
    val instructions = day10Parse()
    val strengths = ArrayList<Int>()

    fun checkStrength() {
        if (listOf(20, 60, 100, 140, 180, 220).contains(cycle)) {
            if (testMode) println("Checking Strength at $cycle... $reg -> ${cycle * reg}")
            strengths.add(cycle * reg)
        }
    }

    for (ins in instructions) {
        checkStrength()

        // Execute Instruction
        if (ins == "noop") cycle++
        else {
            val arg = ins.split(" ")[1].toInt()
            cycle++
            checkStrength()
            reg += arg
            cycle++
        }
    }

    println("Sum of signals: ${strengths.sum()}")
}

fun day10p2() {
    var cycle = 1
    var reg = 1
    val instructions = day10Parse()
    val screen = ArrayList<String>()
    val currentLine = ArrayList<String>()

    fun draw() {
        val modCycle = cycle % 40
        currentLine.add(if(modCycle >= reg && modCycle < reg + 3) "#" else ".")
        if(testMode) {
            println("== Cycle $cycle ==")
            println("Current CRT Row: ${currentLine.joinToString("")}")
            print("Sprite Position: ")
            for (i in 1 until reg) print(".")
            print("###")
            for (i in reg+3 until 40) print(".")
            println()
        }
        if(modCycle == 0) {
            screen.add(currentLine.joinToString(""))
            currentLine.clear()
        }
    }


    for (ins in instructions) {
        draw()

        // Execute Instruction
        if (ins == "noop") cycle++
        else {
            val arg = ins.split(" ")[1].toInt()
            cycle++
            draw()
            reg += arg
            cycle++
        }
    }

    println("Screen:\n")
    screen.forEach { println(it) }
}
