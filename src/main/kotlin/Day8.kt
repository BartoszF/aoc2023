import pl.bfelis.aoc2022.getInput
import pl.bfelis.aoc2022.repeat

data class Node(val value: String, var left: Node?, var right: Node?) {
    override fun toString(): String {
        return "Node(value='$value', left=${left?.value}, right=${right?.value})"
    }
}

fun main() {
    val input = getInput("day8.txt")!!.trim()
    val splitRegex = "\\n\\s*\\n".toRegex()

    val parts = input.split(splitRegex)
    val moveSeq = parts[0].trim().toCharArray().toList()
    val nodeLines = parts[1].trim().lines()

    val readNodes = nodeLines.associate {
        val line = it.split(" = ")
        val n = line[1].replace("[\\(\\)]".toRegex(), "").split(", ")
        line[0] to (n[0] to n[1])
    }

    val nodes = readNodes.keys.map {
        Node(it, null, null)
    }

    nodes.forEach {
        it.left = nodes.first { n -> n.value == readNodes[it.value]!!.first }
        it.right = nodes.first { n -> n.value == readNodes[it.value]!!.second }
    }

    val sequence = moveSeq.asSequence().repeat()

    // PART 1
    var currentNode = nodes.first { it.value == "AAA" }
    var moves = 0

    while (currentNode.value != "ZZZ") {
        when (sequence.elementAt(moves)) {
            'L' -> currentNode = currentNode.left!!
            'R' -> currentNode = currentNode.right!!
        }
        moves++
    }

    println(moves)

    var currentNodes = nodes.filter { it.value.endsWith('A') }
    moves = 0

    while (!currentNodes.all { it.value.endsWith('Z') }) {
        when (sequence.elementAt(moves)) {
            'L' -> currentNodes = currentNodes.map { it.left!! }
            'R' -> currentNodes = currentNodes.map { it.right!! }
        }
        moves++
    }

    println(moves)
}
