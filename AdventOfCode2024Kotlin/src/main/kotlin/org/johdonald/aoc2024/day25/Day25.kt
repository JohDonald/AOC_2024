package org.johdonald.aoc2024.day25

import org.johdonald.aoc2024.utils.readInput
    
fun part1(): Int {
    val input = readInput("day25").split("\n\n").map { it.split("\n") }
    val (locks, keys) = input.partition { it -> it.first().all { it == '#' } && it.last().all { it == '.' } }
    val locksPinHeights = locks.map { calculatePinHeights(it) }
    val keysPinHeights = keys.map { calculatePinHeights(it.reversed()) }
    var matches = 0
    for (lock in locksPinHeights) {
        for (key in keysPinHeights) {
            val isMatch = lock.zip(key).all { (lockPin, keyPin) -> 
                lockPin + keyPin < 6
            }
            if (isMatch) {
                matches++
            }
        }
    }
    return matches
}

fun calculatePinHeights(lockOrKey: List<String>): List<Int>{
    val pinHeights: MutableList<Int> = MutableList(lockOrKey[0].length) { -1 }
    lockOrKey.map { row -> row.forEachIndexed { i, c -> 
        if (c == '#') {
            pinHeights[i]++
        } 
    } }
    return pinHeights
}