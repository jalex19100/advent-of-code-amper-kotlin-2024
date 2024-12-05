import kotlin.math.abs

fun main() {

    data class Locations(
        val left: List<Int>,
        val right: List<Int>
    )

    fun Locations(input: List<String>): Locations {
        val items = input.map { line -> line.split("   ").map { item -> item.toInt() } }
        val left = items.map { item -> item[0] }.sorted()
        val right = items.map { item -> item[1] }.sorted()
        return Locations(left, right)
    }

    fun Locations.sumDifferences(): Int {
        return left.foldIndexed(0) { idx, sum, leftItem ->
            sum + abs(leftItem - right[idx])
        }
    }

    fun Locations.calculateSimilarityScore(): Int {
        return left.foldIndexed(0) { idx, sum, leftItem ->
            sum + leftItem * right.count { it == leftItem }
        }
    }

    fun part1(input: List<String>): Int {
        return Locations(input).sumDifferences()
    }

    fun part2(input: List<String>): Int {
        return Locations(input).calculateSimilarityScore()
    }

    val testInput = readInput("Day01_test")
    val part1Test = measureTimeMillis({ time -> print("Part1 Test ($time ms): ") }) {
        part1(testInput)
    }
    part1Test.println()
    val part2Test = measureTimeMillis({ time -> print("Part2 Test ($time ms): ") }) {
        part2(testInput)
    }
    part2Test.println()
    check(part1Test == 11)
    check(part2Test == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    val part1 = measureTimeMillis({ time -> print("Part1 ($time ms): ") }) {
        part1(input)
    }
    part1.println()
    val part2 = measureTimeMillis({ time -> print("Part2 ($time ms): ") }) {
        part2(input)
    }
    part2.println()
    check(part1 == 2815556)
    check(part2 == 23927637)

    println("### Success! ###")
}
