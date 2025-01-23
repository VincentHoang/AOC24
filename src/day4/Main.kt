package day4

import utils.InputReaderToLines

fun main() {

//        val input = InputReaderToLines("src/day4/inputShort.txt");
    val input = InputReaderToLines("src/day4/input.txt")

    val lines = input.lines

    var count = 0;

    for (y in lines.indices) {
        val line = lines[y];

        for (x in line.indices) {
//            count += totalForOneLetter(y, x, lines)
            count += totalXmasForOneLetterPart2(y, x, lines)
        }
    }

//    println("Total: ${totalForOneLetter(0, 5, lines, "XMAS")}")
    println("Total: $count")
}

fun totalForOneLetter(y: Int, x: Int, input: List<String>): Int {
    println("Current starting letter ${input[y][x]}")
    val word = "XMAS"
    return listOf(
        matchesWord(y, x, 0, 1, input, word),
        matchesWord(y, x, 1, 1, input, word),
        matchesWord(y, x, 1, 0, input, word),
        matchesWord(y, x, 1, -1, input, word),
        matchesWord(y, x, 0, -1, input, word),
        matchesWord(y, x, -1, -1, input, word),
        matchesWord(y, x, -1, 0, input, word),
        matchesWord(y, x, -1, 1, input, word),
    ).count {
        it
    }
}


fun totalXmasForOneLetterPart2(y: Int, x: Int, input: List<String>): Int {
    println("Current starting letter ${input[y][x]}")

    val word = "MAS"

    val totalCount = listOf(
        matchesWord(y-1, x-1, 1, 1, input, word),
        matchesWord(y+1, x-1, -1, 1, input, word),
        matchesWord(y-1, x+1, 1, -1, input, word),
        matchesWord(y+1, x+1, -1, -1, input, word),
    ).count { it }

    if (totalCount > 1) {
        return 1
    }

    return 0
}

// x is horizontal direction, y is vertical direction.
// +x is right, +y is down
fun matchesWord(y: Int, x: Int, deltaY: Int, deltaX: Int, input: List<String>, word: String): Boolean {
    println("START COMPARING")

    try { // Explicit bound checking is better. i.e. check if current y + word.length is < y.length
        for (i in word.indices) {
            print(input[y + (deltaY * i)][x + (deltaX * i)])
            if (input[y + (deltaY * i)][x + (deltaX * i)] != word[i]) {
                return false
            }
        }
    } catch (e: IndexOutOfBoundsException) {
        return false
    } finally {
        println("")
    }

    return true
}