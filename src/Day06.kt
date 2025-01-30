fun main() {
  val START = '^'
  val EMPTY = '.'

  fun Grid.guardMove(current: PosDir, tries: Int = 3): PosDir? {
    val currentPos = current.pos
    val currentDirection = current.direction
    val newPos = currentDirection.move(currentPos)
    return if (inBounds(newPos) && (getCell(newPos).value == START || getCell(newPos).value == EMPTY)) {
      PosDir(newPos, currentDirection)
    } else if (inBounds(newPos) && tries > 0) {
      guardMove(PosDir(currentPos, currentDirection.change()), tries - 1)
    } else {
      null
    }
  }


  fun Grid.followProtocol(posDir: PosDir, infLoopCheck: Boolean): List<PosDir> {
    val movements = ArrayList<PosDir>()
    movements.add(posDir)
    var nextPosDir = guardMove(posDir)
    // loop until out of bounds or a movement repeats
    while (nextPosDir != null) {
      if (infLoopCheck && movements.count { p -> p == nextPosDir } > 5) {
        return emptyList()
      }
      movements.add(nextPosDir)
      nextPosDir = guardMove(nextPosDir)
    }
    return movements
  }

  fun Grid.getMovements(infLoopCheck: Boolean = false): List<PosDir> {
    val start =
      cells.map { row ->
        row
          .filter { cell -> cell.value == START }
          .map { cell -> cell.pos }
      }.flatten().first()
    return followProtocol(PosDir(start, Direction.UP), infLoopCheck)
  }

  fun part1(input: List<String>): Int {
    return Grid(input).getMovements().map { posDir -> posDir.pos }.toSet().size
  }

  fun part2(input: List<String>): Int {
    val grid = Grid(input)
    val uniquePositionsMinusStart = grid.getMovements()
      .map { posDir -> posDir.pos }.drop(1).toSet()
    val options = uniquePositionsMinusStart.fold(0) { sum, pos ->
      if (grid.getCell(pos).value == '^') sum
      else {
        val tempGrid = grid.setCell(Cell(Pos(pos.x, pos.y), 'O'))
        val tempMovements = tempGrid.getMovements(true)
        if (tempMovements.isEmpty()) sum + 1
        else sum
      }
    }
    return options
  }


  // Test Input
  val testInput = readInput("Day06_test")
  val part1Test = measureTimeMillis({ time, result -> println("Part1 Test ($time ms): $result") }) {
    part1(testInput)
  }
  check(part1Test == 41)

  val part2Test = measureTimeMillis({ time, result -> println("Part2 Test ($time ms): $result") }) {
    part2(testInput)
  }
  check(part2Test == 6)

  // User Input
  val input = readInput("Day06")
  val part1 = measureTimeMillis({ time, result -> println("Part1 ($time ms): $result") }) {
    part1(input)
  }
  check(part1 == 4973)

  val part2 = measureTimeMillis({ time, result -> println("Part2 ($time ms): $result") }) {
    part2(input)
  }
  check(part2 == 1482)
}
