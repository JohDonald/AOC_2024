package org.johdonald.aoc2024.day24

import org.johdonald.aoc2024.utils.readInput
import org.johdonald.aoc2024.day24.operations

fun operations(input1: String, input2: String, op: String): String {
    return "($input1 $op $input2)"
}

val operators: Map<String, (Int, Int) -> Int> = mapOf(
    "AND" to { a, b -> a and b },
    "XOR" to { a, b -> a xor b },
    "OR" to { a, b -> a or b }
)

fun generateXyValueMap(): Map<String, Int> {
    val (xyValues, _) = readInput("day24").split("\n\n")
    val xyValueMap = xyValues.split("\n").map { it.split(": ") }.associate { (k, v) -> k to v.toInt() }.toMutableMap()
    return xyValueMap
}

fun generateGatesMap(): Map<String, List<String>> {
    val (_, gates) = readInput("day24").split("\n\n")
    val gateOperations = gates.split("\n").map { it.split(" -> ") }.associate { (v, k) -> k to v.split(" ") }.toMutableMap()
    return gateOperations
}

fun runCircuit(xyValueMap: Map<String, Int>, gatesMap: Map<String, List<String>>): Map<String, Int> {
    val outputsMap: MutableMap<String, Int> = xyValueMap.toMutableMap().toSortedMap()
    val gatesKeys = gatesMap.keys.toMutableList()
    var i = 0
    while (gatesKeys.size > 0) {
        val output = gatesKeys[i]
        val (input1, operator, input2) = gatesMap[output]!!
        if (input1 in outputsMap && input2 in outputsMap && output !in outputsMap) {
            outputsMap[output] = operators[operator]!!(outputsMap[input1]!!, outputsMap[input2]!!)           
            gatesKeys.removeAt(i)
        }
        i = if (i >= gatesKeys.size - 1) 0 else i + 1
    }
    return outputsMap
}

fun calculateValue(char: Char, valueMap: Map<String, Int>): Long {
    return getBinaryValue(valueMap, char).toLong(2)
}

fun getBinaryValue(valueMap: Map<String, Int>, char: Char): String {
    val binaryValue = StringBuilder()
    for ((k, v) in valueMap) {
        if (k.first() == char) {
            binaryValue.insert(0, v)
        }
    }
    return binaryValue.toString()
}

fun part1(): Long {
    val xyValueMap = generateXyValueMap()
    val gatesMap = generateGatesMap()
    val outputsMap = runCircuit(xyValueMap, gatesMap)
    return calculateValue('z', outputsMap)
}