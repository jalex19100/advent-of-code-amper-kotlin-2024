fun main() {
    fun executeInstruction(instruction: String): Int {
        val parts = Regex("[(,)]").split(instruction)
        return (parts[1].toInt() * parts[2].toInt())
    }

    fun executeAllInstructions(inputString: String): Int {
        val multiplyFunctions = Regex("(mul\\(\\d+,\\d+\\))").findAll(inputString).toList().map { it.value }
        return multiplyFunctions.fold(0) { sum, instruction -> sum + executeInstruction(instruction) }
    }

    fun part1(input: List<String>): Int {
        return executeAllInstructions(input.joinToString(separator = ""))
    }

    fun part2(input: List<String>): Int {
        return executeAllInstructions(
            Regex("don't\\(\\).*?do\\(\\)").replace(input.joinToString(""), "")
        )
    }


    // Test Input
    val testInput = readInput("Day03_test")
    val part1Test = measureTimeMillis({ time, result -> println("Part1 Test ($time ms): $result") }) {
        part1(testInput)
    }
    check(part1Test == 161)
    val part2Test = measureTimeMillis({ time, result -> println("Part2 Test ($time ms): $result") }) {
        part2(testInput)
    }
    check(part2Test == 48)

    // User Input
    val input = readInput("Day03")
    val part1 = measureTimeMillis({ time, result -> println("Part1 ($time ms): $result") }) {
        part1(input)
    }
    check(part1 == 174103751)
    val part2 = measureTimeMillis({ time, result -> println("Part2 ($time ms): $result") }) {
        part2(input)
    }
    check(part2 == 100411201)
}
