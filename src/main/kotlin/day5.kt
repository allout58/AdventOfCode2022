import java.util.*

class Wearhouse {
    val stacks = ArrayList<Stack<String>>()

    fun parseDrawing(drawing: List<String>) {
        val numStacks = drawing.last().split("\\s+".toRegex()).last().toInt()
        for (i in 0 until numStacks) stacks.add(Stack())
        if (testMode) println("numStacks $numStacks")
        val regex = "( {3}|\\[[a-zA-Z]]) ?".toRegex()
        for (i in drawing.size - 2 downTo 0) {
            for (scan in 0 until drawing[i].length step 4) {
                val chunk = regex.matchAt(drawing[i], scan)?.groupValues?.get(0)
                if (chunk?.isBlank() == false) {
                    stacks[scan / 4].add(chunk.substring(1, 2))
                }
            }
        }
    }

    fun handleInstruction(ins: String) {
        val parsed = "move (\\d+) from (\\d+) to (\\d+)".toRegex().matchEntire(ins)
        if (parsed != null) {
            val (countS, fromS, toS) = parsed.destructured
            val count = countS.toInt()
            val from = fromS.toInt() - 1
            val to = toS.toInt() - 1
            for (i in 1..count) {
                val popped = stacks[from].pop()
                stacks[to].push(popped)
            }
        }
    }

    fun handleInstructionP2(ins: String) {
        val parsed = "move (\\d+) from (\\d+) to (\\d+)".toRegex().matchEntire(ins)
        if (parsed != null) {
            val (countS, fromS, toS) = parsed.destructured
            val count = countS.toInt()
            val from = fromS.toInt() - 1
            val to = toS.toInt() - 1
            val holdingStack = Stack<String>()
            for (i in 1..count) {
                holdingStack.push(stacks[from].pop())
            }
            while (holdingStack.isNotEmpty()) {
                stacks[to].push(holdingStack.pop())
            }
        }
    }

    fun getTops(): String = stacks.joinToString("") { it.peek() }
}

private fun day5Parse() =
    getResourceAsText("/day5/${if (testMode) "test" else "input"}.txt")?.trimEnd()?.split("\\r?\\n".toRegex()).orEmpty()

fun day5p1() {
    val wearhouse = Wearhouse()
    val lines = day5Parse()
    val drawing = lines.subList(0, lines.indexOf(""))
    val instructions = lines.subList(lines.indexOf("") + 1, lines.size)
    wearhouse.parseDrawing(drawing)
    if (testMode) println(wearhouse.stacks)
    if (testMode) println()
    instructions.forEach { wearhouse.handleInstruction(it); if (testMode) println(wearhouse.stacks) }

    println()
    println("Result: ${wearhouse.getTops()}")
}

fun day5p2() {
    val wearhouse = Wearhouse()
    val lines = day5Parse()
    val drawing = lines.subList(0, lines.indexOf(""))
    val instructions = lines.subList(lines.indexOf("") + 1, lines.size)
    wearhouse.parseDrawing(drawing)
    if (testMode) println(wearhouse.stacks)
    if (testMode) println()
    instructions.forEach { wearhouse.handleInstructionP2(it); if (testMode) println(wearhouse.stacks) }

    println()
    println("Result: ${wearhouse.getTops()}")
}
