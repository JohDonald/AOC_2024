package org.johdonald.aoc2024.day24

import org.johdonald.aoc2024.utils.readInput

val operators: Map<String, (Int, Int) -> Int> = mapOf(
    "AND" to { a, b -> a and b },
    "XOR" to { a, b -> a xor b },
    "OR" to { a, b -> a or b }
)

fun part1(): Long {
    val (wires, gates) = readInput("day24").split("\n\n")
    val wireValueMap = wires.split("\n").map { it.split(": ") }.associate { (k, v) -> k to v.toInt() }.toMutableMap().toSortedMap()
    val gateOperations = gates.split("\n").map { it.split(" ") }.toMutableList()
    var i = 0
    while (gateOperations.size > 0) {
        val (input1, operator, input2, _, output) = gateOperations[i]
        if (input1 in wireValueMap && input2 in wireValueMap && output !in wireValueMap) {
            wireValueMap[output] = operators.getValue(operator).invoke(wireValueMap[input1]!!, wireValueMap[input2]!!)
            gateOperations.removeAt(i)
        }
        i = if (i >= gateOperations.size - 1) 0 else i + 1
    }
    val binaryValue = getBinaryValue(wireValueMap, 'z')
    return binaryValue.toLong(2)
}

fun getBinaryValue(wireValueMap: Map<String, Int>, char: Char): String {
    val binaryValue = StringBuilder()
    for ((k, v) in wireValueMap) {
        if (k.first() == char) {
            binaryValue.insert(0, v)
        }
    }
    return binaryValue.toString()
}