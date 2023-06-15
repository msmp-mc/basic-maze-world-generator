package world.anhgelus.msmp.basicmazeworldgenerator.generator

import org.bukkit.generator.ChunkGenerator
import org.bukkit.generator.WorldInfo
import java.util.*
import kotlin.math.abs


class MazeGenerator: ChunkGenerator() {
    init {
        parser = MazeParser()
    }
    override fun generateNoise(worldInfo: WorldInfo, random: Random, chunkX: Int, chunkZ: Int, chunkData: ChunkData) {
        parser.placeCell(chunkX,chunkZ,chunkData, random)
    }

    override fun shouldGenerateMobs(worldInfo: WorldInfo, random: Random, chunkX: Int, chunkZ: Int): Boolean {
        return false
    }

    override fun shouldGenerateNoise(): Boolean {
        return false
    }

    override fun shouldGenerateSurface(): Boolean {
        return false
    }

    override fun shouldGenerateCaves(): Boolean {
        return false
    }

    override fun shouldGenerateDecorations(): Boolean {
        return false
    }

    override fun shouldGenerateMobs(): Boolean {
        return false
    }

    override fun shouldGenerateStructures(): Boolean {
        return false
    }

    companion object {
        lateinit var parser: MazeParser

        fun isOutside(x: Int, z: Int): Boolean {
            return abs(x) > parser.width || abs(z) > parser.height
        }
    }
}