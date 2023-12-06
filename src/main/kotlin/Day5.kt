import pl.bfelis.aoc2022.getInput
import kotlin.system.measureTimeMillis

data class MapEntry(val dest: Long, val source: Long, val range: Long)

fun main() {
    val input = getInput("day5.txt")!!.trim()
    val data = input.split("\\n\\s*\\n".toRegex())
    val seeds = data[0].split(": ")[1].split(" ").map { it.toLong() }
    val maps = data.drop(1).associate {
        val (kind, ranges) = it.split(":")
        val mappedRanges = ranges.trim().lines().map { line ->
            val (dest, source, range) = line.trim().split(" ").map { n -> n.toLong() }
            MapEntry(dest, source, range)
        }
        kind to mappedRanges
    }

    // PART 1
    seeds.minOfOrNull { seed ->
        maps.entries.fold(seed) { acc, (_, map) ->
            map.firstOrNull { entry ->
                acc >= entry.source && acc <= entry.source + entry.range
            }?.let { entry -> acc - entry.source + entry.dest } ?: acc
        }
    }.let { println(it) }

    // PART 2
    measureTimeMillis {
        seeds.chunked(2).minOf { seedRange ->
            (seedRange[0]..<(seedRange[0] + seedRange[1])).minOf { seed ->
                // TBH I don't care about complexity XD
                maps.entries.fold(seed) { acc, (_, map) ->
                    map.firstOrNull { entry ->
                        acc >= entry.source && acc < entry.source + entry.range
                    }?.let { entry -> acc - entry.source + entry.dest } ?: acc
                }
            }
        }.let { println(it) }
    }.let { println("TOOK $it ms") }
}
