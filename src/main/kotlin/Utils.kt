package pl.bfelis.aoc2022

fun getInput(name: String): String? {
    return {}.javaClass.getResource("/$name")?.readText()
}
