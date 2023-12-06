import pl.bfelis.aoc2022.getInput
import kotlin.math.floor
import kotlin.math.pow

fun main() {
    val input = getInput("day4.txt")!!.trim()
    val lines = input.lines()

    val splitRegex = "\\s+".toRegex()

    val cards = lines.map {
        val (winningStr, gotStr) = it.split(": ")[1].split("|")
        val winning = winningStr.trim().split(splitRegex).toSet()
        val got = gotStr.trim().split(splitRegex).toList()
        winning to got
    }

    val wins = cards.map { (winning, got) ->
        got.intersect(winning)
    }

    // Part 1
    wins.sumOf { w ->
        val s = 2f.pow(w.count() - 1)
        floor(s).toInt()
    }.let { println(it) }

    // Part 2
    var cardsCount = 0
    val cardsToCheck = (1..cards.count()).map { it - 1 }.toMutableList()

    while (cardsToCheck.isNotEmpty()) {
        cardsCount++
        val card = cardsToCheck.removeFirst()
        val newCards = wins[card].count()
        (1..newCards).map { card + it }.forEach { cardsToCheck.add(it) }
        println("$cardsCount ${cardsToCheck.count()}")
    }

    println(cardsCount)
}
