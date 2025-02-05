package day13

import utils.InputReaderToLines
import java.math.BigDecimal

data class Button(val x: Long, val y: Long)
data class Prize(val x: Long, val y: Long)

data class Result(val a: BigDecimal, val b: BigDecimal)
fun main() {
//    val input = InputReaderToLines("src/day13/inputShort.txt")
    val input = InputReaderToLines("src/day13/input.txt")

    input.lines


    var i = 0

    var total = BigDecimal.ZERO
    while (i < input.lines.size) {
        val buttonA = findButton(input.lines[i])
        println(buttonA)
        i++
        val buttonB = findButton(input.lines[i])
        println(buttonB)
        i++
        val prize = findPrizePart2(input.lines[i])
//        val prize = findPrize(input.lines[i])
        println(prize)
        i++
        val result = simultaneousEquation(buttonA, buttonB, prize)
        println(result)

        result?.let {
            val cost = (it.a * BigDecimal(3)) + (it.b)
            println("Cost = $cost")
            total += cost
        }
        println("")
        i++

    }

    println("total cost: $total")
}

fun findButton(l: String): Button {
    val regexX = Regex("X\\+(\\d+)")
    val regexY = Regex("Y\\+(\\d+)")

    val xVal = regexX.find(l)!!.groupValues[1].toLong()
    val yVal = regexY.find(l)!!.groupValues[1].toLong()
    return Button(xVal, yVal)
}

fun findPrize(l: String): Prize {
    val regexX = Regex("X=(\\d+)")
    val regexY = Regex("Y=(\\d+)")

    val xVal = regexX.find(l)!!.groupValues[1].toLong()
    val yVal = regexY.find(l)!!.groupValues[1].toLong()

    return Prize(xVal, yVal)
}

fun findPrizePart2(l: String, addition: Long = 10000000000000): Prize {
    val regexX = Regex("X=(\\d+)")
    val regexY = Regex("Y=(\\d+)")

    val xVal = regexX.find(l)!!.groupValues[1].toLong() + addition
    val yVal = regexY.find(l)!!.groupValues[1].toLong() + addition

    return Prize(xVal, yVal)
}


fun simultaneousEquation(buttonA: Button, buttonB: Button, prize: Prize): Result? {
    /**
     *  (buttonA.x * a) + (buttonB.x * b) = prize.x
     *  (buttonA.y * a) + (buttonB.y * b) = prize.y
     *
     *  94a + 22b = 8400
     *  34a + 67b = 5400
     *
     *
     */

    // Coefficients for equation 1: 94a + 22b = 8400
    val a1 = BigDecimal(buttonA.x) //94
    val b1 = BigDecimal(buttonB.x) //22
    val c1 = BigDecimal(prize.x) // 8400

    // Coefficients for equation 2: 34a + 67b = 5400
    val a2 = BigDecimal(buttonA.y) //34
    val b2 = BigDecimal(buttonB.y) //67
    val c2 = BigDecimal(prize.y) //5400

    // Step 1: Calculate the determinant (denominator)
    val denominator = (a1 * b2) - (a2 * b1)

    // Step 2: Calculate the determinant for 'a' (replace the first column with the results)
    val numeratorA = (c1 * b2) - (c2 * b1)

    // Step 3: Calculate the determinant for 'b' (replace the second column with the results)
    val numeratorB = (a1 * c2) - (a2 * c1)

    // Step 4: Solve for 'a' and 'b'

    if (numeratorA.remainder(denominator) == BigDecimal.ZERO) {
        if (numeratorB.remainder(denominator) == BigDecimal.ZERO) {
            val a = numeratorA / denominator
            val b = numeratorB / denominator

            return Result(a, b)
        }
    }



//    // Multiply equation 1 by 34 and equation 2 by 94 to align coefficients of 'a'
//    val multiplier1 = a2
//    val multiplier2 = a1
//
//    // Equation 1 after multiplication: 34 * (94a + 22b) = 34 * 8400
//    val newA1 = multiplier1 * a1
//    val newB1 = multiplier1 * b1
//    val newC1 = multiplier1 * c1
//
//    // Equation 2 after multiplication: 94 * (34a + 67b) = 94 * 5400
//    val newA2 = multiplier2 * a2
//    val newB2 = multiplier2 * b2
//    val newC2 = multiplier2 * c2
//
//    // Now, subtract one equation from the other to eliminate 'a'
//    val newB = newB1 * newA2 - newB2 * newA1
//    val newC = newC1 * newA2 - newC2 * newA1
//
//    // Solve for 'b'
//    if (newC.remainder(newB) == BigDecimal.ZERO) {
//        val b = newC / newB
//
//        if ((c1 - b1 * b).remainder(a1) == BigDecimal.ZERO) {
//            // Now substitute the value of 'b' into one of the original equations to solve for 'a'
//            val a = (c1 - b1 * b) / a1
//
//            return Result(a, b)
//        }
//    }





    return null

}