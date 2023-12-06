import pl.bfelis.aoc2022.getInput

fun main() {
    /*
    "two1two\n" +
        "two1nine\n" +
        "eightwothree\n" +
        "abcone2threexyz\n" +
        "xtwone3four\n" +
        "4nineeightseven2\n" +
        "zoneight234\n" +
        "7pqrstsixteen\n".trim()
     */
    val input = getInput("day1.txt")!!.trim()

    // Part 1
    input.lines().sumOf {
        val digits = it.filter { c -> c.isDigit() }.toList()
        "${digits.first()}${digits.last()}".toInt()
    }.let { println(it) }

    // Part 2
    val digitsMap =
        mapOf("one" to "1", "two" to "2", "three" to "3", "four" to "4", "five" to "5", "six" to "6", "seven" to "7", "eight" to "8", "nine" to "9")
    input.lines().sumOf {
        val firstDigit = findFirstSubstring(it, (digitsMap.keys + digitsMap.values).toList()).let { str ->
            if (str.length > 1) {
                digitsMap[str]
            } else {
                str
            }
        }
        val lastDigit = findLastSubstring(it, (digitsMap.keys + digitsMap.values).toList()).let { str ->
            if (str.length > 1) {
                digitsMap[str]
            } else {
                str
            }
        }

        "$firstDigit$lastDigit".toInt()
    }.let { println(it) }
}

fun findFirstSubstring(str: String, list: List<String>): String {
    var lowestIndex = Int.MAX_VALUE
    var lowestStr = ""
    list.forEach {
        val ind = str.indexOf(it)
        if (ind in 0..<lowestIndex) {
            lowestIndex = ind
            lowestStr = it
        }
    }

    return lowestStr
}

fun findLastSubstring(str: String, list: List<String>): String {
    var highestIndex = -1
    var highestStr = ""
    list.forEach {
        while (true) {
            val ind = str.indexOf(it, highestIndex + 1)
            if (ind >= 0 && ind > highestIndex) {
                highestIndex = ind
                highestStr = it
            } else if (ind == -1 || ind == highestIndex) break
        }
    }

    return highestStr
}
