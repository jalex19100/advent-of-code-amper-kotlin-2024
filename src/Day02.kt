import kotlin.math.abs

fun main() {
    fun getIntervals(levels: List<Int>): List<Int> {
        return levels.windowed(2).map { (a, b) ->
            b - a
        }
    }

    fun checkSafety(levels: List<Int>, oneLevelSkipEnabled: Boolean = false): Boolean {
        val tries = if (oneLevelSkipEnabled) levels.size else 1
        for (i in tries downTo 0) {
            val (pos, neg) = getIntervals(levels).filter { level -> level != 0 && abs(level) <= 3 }
                .partition { level -> level > 0 }
            if (pos.size == levels.size - 1 || neg.size == levels.size - 1) return true
            if (oneLevelSkipEnabled) {
                if (checkSafety(levels.filterIndexed { index, _ -> index != i })) return true
            }
        }
        return false
    }

    fun part1(input: List<String>): Int {
        return splitToListsOfInts(' ', input).filter { levels -> checkSafety(levels) }.size
    }

    fun part2(input: List<String>): Int {
        return splitToListsOfInts(' ', input).filter { levels -> checkSafety(levels, true) }.size
    }

    // Test Input
    val testInput = readInput("Day02_test")
    val part1Test = measureTimeMillis({ time, result -> println("Part1 Test ($time ms): $result") }) {
        part1(testInput)
    }
    check(part1Test == 2)

    val part2Test = measureTimeMillis({ time, result -> println("Part2 Test ($time ms): $result") }) {
        part2(testInput)
    }
    check(part2Test == 4)

    // User Input
    val input = readInput("Day02")
    val part1 = measureTimeMillis({ time, result -> println("Part1 ($time ms): $result") }) {
        part1(input)
    }
    check(part1 == 390)
    val part2 = measureTimeMillis({ time, result -> println("Part2 ($time ms): $result") }) {
        part2(input)
    }
    check(part2 == 439)
}
