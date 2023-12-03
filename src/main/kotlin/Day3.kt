import pl.bfelis.aoc2022.getInput

fun main() {
    val input = getInput("day3.txt")!!.trim()
    val arr = input.lines().map { it.toCharArray() }.toTypedArray()

    val numRegex = Regex("([0-9]*)")

    val numMatches = collectMatches(input.lines(), numRegex)

    // PART 1
    numMatches.mapNotNull { (coord, num) ->
        if (lookFor(coord.first, coord.second, arr, ::symbolPredicate).isNotEmpty()) num else null
    }.sumOf { it.toInt() }.let { println(it) }

    // PART 2
    val gearRegex = Regex("""([\\*])""")
    val gearMatches = collectMatches(input.lines(), gearRegex)

    gearMatches.associate { (coord, _) ->
        coord to lookFor(
            coord.first,
            coord.second.first,
            arr,
            ::digitPredicate,
        ).map { (yDiff, xDiff) -> coord.first + yDiff to coord.second.first + xDiff }
            .map { (y, x) -> numMatches.first { (numCoord, _) -> numCoord.first == y && numCoord.second.contains(x) } }
            .toSet()
            .map { it.second }
    }.filterValues { it.count() == 2 }.values.map { it.map { num -> num.toInt() } }
        .sumOf { it.reduce { acc, i -> acc * i } }.let { println(it) }
}

fun collectMatches(lines: List<String>, regex: Regex): List<Pair<Pair<Int, IntRange>, String>> =
    lines.mapIndexed { i, line ->
        i to regex.findAll(line).associate { match -> match.range to match.value }.filter { it.value.isNotEmpty() }
    }.flatMap { (y, results) ->
        results.map { (range, result) ->
            (y to range) to result
        }
    }

val dirs = listOf(-1 to -1, -1 to 0, -1 to 1, 0 to -1, 0 to 1, 1 to -1, 1 to 0, 1 to 1)

typealias LookPredicate = (
    x: Int,
    y: Int,
    arr: Array<CharArray>,
) -> Boolean

fun symbolPredicate(x: Int, y: Int, arr: Array<CharArray>): Boolean = !arr[y][x].isDigit() && arr[y][x] != '.'
fun digitPredicate(x: Int, y: Int, arr: Array<CharArray>): Boolean = arr[y][x].isDigit()

fun lookFor(
    y: Int,
    xRange: IntRange,
    arr: Array<CharArray>,
    predicate: LookPredicate,
): List<Pair<Int, Int>> {
    return xRange.flatMap { x -> lookFor(y, x, arr, predicate) }
}

fun lookFor(startY: Int, startX: Int, arr: Array<CharArray>, predicate: LookPredicate): List<Pair<Int, Int>> {
    return dirs.filter { (yDir, xDir) ->
        val x = startX + xDir
        val y = startY + yDir
        !(x < 0 || x >= arr[0].size || y < 0 || y >= arr.size) && predicate(x, y, arr)
    }
}
