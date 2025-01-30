package day10

import utils.InputReaderToLines

enum class Direction(val deltaRow: Int, val deltaCol: Int) {
    UP(-1, 0),
    RIGHT(0, 1),
    DOWN(1, 0),
    LEFT(0, -1);

    fun turnRight(): Direction {
        val values = entries.toTypedArray()

        return values[(ordinal + 1) % values.size]
    }
}

data class Point(val row: Int, val col: Int)
data class Visit(val point: Point, val direction: Direction)

val allDirections = listOf(Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT)
fun main() {
//    val input = InputReaderToLines("src/day10/inputShort.txt")
    val input = InputReaderToLines("src/day10/input.txt")

    val lines = input.lines


    val grid = Array(lines[0].length) {
        IntArray(lines.size)
    }

    for (row in lines.indices) {
        for (col in lines[row].indices) {
            val point = lines[row][col]

            grid[row][col] = point.digitToInt()
        }
    }

    var totalScore = 0

    for (i in grid.indices) {
        for (j in grid[i].indices) {
            if (grid[i][j] == 0) {
                totalScore += findTrials(Point(i, j), grid)
            }
        }
    }

    println("total score: $totalScore")
//    findTrials(Point(7, 1), grid)
}

val visit: Map<Point, Set<Point>> = mutableMapOf() // start and end points
fun findTrials(start: Point, grid: Array<IntArray>): Int {
    println("Find trials for $start")
    val startVal = getVal(grid, start)
//    val allEndPoints = allDirections.flatMap { direction -> //for each direction, return list of end points?
    val allEndPoints = traverse2(start, startVal, grid, listOf())
//    }

    println("all points for $start  ${allEndPoints.size}: $allEndPoints")
    return allEndPoints.size
}

fun traverse( // THIS IS WRONG adds to the list 4x (for each direction)
    currentPoint: Point,
    currentVal: Int,
    grid: Array<IntArray>,
    direction: Direction,
    endPoints: Set<Point>
): Set<Point> {
    if (currentVal == 9) {
        return setOf(currentPoint)
    }

    if (goingOutOfBounds(currentPoint, direction, grid)) {
        return emptySet()
    }

    val nextPoint = move(currentPoint, direction)
    val nextVal = getVal(grid, nextPoint)

    if (nextVal == currentVal + 1) {
        return allDirections.flatMap {
            traverse(nextPoint, nextVal, grid, it, endPoints)
        }.toSet()
    }
    return emptySet()
}

fun traverse2(currentPoint: Point, currentVal: Int, grid: Array<IntArray>, endPoints: List<Point>): List<Point> {
    if (currentVal == 9) {
        return listOf(currentPoint)
    }
    return allDirections.flatMap { direction ->
        if (goingOutOfBounds(currentPoint, direction, grid)) {
            emptyList()
        } else {
            val nextPoint = move(currentPoint, direction)
            val nextVal = getVal(grid, nextPoint)

            if (nextVal == currentVal + 1) {
                traverse2(nextPoint, nextVal, grid, endPoints)
            } else {
                emptyList()
            }
        }
    }
}

private fun move(currentPoint: Point, direction: Direction) =
    Point(currentPoint.row + direction.deltaRow, currentPoint.col + direction.deltaCol)

private fun getVal(grid: Array<IntArray>, point: Point) = grid[point.row][point.col]

fun goingOutOfBounds(currentPosition: Point, direction: Direction, grid: Array<IntArray>): Boolean =
    currentPosition.row + direction.deltaRow >= grid.size || currentPosition.col + direction.deltaCol >= grid[0].size
            || currentPosition.row + direction.deltaRow < 0 || currentPosition.col + direction.deltaCol < 0