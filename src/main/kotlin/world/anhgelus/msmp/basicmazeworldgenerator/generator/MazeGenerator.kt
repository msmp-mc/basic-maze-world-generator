package world.anhgelus.msmp.basicmazeworldgenerator.generator

import org.bukkit.generator.ChunkGenerator
import org.bukkit.generator.WorldInfo
import java.util.*
import kotlin.math.abs


class MazeGenerator: ChunkGenerator() {
    override fun generateNoise(worldInfo: WorldInfo, random: Random, chunkX: Int, chunkZ: Int, chunkData: ChunkData) {
        parser.placeCell(chunkX,chunkZ,chunkData, random)
    }

    override fun shouldGenerateNoise(worldInfo: WorldInfo, random: Random, chunkX: Int, chunkZ: Int): Boolean {
        return shouldGenerate(chunkX, chunkZ)
    }

    override fun shouldGenerateSurface(worldInfo: WorldInfo, random: Random, chunkX: Int, chunkZ: Int): Boolean {
        return shouldGenerate(chunkX, chunkZ)
    }

    override fun shouldGenerateCaves(worldInfo: WorldInfo, random: Random, chunkX: Int, chunkZ: Int): Boolean {
        return shouldGenerate(chunkX, chunkZ)
    }

    override fun shouldGenerateDecorations(worldInfo: WorldInfo, random: Random, chunkX: Int, chunkZ: Int): Boolean {
        return shouldGenerate(chunkX, chunkZ)
    }

    override fun shouldGenerateStructures(worldInfo: WorldInfo, random: Random, chunkX: Int, chunkZ: Int): Boolean {
        return shouldGenerate(chunkX, chunkZ)
    }

    private fun shouldGenerate(chunkX: Int, chunkZ: Int): Boolean {
        if (isOutside(chunkX, chunkZ)) return false
        return isInHole(chunkX, chunkZ)
    }

    companion object {
        lateinit var parser: MazeParser

        fun isOutside(x: Int, z: Int): Boolean {
            return abs(x) > parser.width*8 || abs(z) > parser.height*8
        }

        fun isInHole(x: Int, z: Int): Boolean {
            parser.getCell(x/8,z/8).let {
                return !it.disabled
            }
        }
    }
}