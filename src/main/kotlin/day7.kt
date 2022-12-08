class Directory(name: String, val parent: Directory?) : FSObject(name) {
    val children = ArrayList<FSObject>()

    fun fullName(): String = if (parent != null) "${parent.fullName()}/$name" else name

    override fun objSize(): Int = children.sumOf { it.objSize() }

    override fun prettyPrint(depth: Int) {
        val depthStr = getDepthStr(depth)
        println("$depthStr- $name (dir)")
        children.sortedBy { it.name }.forEach {
            it.prettyPrint(depth + 1)
        }
    }
}

abstract class FSObject(val name: String) {
    abstract fun objSize(): Int
    abstract fun prettyPrint(depth: Int): Unit
    protected fun getDepthStr(depth: Int): String {
        var depthStr = ""
        for (i in 0 until depth) depthStr += "  "
        return depthStr
    }
}

class File(name: String, private val size: Int) : FSObject(name) {
    override fun objSize(): Int = size
    override fun prettyPrint(depth: Int) = println("${getDepthStr(depth)}- $name (file, size=$size)")
}

private fun day7Parse(): Directory {
    val root = Directory("/", null)
    var cwd = root
    val input =
        getResourceAsText("/day7/${if (testMode) "test" else "input"}.txt")?.trimEnd()?.split("\\r?\\n".toRegex())
            .orEmpty()
    // purposefully skip the first line since it's always `cd /`
    var lineNum = 1
    while (lineNum < input.size) {
        val line = input[lineNum]
        if (line.startsWith("$ cd")) {
            val name = line.substring("$ cd ".length)
            if (name == ".." && cwd.parent != null) {
                cwd = cwd.parent!!
            } else {
                val newWd = cwd.children.find { it is Directory && it.name == name }
                if (newWd == null) {
                    println("Unable to find '$name' under the current working directory ${cwd.fullName()}")
                } else {
                    cwd = newWd as Directory
                }
            }
        }
        if (line.startsWith("$ ls")) {
            do {
                lineNum++
                val lsLine = input[lineNum]
                if (lsLine.startsWith("dir ")) {
                    cwd.children.add(Directory(lsLine.substring("dir ".length), cwd))
                } else {
                    val (size, name) = lsLine.split(" ")
                    cwd.children.add(File(name, size.toInt()))
                }
            } while (lineNum + 1 < input.size && !input[lineNum + 1].startsWith("$ "))
        }
        lineNum++
    }

    return root
}


fun day7p1() {
    val root = day7Parse()

    if (testMode) root.prettyPrint(0)

    val smallDirs = ArrayList<Directory>()
    val workingDirs = ArrayList<Directory>()
    workingDirs.add(root)
    while (workingDirs.isNotEmpty()) {
        val wd = workingDirs.removeAt(0)
        if (wd.objSize() <= 100_000) smallDirs.add(wd)
        workingDirs.addAll(wd.children.filterIsInstance<Directory>())
    }
    println()
    println("Answer is: ${smallDirs.sumOf { it.objSize() }}")
}

fun day7p2() {
    val root = day7Parse()
    val totalFsSize = 70000000
    val neededFree = 30_000_000
    val rootSize = root.objSize()
    val currentFreeSpace = totalFsSize - rootSize
    val toClean = neededFree - currentFreeSpace
    println("Need $toClean bytes removed")

    if (testMode) root.prettyPrint(0)

    val deleteable = ArrayList<Directory>()
    val workingDirs = ArrayList<Directory>()
    workingDirs.add(root)
    while (workingDirs.isNotEmpty()) {
        val wd = workingDirs.removeAt(0)
        if (wd.objSize() >= toClean) deleteable.add(wd)
        workingDirs.addAll(wd.children.filterIsInstance<Directory>())
    }
    deleteable.sortBy { it.objSize() }
    println()
    println("Answer is: ${deleteable[0].objSize()}")
}
