package org.johdonald.aoc2024.utils

import kotlin.io.path.Path
import kotlin.io.path.readText

fun readInput(day: String) = Path("src/main/resources/input_$day.txt").readText().trim()