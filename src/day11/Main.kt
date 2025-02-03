package day11

import utils.InputReaderToLines

fun main() {
//    val input = InputReaderToLines("src/day11/inputShort.txt")
    val input = InputReaderToLines("src/day11/input.txt")

    val lines = input.lines

    val initial = lines[0].split(" ")

    println(initial)
//    blinkTimes(initial, 6)
    val newList = blinkTimes(initial, 75)
    println(newList)
    println("Stones : ${newList.size}")
}

// 0 -> 1  : NOTHING
// 2233 -> 22, 33 : -> 2 then 4. so 4 stones
// else -> n * 2024 -> always becomes even digits = this many stones

// 234 * 2024 ->
enum class Type {
    ZERO, EVEN_DIGITS, OTHER
}
interface StoneOperator {
    fun shouldApply(v: String): Boolean
    fun apply(v: String): List<String>
}

class ZeroOperator : StoneOperator {
    override fun shouldApply(v: String): Boolean = v.toLong() == 0L
    override fun apply(v: String): List<String> = listOf("1")
}

class EvenOperator : StoneOperator {
    override fun shouldApply(v: String): Boolean = v.length % 2 == 0

    override fun apply(v: String): List<String> {

        return listOf(
            (v.substring(0, v.length / 2).toLong()).toString(),
            (v.substring(v.length / 2, v.length).toLongOrNull() ?: 0).toString()
        )
    }

}

class MultiplierOperator : StoneOperator {
    override fun shouldApply(v: String): Boolean = true

    override fun apply(v: String): List<String> {
        return listOf((v.toLong() * 2024).toString())
    }
}

val allOperators = listOf(ZeroOperator(), EvenOperator(), MultiplierOperator())

fun blink(initial: List<String>): List<String> {
    val newList = mutableListOf<String>()

    for (v in initial) {
        val newStones: List<String> = allOperators.first { it.shouldApply(v) }.apply(v)
        newList.addAll(newStones)
    }

    return newList
}

fun blinkTimes(initial: List<String>, n: Int): List<String> {
    var current = initial
    repeat (n) {
        current = blink(current)
        println(current.size)
        if (current.size == 21789551) {
            println(current)
        }
    }

    return current
}