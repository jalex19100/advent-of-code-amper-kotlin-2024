data class Pos(
  val x: Int,
  val y: Int,
)

data class Cell(
  val pos: Pos,
  val value: Char
)

interface CellOp {
  fun move(start: Pos): Pos
}

enum class Direction : CellOp {
  UP {
    override fun move(start: Pos): Pos {
      return Pos(start.x, start.y - 1)
    }
  },
  UPRIGHT {
    override fun move(start: Pos): Pos {
      return Pos(start.x + 1, start.y - 1)
    }
  },
  RIGHT {
    override fun move(start: Pos): Pos {
      return Pos(start.x + 1, start.y)
    }
  },
  DOWNRIGHT {
    override fun move(start: Pos): Pos {
      return Pos(start.x + 1, start.y + 1)
    }
  },
  DOWN {
    override fun move(start: Pos): Pos {
      return Pos(start.x, start.y + 1)
    }
  },
  DOWNLEFT {
    override fun move(start: Pos): Pos {
      return Pos(start.x - 1, start.y + 1)
    }
  },
  LEFT {
    override fun move(start: Pos): Pos {
      return Pos(start.x - 1, start.y)
    }
  },
  UPLEFT {
    override fun move(start: Pos): Pos {
      return Pos(start.x - 1, start.y - 1)
    }
  }
}

fun main() {

  class Grid(
    val cells: List<List<Cell>>,
    val width: Int,
    val height: Int
  )

  fun Grid(input: List<String>): Grid {
    val width = if (input.isNotEmpty()) input[0].length else 0
    val height = input.size
    val cells = List(height) { row ->
      List(width) { col ->
        Cell(Pos(col, row), input[row][col])
      }
    }
    return Grid(cells, width, height)
  }

  fun Grid.inBounds(pos: Pos): Boolean {
    return (pos.x in 0..<width) && (pos.y in 0..<height)
  }

  fun Grid.getCell(pos: Pos): Cell {
    return cells[pos.y][pos.x]
  }

  fun Grid.scan(pos: Pos, direction: Direction): List<Cell> {
    val nextPos = direction.move(pos)
    return if (inBounds(pos)) {
      if (inBounds(nextPos))
        listOf(listOf(getCell(pos)), scan(nextPos, direction)).flatten()
      else
        listOf(getCell(pos))
    } else
      emptyList()
  }

  fun Grid.findAll(char: Char): List<Pos> {
    return cells.map { row -> row.filter { cell -> cell.value == char }.map { cell -> cell.pos } }.flatten()
  }

  fun part1(input: List<String>): Int {
    val grid = Grid(input)
    val matches = grid.findAll('X')
      .map { matchingX ->
        Direction.entries
          .map { direction -> grid.scan(matchingX, direction) }
          .filter { scannedCells -> scannedCells.size > 3 }
      }.map { candidates ->
        candidates
          .filter { listOfCells ->
            listOfCells.subList(0, 4).map { it.value } == listOf('X', 'M', 'A', 'S')
          }
      }.flatten()
    return matches.size
  }

  fun part2(input: List<String>): Int {
    val grid = Grid(input)
    val directions = listOf(Direction.UPRIGHT, Direction.DOWNRIGHT, Direction.DOWNLEFT, Direction.UPLEFT)
    val matches = grid.findAll('M')
      .asSequence()
      .map { matchingChar ->
        directions
          .map { direction -> grid.scan(matchingChar, direction) }
          .filter { scannedCells -> scannedCells.size > 2 }
      }
      .map { candidates ->
        candidates
          .map { listOfCells -> listOfCells.subList(0, 3) }
          .filter { subList -> subList.map { it.value } == listOf('M', 'A', 'S') }
      }
      .flatten().flatten()
      .filter { cell -> cell.value == 'A' }
      .groupingBy { cell -> cell.pos }
      .eachCount()
      .filter { it.value > 1 }
    return matches.size
  }


  // Test Input
  val testInput = readInput("Day04_test")
  val part1Test = measureTimeMillis({ time, result -> println("Part1 Test ($time ms): $result") }) {
    part1(testInput)
  }
  check(part1Test == 18)
  val part2Test = measureTimeMillis({ time, result -> println("Part2 Test ($time ms): $result") }) {
    part2(testInput)
  }
  check(part2Test == 9)

  // User Input
  val input = readInput("Day04")
  val part1 = measureTimeMillis({ time, result -> println("Part1 ($time ms): $result") }) {
    part1(input)
  }
  check(part1 == 2514)
  val part2 = measureTimeMillis({ time, result -> println("Part2 ($time ms): $result") }) {
    part2(input)
  }
  check(part2 == 1888)
}
