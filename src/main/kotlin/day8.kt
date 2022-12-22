private fun day8Parse() =
    getResourceAsText("/day8/${if (testMode) "test" else "input"}.txt")?.trimEnd()?.split("\\r?\\n".toRegex()).orEmpty()
        .map { it.split("").filter { c -> c.isNotEmpty() }.map { c -> c.toInt() } }

fun day8p1() {
    val trees = day8Parse()
    val width = trees[0].size
    val height = trees.size

    var visible = width * 2 + (height - 2) * 2 // The border

    for (h in 1 until height - 1) {
        for (w in 1 until width - 1) {
            val currentHeight = trees[h][w]
//            print("$currentHeight ")
            // North
            var isVisible = true
            for (y in 0 until h) {
                if (trees[y][w] >= currentHeight) {
                    isVisible = false
                    break
                }
            }
            if (isVisible) {
                visible++
                if (testMode) print("V ")
                continue
            }

            // South
            isVisible = true
            for (y in h + 1 until height) {
                if (trees[y][w] >= currentHeight) {
                    isVisible = false
                    break
                }
            }
            if (isVisible) {
                visible++
                if (testMode) print("V ")
                continue
            }

            // West
            isVisible = true
            for (x in 0 until w) {
                if (trees[h][x] >= currentHeight) {
                    isVisible = false
                    break
                }
            }
            if (isVisible) {
                visible++
                if (testMode) print("V ")
                continue
            }

            // East
            isVisible = true
            for (x in w + 1 until width) {
                if (trees[h][x] >= currentHeight) {
                    isVisible = false
                    break
                }
            }
            if (isVisible) {
                visible++
                if (testMode) print("V ")
                continue
            }

            if (testMode) print("I ")

        }
        if (testMode) println()
    }

    println("Visible Trees: $visible")

}

fun day8p2() {
    val trees = day8Parse()
    val width = trees[0].size
    val height = trees.size

    var highestScenic = 0

    for (h in 0 until height) {
        for (w in 0 until width) {
            val currentHeight = trees[h][w]
            // North
            var viewingDistance = 0
            for (y in h - 1 downTo 0) {
                viewingDistance++
                if (trees[y][w] >= currentHeight) {
                    break
                }
            }
            var scenicScore = viewingDistance

            // South
            viewingDistance = 0
            for (y in h + 1 until height) {
                viewingDistance++
                if (trees[y][w] >= currentHeight) {
                    break
                }
            }
            scenicScore *= viewingDistance

            // West
            viewingDistance = 0
            for (x in w - 1 downTo 0) {
                viewingDistance++
                if (trees[h][x] >= currentHeight) {
                    break
                }
            }
            scenicScore *= viewingDistance

            // East
            viewingDistance = 0
            for (x in w + 1 until width) {
                viewingDistance++
                if (trees[h][x] >= currentHeight) {
                    break
                }
            }
            scenicScore *= viewingDistance

            highestScenic = maxOf(highestScenic, scenicScore)
        }
    }

    println("Highest Scenic Score: $highestScenic")

}
