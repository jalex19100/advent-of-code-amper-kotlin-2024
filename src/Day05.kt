fun main() {

    fun intSplitter(separator: Char, input: List<String>): List<List<Int>> {
        return input.filter { it.contains(separator) }
            .map { line -> line.split(separator).map { it.toInt() } }
    }

    fun ruleCheck(update: List<Int>, rules: List<List<Int>>): Boolean {
        rules.forEach { rule ->
            if (update.containsAll(rule) && update.indexOf(rule[0]) > update.indexOf(rule[1])) {
                return false
            }
        }
        return true
    }

    data class SafetyManualUpdates(
        val rules: List<List<Int>>, val updates: List<List<Int>>
    )

    fun SafetyManualUpdates(input: List<String>): SafetyManualUpdates {
        return SafetyManualUpdates(intSplitter('|', input), intSplitter(',', input))
    }

    fun getGraph(rules: List<List<Int>>, update: List<Int>): Map<Int, List<Int>> {
        return update.associateWith { page ->
            rules.filter { it[0] == page && update.contains(it[1]) }.map { it[1] }
        }
    }

    fun depthFirstSearch(
        page: Int,
        graph: Map<Int, List<Int>>,
        visited: ArrayList<Int>
    ): ArrayList<Int> {
        graph[page]?.forEach { edge ->
            if (!visited.contains(edge)) {
                visited.addAll(depthFirstSearch(edge, graph, visited).filter { !visited.contains(it) })
            }
        }
        !visited.contains(page) && visited.add(page)
        return visited
    }

    fun sumMiddle(updates: List<List<Int>>): Int {
        return updates.fold(0) { sum, validUpdate ->
            sum + validUpdate[(validUpdate.count() - 1) / 2]
        }
    }

    fun part1(input: List<String>): Int {
        val safetyManualUpdates = SafetyManualUpdates(input)
        return sumMiddle(safetyManualUpdates.updates.filter { ruleCheck(it, safetyManualUpdates.rules) })
    }

    fun part2(input: List<String>): Int {
        val safetyManualUpdates = SafetyManualUpdates(input)
        val invalidUpates = safetyManualUpdates.updates.filter { !ruleCheck(it, safetyManualUpdates.rules) }
        val sortedInvalidUpdates = invalidUpates.map { invalidUpdate ->
            val graph = getGraph(safetyManualUpdates.rules, invalidUpdate)
            val visited = ArrayList<Int>()
            graph.keys.forEach { edge ->
                visited.addAll(depthFirstSearch(edge, graph, visited).filter { !visited.contains(it) })
            }
            visited.reversed()
        }
        // println("Unsorted invalid updates: $invalidUpates")
        // println("Sorted invalid updates: $sortedInvalidUpdates")
        return sumMiddle(sortedInvalidUpdates)
    }

    val testInput = readInput("Day05_test")
    val part1Test = measureTimeMillis({ time, result -> println("Part1 Test ($time ms): $result") }) {
        part1(testInput)
    }
    val part2Test = measureTimeMillis({ time, result -> println("Part2 Test ($time ms): $result") }) {
        part2(testInput)
    }
    check(part1Test == 143)
    check(part2Test == 123)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day05")
    val part1 = measureTimeMillis({ time, result -> println("Part1 ($time ms): $result") }) {
        part1(input)
    }
    val part2 = measureTimeMillis({ time, result -> println("Part2 ($time ms): $result ") }) {
        part2(input)
    }
    check(part1 == 5732)
    check(part2 == 4716)
}
