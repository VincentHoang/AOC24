package day11.two

import utils.InputReaderToLines

fun main() {
//    val input = InputReaderToLines("src/day11/inputShort.txt")
    val input = InputReaderToLines("src/day11/input.txt")

    val lines = input.lines

    val initial = lines[0].split(" ")

    println(initial)
//    blinkTimes(initial, 6)
//    val newList = blinkTimes(initial, 75)
//    println(newList)
//    println("Stones : ${newList.size}")

//    println("Total: ${blinkDFS(initial, 25)}")
    println("Total: ${blinkDFS(initial, 75)}")
}

// 0 -> 1  : NOTHING
// 2233 -> 22, 33 : -> 2 then 4. so 4 stones
// else -> n * 2024 -> always becomes even digits = this many stones

// 234 * 2024 ->
// Basically DFS -> up to n blinks -> returns the total number, no need to keep it in a list

val cache = mutableMapOf<Pair<Long, Int>, Long>() //currentValue to totalNumberOfStonres
fun blinkDFS(initial: List<String>, numberOfBlinks: Int): Long {
//    println("Blink for 125: ${totalNumberOfStones(125, 2)}")

    return initial.sumOf {
        totalNumberOfStones(it.toLong(), numberOfBlinks)
    }
}

fun totalNumberOfStones(currentStone: Long, depth: Int): Long {
//    println("$currentStone  DEPTH $depth")

    val cacheID = Pair(currentStone, depth)
    if (cache.contains(cacheID)) {
        println("Getting $currentStone from cache = ${cache[cacheID]}")
        return cache[cacheID]!!
    }

    if (depth == 0) {
        return 1
    }

    if (currentStone == 0L) {
        val totalNumberOfStones = totalNumberOfStones(1, depth - 1)
        cache[cacheID] = totalNumberOfStones
        return totalNumberOfStones
    }

    val currentString = currentStone.toString()
    if (currentString.length % 2 == 0) {
        val length = currentString.length
        val firstVal = currentString.substring(0, length / 2).toLong()
        val secondVal = currentString.substring(length / 2, length).toLongOrNull() ?: 0

        val total = totalNumberOfStones(firstVal, depth - 1) + totalNumberOfStones(secondVal, depth - 1)
        cache[cacheID] = total
        return total
    }

    return totalNumberOfStones(currentStone * 2024L, depth - 1)
}

//fun blinkTimes(initial: List<String>, n: Int): List<String> {
//    var current = initial
//    repeat (n) {
//        current = blink(current)
//        println(current.size)
//        if (current.size == 21789551) {
//            println(current)
//        }
//    }
//
//    return current
//}