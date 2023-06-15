package world.anhgelus.msmp.basicmazeworldgenerator.api

import org.bukkit.Location
import org.bukkit.World
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

    fun relativeToAbsoluteLocation(world: World, x: Float, y: Float, z: Float): Location {
        val cX = if (x < 0) {
            (this.x-1)*16+x
        } else {
            x+this.x*16
        } + 0.5

        val cZ = if (z < 0) {
            (this.z-1)*16+z
        } else {
            z+this.z*16
        } + 0.5
        return Location(world, cX, y.toDouble(), cZ)
    }
}
