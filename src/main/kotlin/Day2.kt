import pl.bfelis.aoc2022.getInput

fun main() {
    val input = getInput("day2.txt")!!.trim()

    val games = input.lines().map {
        val game = it.replace("Game ", "").split(":")
        game[0].toInt() to game[1].trim().split("; ").map { set ->
            set.split(", ").associate { color ->
                val splitCube = color.split(" ")
                splitCube[1] to splitCube[0].toInt()
            }
        }
    }

    // Part 1
    games.sumOf { (id, sets) ->
        val viable =
            sets.all { set -> (set["red"] ?: 0) <= 12 && (set["green"] ?: 0) <= 13 && (set["blue"] ?: 0) <= 14 }
        if (viable) id else 0
    }.let { println(it) }

    // Part 2
    games.sumOf { (_, sets) ->
        val red = sets.maxOf { it["red"] ?: 0 }
        val green = sets.maxOf { it["green"] ?: 0 }
        val blue = sets.maxOf { it["blue"] ?: 0 }

        red * green * blue
    }.let { println(it) }
}
