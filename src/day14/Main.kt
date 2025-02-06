package day14

import utils.InputReaderToLines

data class Point(val x: Int, val y: Int)

data class Robot(val initialPosition: Point, val velocity: Point) {

    var position: Point = initialPosition // TODO prob best to have a hashmap to keep track of current movement
    fun move(): Point {
        position = Point(position.x + velocity.x, position.y + velocity.y)

        return position
    }
}

val robotPositions = mutableMapOf<Robot, Point>()
fun main() {
    val width = 101
    val height = 103
//    val width = 11
//    val height = 7
//    val input = InputReaderToLines("src/day14/inputShort.txt")
    val input = InputReaderToLines("src/day14/input.txt")
    //110576340
    //199870020
    input.lines

    val noOfMoves = 100000

    val robots = input.lines.map {
        val pRegex = Regex("p=(-?\\d+),(-?\\d+)")
        val vRegex = Regex("v=(-?\\d+),(-?\\d+)")
        val pString = pRegex.find(it)!!
        val p = Point(pString.groupValues[1].toInt(), pString.groupValues[2].toInt())

        val vString = vRegex.find(it)!!
        val v = Point(vString.groupValues[1].toInt(), vString.groupValues[2].toInt())

        val robot = Robot(p, v)
        robotPositions[robot] = robot.initialPosition.copy()
        robot
    }

    println(robots)

    val tiles = createTiles(width, height, robots)

    printGrid(tiles)

    repeat(noOfMoves) { i ->
        robots.forEach { robot ->
            move(robot, tiles)
        }

//27317
        if (findTree(tiles)) {
//        if (i >= 27300) {
            println("$i seconds")
            printGrid(tiles)
        }

//6511
    }

//    robots.forEach {
//        val y = if (it.velocity.y > 0) {
//            (it.initialPosition.y + (it.velocity.y * noOfMoves) ) % height
//        } else {
//            (it.initialPosition.y - (it.velocity.y * noOfMoves) ) % height
//        }
//        val x = (it.initialPosition.x * it.velocity.x) % width
//
//        tiles[y][x]++
//    }

//    var total = 1
//    println("========")
//    total *= quadrant(tiles, 0, 0, width / 2, height / 2)
//    println("-------")
//    total *= quadrant(tiles, 0, height / 2 + 1, width / 2, height)
//    println("-------")
//    total *= quadrant(tiles, width / 2 + 1, 0, width, height / 2)
//    println("-------")
//    total *= quadrant(tiles, width / 2 + 1, height / 2 + 1, width, height)
//
//    println("Total: $total")
}

fun findTree(tiles: Array<IntArray>): Boolean {
    for (row in tiles.indices) {
        var foundFirstValue = false
        var consecutive = 0
        for (col in tiles[row]) {
            if (col > 0) {
                foundFirstValue = true
                consecutive++
            } else {
                if (foundFirstValue) {
                    if (consecutive > 10) {
                        println("FOUND CONS row $row")
                        return true
                    }

                    consecutive = 0
                    foundFirstValue = false
                }
            }
        }

        if (consecutive > 5) {
            println("FOUND CONS row $row")
            return true
        }
//        println("")
    }

    return false
}

fun quadrant(tiles: Array<IntArray>, startX: Int, startY: Int, endX: Int, endY: Int): Int {
    var accu = 0
    println("$startX, $startY, $endX, $endY")

    for (y in startY until endY) {
        for (x in startX until endX) {
            val i = tiles[y][x]
            if (i == 0) {
                print(".")
            } else {
                print(i)
                accu += i
            }
        }

        println("")

    }

    println("\n$accu\noooooooooooooooo")

    return accu
}

private fun printGrid(tiles: Array<IntArray>) {
    for (row in tiles.indices) {
        print(row)
        for (col in tiles[row]) {

            print(
                if (col == 0) "." else "#"
            )
        }

        println("")
    }

    println("")

}

fun createTiles(width: Int, height: Int, robots: List<Robot>): Array<IntArray> {
    val tiles = Array(height) {
        IntArray(width)
    }

    for (robot in robots) {
        val initialPos = robot.initialPosition
        tiles[initialPos.y][initialPos.x]++
    }

    return tiles
}

fun move(robot: Robot, tiles: Array<IntArray>) {
    val currentTile = robotPositions[robot]!!
    tiles[currentTile.y][currentTile.x]--

    val currentPosition = robotPositions[robot]!!
    var newX = currentPosition.x + robot.velocity.x
    var newY = currentPosition.y + robot.velocity.y
//    val newPos = Point(newX, newY)

    // if out of bounds ->
    if (newX >= tiles[0].size) {
        newX = robot.velocity.x - (tiles[0].size - currentPosition.x)
    } else if (newX < 0) {
        newX = tiles[0].size + robot.velocity.x + currentPosition.x //robot.velocity.x is negative so it'll just flip the +
    }

    if (newY >= tiles.size) {
        newY = robot.velocity.y - (tiles.size - currentPosition.y)
    } else if (newY < 0) {
        newY = (tiles.size) + (robot.velocity.y + currentPosition.y)
    }

    val newPos = Point(newX, newY)
    robotPositions[robot] = newPos
    tiles[newPos.y][newPos.x]++

}