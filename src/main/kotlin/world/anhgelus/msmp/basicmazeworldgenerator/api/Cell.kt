package world.anhgelus.msmp.basicmazeworldgenerator.api

import org.bukkit.Location
import org.bukkit.World
import kotlin.math.abs

/**
 * Represents a cell in the maze
 *
 * @param x The x coordinate of the cell
 * @param z The z coordinate of the cell
 * @param wallTop Whether the cell has a wall on the top
 * @param wallSouth Whether the cell has a wall on the south
 * @param wallWest Whether the cell has a wall on the west
 * @param wallEast Whether the cell has a wall on the east
 * @param disabled Whether the cell is disabled
 */
data class Cell(
    val x: Int,
    val z: Int,
    val wallTop: Boolean,
    val wallSouth: Boolean,
    val wallWest: Boolean,
    val wallEast: Boolean,
    val disabled: Boolean,
) {

    constructor(x: Int, z: Int, disabled: Boolean): this(x, z, true, true, true, true, disabled)
    constructor(x: Int, z: Int, wallTop: Boolean, wallSouth: Boolean, wallWest: Boolean, wallEast: Boolean):
            this(x, z, wallTop, wallSouth, wallWest, wallEast, false)

    /**
     * Calculates the distance to the center of the maze
     *
     * @return the dist to the center
     */
    fun distToCenter(): Int {
        return 16*(abs(x) + abs(z))
    }

    /**
     * Converts relative coordinates of a cell into absolute coordinates
     *
     * @param world the world to convert to
     * @param x the relative x coordinate
     * @param y the relative y coordinate
     * @param z the relative z coordinate
     * @return the absolute location
     */
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
