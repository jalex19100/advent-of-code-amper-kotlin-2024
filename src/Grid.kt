data class Pos(
  val x: Int,
  val y: Int,
)

interface PositionMovement {
  fun move(start: Pos): Pos
}

enum class Direction : PositionMovement {
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

class PosDir(
  val pos: Pos,
  val direction: Direction
) {
  override fun equals(other: Any?): Boolean {
    if (other !is PosDir) return false
    return this.hashCode() == other.hashCode()
  }

  override fun hashCode(): Int {
    return ((this.pos.x * 100) + (this.pos.y * 10) + this.direction.ordinal)
  }

  override fun toString(): String {
    return StringBuilder("(")
      .append(this.pos.x)
      .append(',')
      .append(this.pos.y)
      .append(").")
      .append(this.direction)
      .toString()
  }
}


fun Direction.change(): Direction {
  return when (this) {
    Direction.UP -> Direction.RIGHT
    Direction.RIGHT -> Direction.DOWN
    Direction.DOWN -> Direction.LEFT
    Direction.LEFT -> Direction.UP
    else -> Direction.UP
  }
}


data class Cell(
  val pos: Pos,
  val value: Char
)


class Grid(
  val cells: List<List<Cell>>,
  val width: Int,
  val height: Int
) {
  override fun toString(): String {
    return cells.map { row ->
      row.map {
        it.value
      } + '\n'
    }.toString()
  }
}

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

// replace element in list
fun Grid.setCell(cell: Cell): Grid {
  val newRow =
    cells[cell.pos.y].slice(0 until cell.pos.x) + cell +
            cells[cell.pos.y].slice(cell.pos.x + 1 until cells[cell.pos.x].size)
  val newRows = cells.toMutableList()
  newRows[cell.pos.y] = newRow
  return Grid(newRows.toList(), cells[0].size, cells.size)
}

fun Grid.inBounds(pos: Pos): Boolean {
  return (pos.x in 0..<width) && (pos.y in 0..<height)
}

fun Grid.getCell(pos: Pos): Cell {
  return cells[pos.y][pos.x]
}

fun Grid.gridSize(): Int {
  return cells.size * cells[0].size
}
