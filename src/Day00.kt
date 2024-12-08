fun main() {

    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day00_test")
    val part1Test = measureTimeMillis({ time, result -> println("Part1 Test ($time ms): $result") }) {
        part1(testInput)
    }
//    val part2Test = measureTimeMillis({ time, result -> println("Part2 Test ($time ms): $result") }) {
//        part2(testInput)
//    }
//    check(part1Test == 1)
//    check(part2Test == 1)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day00")
//    val part1 = measureTimeMillis({ time, result -> println("Part1 ($time ms): $result") }) {
//        part1(input)
//    }
//    val part2 = measureTimeMillis({ time, result -> println("Part2 ($time ms): $result") }) {
//        part2(input)
//    }
//    check(part1 == 1)
//    check(part2 == 1)
}
