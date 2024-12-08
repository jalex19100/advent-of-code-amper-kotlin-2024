fun main() {

    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }


    // Test Input
    val testInput = readInput("Day00_test")
    val part1Test = measureTimeMillis({ time, result -> println("Part1 Test ($time ms): $result") }) {
        part1(testInput)
    }
//    check(part1Test == 1)
//    val part2Test = measureTimeMillis({ time, result -> println("Part2 Test ($time ms): $result") }) {
//        part2(testInput)
//    }
//    check(part2Test == 1)

    // User Input
    val input = readInput("Day00")
//    val part1 = measureTimeMillis({ time, result -> println("Part1 ($time ms): $result") }) {
//        part1(input)
//    }
//    check(part1 == 1)
//    val part2 = measureTimeMillis({ time, result -> println("Part2 ($time ms): $result") }) {
//        part2(input)
//    }
//    check(part2 == 1)
}
