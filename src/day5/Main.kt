package day5

import utils.InputReaderToLines

fun main() {
    val input = InputReaderToLines("src/day5/input.txt")
//    val input = InputReaderToLines("src/day5/inputShort.txt")

    val lines = input.lines

    var i = 0;

    val orderingRules = mutableMapOf<Int, List<Int>>()


    // process ordering rules
    while (i<lines.size) {

        val line = lines[i]

        if (line.isEmpty()) {
            i++
            break;
        }

        val split = line.split("|")
        val key = split[0].toInt()
        val value = split[1].toInt()

        val node: List<Int> = orderingRules.getOrDefault(key, emptyList())
        orderingRules.put(key, node + value)

        i++
    }

    println(orderingRules)

    println("--BREAK--")
    val updates = mutableListOf<List<Int>>()
    while (i < lines.size ) {
        val line = lines[i]

        updates.add(line.split(",").map { it.toInt() })
        i++
    }


    /////////////////////////////////////////

    val result = updates.sumOf {
        getListValue(it, orderingRules)
    }

    println(result)
}

fun getListValue(line: List<Int>, rules: Map<Int, List<Int>>): Int {
    return middleValue(reorder(line, rules))
}

fun reorder(line: List<Int>, rules: Map<Int, List<Int>>): List<Int> {
    var currentList = line

    var newList = flip(currentList, rules)

    if (newList == currentList) {
        return emptyList()
    }

    while (newList != currentList) {
        currentList = newList
        newList = flip(currentList, rules)
    }

    return newList
}

fun flip(line: List<Int>, rules: Map<Int, List<Int>>): List<Int> {
    // compare what's occurred with the what's in the children
    print("ORDER????: $line ")
    val occurredValues = mutableSetOf<Int>()

    line.forEachIndexed { i, n ->
        rules[n]?.let { children ->
            val valuesOutOfOrder = children.intersect(occurredValues)

            //Do reorder
            if (valuesOutOfOrder.isNotEmpty()) {
                val newList = mutableListOf<Int>()
                val valuesToMove = mutableListOf<Int>()

                line.forEach {
                    if (valuesOutOfOrder.contains(it)) {
                        valuesToMove.add(it)
                    } else {
                        newList.add(it)
                    }
                }

                valuesToMove.forEachIndexed { j, n ->
                    newList.add((i-valuesToMove.size+1) + j, n)
                }

                return newList
            }

            ///////
        }

        occurredValues.add(n)
    }

    println(true)
    return line
}

fun isOrdered(line: List<Int>, rules: Map<Int, List<Int>>): Boolean {
    // compare what's occurred with the what's in the children
    print("ORDER????: $line ")
    val occurredValues = mutableSetOf<Int>()

    line.forEach { n ->
        rules[n]?.let { children ->
            if (children.intersect(occurredValues).isNotEmpty()) {
                println("false")
                return false
            }
        }

        occurredValues.add(n)
    }

    println(true)
    return true
}

fun middleValue(line: List<Int>): Int {
    return if (line.isEmpty()) 0 else line[line.size/2]
}
