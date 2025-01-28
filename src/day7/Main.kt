package day7

import utils.InputReaderToLines


data class Equation(val total: Long, val values: Node)
data class Node(val value: Long, val next: Node? = null)
fun interface Operator {
    fun apply(v1: Long, v2: Long): Long
}

val operators: List<Operator> = listOf(
    Operator { v1, v2 -> v1 + v2 },
    Operator { v1, v2 -> v1 * v2 },
    Operator { v1, v2 -> "$v1$v2".toLong() }
)


fun main() {
    val input = InputReaderToLines("src/day7/input.txt")
//    val input = InputReaderToLines("src/day7/inputShort.txt")

    input.lines

    val equations = input.lines.map {
        val split1 = it.split(":")
        val total = split1[0].toLong()
        val values = split1[1].trim().split(" ").map { it.toLong() }

        var root: Node? = null

        values.forEach { v ->
            if (root == null) {
                root = Node(v)
            } else {
                root = addToNode(root, v)
            }
        }

        Equation(total, root!!)
    }

    val result = equations.filter { e ->
        getAllResults(e).any { it == e.total }
    }.sumOf { it.total }

    println(result)
}

fun addToNode(node: Node?, v: Long): Node {
    if (node == null) {
        return Node(v)
    }

    return Node(node.value, addToNode(node.next, v))
}

fun getAllResults(equation: Equation): List<Long> {
    val root = equation.values

    val allResults = operators.flatMap { op ->
        val newValue = op.apply(root.value, root.next!!.value)
        getResult(newValue, root.next)
    }

    return allResults
}

fun getResult(value: Long, node: Node): List<Long> {
    if (node.next == null) { //no child, end
        return listOf(value)
    } else {
        val results = operators.flatMap {
            val newValue = it.apply(value, node.next.value)
            getResult(newValue, node.next)
        }

        return results
    }

}
