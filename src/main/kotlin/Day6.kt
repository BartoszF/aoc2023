import pl.bfelis.aoc2022.getInput
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.sqrt

fun main() {
    val input = getInput("day6.txt")!!.trim().lines()

    // Part 1
    val splitRegex = "\\s+".toRegex()

    val time = input[0].split(":")[1].trim().split(splitRegex).map { it.toInt() }
    val dist = input[1].split(":")[1].trim().split(splitRegex).map { it.toInt() }

    val ranges = (0..<time.count()).map {
        val b = -time[it].toDouble()
        val c = dist[it].toDouble()
        val delta = getDelta(1.0, b, c)
        getRange(1.0, b, delta)
    }

    println(ranges.fold(1) { acc, it -> acc * it.count() })

    // Part 2
    val oneTime = input[0].split(":")[1].trim().split(splitRegex).joinToString("").toLong()
    val oneDist = input[1].split(":")[1].trim().split(splitRegex).joinToString("").toLong()

    val b = -oneTime.toDouble()
    val c = oneDist.toDouble()
    val delta = getDelta(1.0, b, c)
    val range = getRange(1.0, b, delta)
    println(range)
    println(range.count())
}

fun getDelta(a: Double, b: Double, c: Double): Double {
    return b.pow(2) - 4.0 * a * c
}

fun getRange(a: Double, b: Double, delta: Double): IntRange {
    val sqrtDelta = sqrt(delta)
    val x1 = (-b - sqrtDelta) / 2 * a
    val x2 = (-b + sqrtDelta) / 2 * a

    return (ceil(x1 + 0.01f).toInt()..(floor(x2 - 0.01f).toInt()))
}
