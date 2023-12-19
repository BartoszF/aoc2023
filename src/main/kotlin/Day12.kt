import pl.bfelis.aoc2022.getInput

fun main() {
    val lines = getInput("day12_test.txt")!!.trim().lines()

//    println("##....##".split(".").filter { it.isNotEmpty() })

    // PART 1
    lines.sumOf {
        println()
        println("GOT: $it")
        val res = checkSolutions(it)
        println(res)
        res
    }.let { println(it) }

    // PART 2
//    lines.sumOf {
// //        println()
// //        println("GOT: $it")
//        val res = checkSolutionsPart2(it)
// //        println(res)
//        res
//    }.let { println(it) }
}

fun checkSolutions(line: String): Long {
    val (record, wanted) = line.split(" ")
    val wantedGroups = wanted.split(",").map { it.toInt() }

    return checkSolution(record, wantedGroups)
}

fun checkSolutionsPart2(line: String): Long {
    val (record, wanted) = line.split(" ")
    val wantedGroups = wanted.split(",").map { it.toInt() }

    val repeatedRecord = mutableListOf<String>()
    val repeatedGroups = mutableListOf<Int>()

    (0..<5).forEach { _ ->
        repeatedRecord.add(record)
        repeatedGroups.addAll(wantedGroups)
    }

    return checkSolution(repeatedRecord.joinToString("?"), repeatedGroups)
}

fun checkSolution(record: String, wantedGroups: List<Int>): Long {
    println("CHECKING $record")

    val groups = record.split(".").filter { it.isNotEmpty() }
    val isCorrect =
        groups.size == wantedGroups.size && record.count { it == '?' } == 0 && groups.mapIndexed { index, group ->
            group.count { it == '#' } == wantedGroups[index]
        }.all { it }

    if (isCorrect) {
        println(" !!! $record is correct")
        return 1
    }

    // If groups <= wanted groups
    //    Every group has # <= wantedGroup[group]
    //    AND group has ? > 0

    if (groups.size > wantedGroups.size) {
        println("$record is not possible")
        return 0
    }

    val isPossible =
        groups.mapIndexed { index, group ->
            group.count { it == '#' } <= wantedGroups[index] ||
                groups.sumOf { g -> g.count { it == '?' } } > 0
        }.all { it }

    if (!isPossible) {
        println("$record is not possible")
        return 0
    }
    val newRecord = groups.joinToString(".")
    if (newRecord.count { it == '?' } == 0) {
        println("$record is not possible")
        return 0
    }
    return checkSolution(record.replaceFirst("?", "."), wantedGroups) + checkSolution(
        record.replaceFirst(
            "?",
            "#",
        ),
        wantedGroups,
    )
}
