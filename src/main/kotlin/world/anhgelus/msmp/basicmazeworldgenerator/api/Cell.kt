package world.anhgelus.msmp.basicmazeworldgenerator.api

import kotlin.math.abs

data class Cell(
    val x: Int,
    val z: Int,
    val wallTop: Boolean,
    val wallSouth: Boolean,
    val wallWest: Boolean,
    val wallEast: Boolean,
) {
    fun distToCenter(): Int {
        return 16*(abs(x) + abs(z))
    }
}
