import day1.day1p1
import day1.day1p2
import day2.day2p1
import day2.day2p2

var testMode = false
fun main(args: Array<String>) {
//    day1p1()
//    day1p2()
    day2p1()
    day2p2()
}

fun getResourceAsText(path: String): String? = object {}.javaClass.getResource(path)?.readText()

