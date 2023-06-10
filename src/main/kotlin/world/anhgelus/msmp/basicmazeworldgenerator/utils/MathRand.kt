package world.anhgelus.msmp.basicmazeworldgenerator.utils

import java.util.*
import kotlin.math.floor

object MathRand {
    /**
     * Return a random number between 0 and 4 (included) based on the distance to the center of the maze
     *
     * @param dist The distance to the center of the maze
     * @param random The random object
     */
    fun zeroToFour(dist: Int, random: Random): Int {
        val coef = ConfigAPI.getConfig("config").get().getInt("maze.coefficient.chest.dist")
        val dist = floor((dist/coef).toDouble()).toInt()
        if (dist < 1) return 0
        val tr = random.nextInt(dist+1)
        return if (tr < 5) tr else 4
    }
}