import pl.bfelis.aoc2022.getInput
import java.util.*

data class Lens(val label: String, var focal: Int)

fun main() {
    val input = getInput("day15.txt")!!.trim()
    val strings = input.split(",")

    // PART 1
    strings.sumOf { hash(it) }.let { println(it) }

    // PART 2
    val boxes = (0..<256).map { Stack<Lens>() }.toList()

    strings.forEach {
        if (it.contains('-')) {
            removeLens(it.replace("-", ""), boxes)
        } else if (it.contains('=')) {
            addLens(it.split("=").zipWithNext()[0], boxes)
        }
    }

    val strength = boxes.flatMapIndexed { index, lenses ->
        lenses.mapIndexed { lensIndex, lens ->
            (index + 1) * (lensIndex + 1) * lens.focal
        }
    }.sum()

    println(strength)
}

fun addLens(lensPair: Pair<String, String>, boxes: List<Stack<Lens>>) {
    val label = lensPair.first
    val focal = lensPair.second.toInt()

    val box = hash(label).toInt()
    val lensInBox = boxes[box].firstOrNull { it.label == label }
    if (lensInBox != null) {
        lensInBox.focal = focal
    } else {
        boxes[box].push(Lens(label, focal))
    }
}

fun removeLens(label: String, boxes: List<Stack<Lens>>) {
    val box = hash(label).toInt()
    val lensInBox = boxes[box].firstOrNull { it.label == label }
    boxes[box].remove(lensInBox)
}

fun hash(str: String): Long {
    return str.toCharArray().map { it.toInt() }.fold(0L) { acc, char ->
        var res = acc + char
        res *= 17
        res %= 256
        res
    }
}
