package day8

import utils.InputReaderToLines

fun main() {
    val input = InputReaderToLines("src/day9/input.txt")
//    val input = InputReaderToLines("src/day9/inputShort.txt")

    input.lines


    val blocks = input.lines[0].flatMapIndexed { i, c ->
        val digit = c.digitToInt()
        val type = i % 2

//        println(i)
        if (type == 0) { //file if index of disk map is even
            List(digit) {
                val value = i / 2
                value.toLong()
            }
        } else { // free space
            List(digit) { null }
        }
    }

    println(blocks.joinToString(",") {
        it?.toString() ?: "."
    })

    val compact = compact(blocks)
//    println(compact.joinToString(""))
    println(compact.joinToString(",") {
        it?.toString() ?: "."
    })

    val result = compact.mapIndexed { i, it ->
        if (it != null) {
            println("$it * $i = ${it * i}")
            it * i
        } else 0
    }.sum()
    println(result)

    println("${blocks.size}  vs   ${compact.size}")

}

fun compact(blocks: List<Long?>): List<Long?> {
    val newList: MutableList<Long?> = MutableList(blocks.size) { null }

    var lastAvailableIndex = blocks.size
    blocks.forEachIndexed { i, c ->

//        println("stop?: $i vs $lastAvailableIndex")
        if (i >= lastAvailableIndex) { // TODO I should break or something
//            println("yes")
        } else {
            if (c == null) {
                lastAvailableIndex = getLastInt(blocks, lastAvailableIndex - 1)
//                println(lastAvailableIndex)
                newList[i] = blocks[lastAvailableIndex]
            } else {
                newList[i] = c
            }
        }

    }

    return newList
}

fun getLastInt(list: List<Long?>, currentLast: Int): Int {
    for (i in currentLast downTo 0) {
        if (list[i] != null) {
            return i;
        }
    }

    return -1
}

