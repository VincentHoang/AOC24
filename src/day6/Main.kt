package day6

import utils.InputReaderToLines

enum class Direction(val c: Char, val deltaRow: Int, val deltaCol: Int) {
    UP('|', -1, 0),
    RIGHT('-', 0, 1),
    DOWN('|', 1, 0),
    LEFT('-', 0, -1);

    fun turnRight(): Direction {
        val values = entries.toTypedArray()

        return values[(ordinal + 1) % values.size]
    }
}

data class Point(val row: Int, val col: Int)
data class Visit(val point: Point, val direction: Direction)

fun main() {
    val input = InputReaderToLines("src/day6/input.txt")
//    val input = InputReaderToLines("src/day6/inputShort.txt")

    val lines = input.lines

    var currentPosition = Point(0, 0)
//    val visited = hashSetOf<Point>()

    val grid = Array(lines[0].length) {
        CharArray(lines.size)
    }

    for (row in lines.indices) {
        for (col in lines[row].indices) {
            val point = lines[row][col]
            if (point == '^') {
                currentPosition = Point(row, col)
//                visited.add(currentPosition)
            }
            grid[row][col] = point
        }
    }

    for (row in grid) {
        println(row)
    }
    println("        ")

    //////////////////////////////////////////////////////////////////////

//    part1(currentPosition, grid)
    part2(currentPosition, grid)
}

private fun part1(
    startingPosition: Point,
    grid: Array<CharArray>
) {
    var currentPosition = startingPosition
    var direction = Direction.UP
    var keepGoing = true
    val visited: HashSet<Point> = hashSetOf()
    visited.add(currentPosition)

    while (keepGoing) {
        if (goingOutOfBounds(currentPosition, direction, grid)) {
            keepGoing = false
        } else {
            grid[currentPosition.row][currentPosition.col] = direction.c

            val newPosition =
                Point(currentPosition.row + direction.deltaRow, currentPosition.col + direction.deltaCol)
            val valueOfNextPosition = grid[newPosition.row][newPosition.col]

            if (valueOfNextPosition == '#') {
                direction = direction.turnRight()
            } else {
                currentPosition = newPosition
                visited.add(currentPosition)
            }
        }

        for (row in grid) {
            println(row)
        }

        println("        ")
    }

    println("Number of visits: ${visited.size}")
}

private fun part2(
    startingPosition: Point,
    grid: Array<CharArray>
) {
    hasLoop(startingPosition, addObstruction(grid, Point(0, 0)))
    val countLoop = grid.flatMapIndexed { i, row ->
        row.mapIndexed { j, value ->
            if (value == '.') {

                hasLoop(startingPosition, addObstruction(grid, Point(i, j)))
            } else {
                false
            }
        }
    }.count { it }

//    val loop = hasLoop(startingPosition, addObstruction(grid, Point(row=1, col=8)))
//    println(loop)

    println("loops?: $countLoop")
}

private fun addObstruction(grid: Array<CharArray>, at: Point): Array<CharArray> {
    val copy = Array(grid.size) { row ->
        grid[row].clone()
    }

    copy[at.row][at.col] = 'O'

    return copy
}

private fun hasLoop(
    startingPosition: Point,
    grid: Array<CharArray>
): Boolean {
    var currentPosition = startingPosition
    var direction = Direction.UP
    var keepGoing = true
    val visited: HashSet<Visit> = hashSetOf()
    visited.add(Visit(currentPosition, direction))

    while (keepGoing) {
        if (goingOutOfBounds(currentPosition, direction, grid)) {
            keepGoing = false
        } else {
            val newPosition =
                Point(currentPosition.row + direction.deltaRow, currentPosition.col + direction.deltaCol)
            val valueOfNextPosition = grid[newPosition.row][newPosition.col]

            if (valueOfNextPosition == '#' || valueOfNextPosition == 'O') {
                direction = direction.turnRight()
                grid[currentPosition.row][currentPosition.col] = '+'
            } else {
                grid[newPosition.row][newPosition.col] = direction.c
                currentPosition = newPosition

                if (visited.contains(Visit(currentPosition, direction))) {
                    return true
                }
                visited.add(Visit(currentPosition, direction))

            }
        }

//        for (row in grid) {
//            println(row)
//        }
//        println("        ")
    }

//    println("Number of visits: ${visited.size}")

    return false
}

fun goingOutOfBounds(currentPosition: Point, direction: Direction, grid: Array<CharArray>): Boolean =
    currentPosition.row + direction.deltaRow >= grid.size || currentPosition.col + direction.deltaCol >= grid[0].size
            || currentPosition.row + direction.deltaRow < 0 || currentPosition.col + direction.deltaCol < 0
