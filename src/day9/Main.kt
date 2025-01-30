package day9

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

    println(blocks.joinToString(" ") {
        it?.toString() ?: "."
    })

    var compact = blocks
    compact = compact3(compact)

//    println(compact.joinToString(" "))
    println(compact.joinToString(" ") {
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

fun compact3(blocks: List<Long?>): List<Long?> {
    val newList: MutableList<Long?> = blocks.toMutableList()
    var lastAvailableIndex = blocks.size - 1


    while(lastAvailableIndex >= 0) {
        val moveValues = findRightMostValues(newList, lastAvailableIndex)

        val leftMostSpace = findLeftMostSpace(newList, moveValues!!)
//        println("LEFT MOST SPACE $leftMostSpace")

        if (leftMostSpace >= 0) {
            newList.setAll(blocks, leftMostSpace, moveValues)
        } else {
//            println("no move")
        }

//        println(newList.joinToString(" ") {
//            it?.toString() ?: "."
//        })

        lastAvailableIndex = moveValues.startIndex - moveValues.n
    }


    return newList
}

fun findLeftMostSpace(list: List<Long?>, subList: Move): Int {
    println("Finding left most space for $subList")
    for (i in 0 until subList.startIndex) {
        if (isEnoughSpace(subList, list, i)) {
            return i
        }
    }

    return -1
}

private fun isEnoughSpace(subList: Move, list: List<Long?>, i: Int): Boolean {
    if (i + subList.n > list.size) {
        return false
    }

    for (j in 0 until subList.n) {
        if (list[i + j] != null) {
            return false
        }
    }

    return true
}

//fun compact2(blocks: List<Long?>): List<Long?> {
//    val newList: MutableList<Long?> = blocks.toMutableList()
//
//    var lastAvailableIndex = blocks.size - 1
//    var i = 0
//
//    while (i < blocks.size) {
//        val c = blocks[i]
//
////        println("stop?: $i vs $lastAvailableIndex")
//        if (i >= lastAvailableIndex) {
//            println("BREAKING AT $i   : $lastAvailableIndex")
//            break
//        } else {
//            if (c == null) {
//                println(newList.joinToString(" ") {
//                    it?.toString() ?: "."
//                })
//
//                val moveValues = getLastInts(newList, lastAvailableIndex)
////                println(lastAvailableIndex)
//
////                newList[i] = blocks[lastAvailableIndex]
//                if (moveValues != null) {
//                    val incrementBy = newList.setAll(newList, i, moveValues)
//
////                    if (incrementBy == moveValues.size) {
////
////                    }
//
//                    i += incrementBy
//                    lastAvailableIndex -= moveValues.n
//                    println("shifting lastAvailableIndex: $lastAvailableIndex")
//                } else {
//                    i++
//                }
//            } else {
//                newList[i] = c
//                i++
//            }
//        }
//
//    }
//
//    return newList
//}

data class Move(val n: Int, val value: Long, val startIndex: Int)

fun findRightMostValues(list: List<Long?>, currentLastIndex: Int): Move? {
    var n = 0
    var found: Long? = null
    var startIndex: Int? = null
    for (i in currentLastIndex downTo 0) {
        val value = list[i]
        if (value != null) {
            if (found == null) {
                n++
                found = value
                startIndex = i
            } else if (found == value) {
                n++
            } else {
                return Move(n, found, startIndex!!)
            }
        }
    }

    return found?.let { Move(n, found, startIndex!!) }
}

fun MutableList<Long?>.setAll(blocks: List<Long?>, startingIndex: Int, toAdd: Move): Int {
    println("$startingIndex -> Moving: $toAdd")
    for (i in 0 until toAdd.n) {
        if (blocks[startingIndex + i] != null) {
            println("OOPS no space for $toAdd. Only has ${i} spaces")
            return i
        }
    }

    for (i in 0 until toAdd.n) {
        this[startingIndex + i] = toAdd.value
        println("removing ${toAdd.value} at ${toAdd.startIndex - i}")
        this[toAdd.startIndex - i] = null
    }

    return toAdd.n
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
            return i
        }
    }

    return -1
}
