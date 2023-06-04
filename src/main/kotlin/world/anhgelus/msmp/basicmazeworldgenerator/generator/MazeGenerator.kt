package world.anhgelus.msmp.basicmazeworldgenerator.generator

import org.bukkit.generator.ChunkGenerator
import org.bukkit.generator.WorldInfo
import java.util.*


class MazeGenerator: ChunkGenerator() {
    var mazeParser = MazeParser()
    override fun generateNoise(worldInfo: WorldInfo, random: Random, chunkX: Int, chunkZ: Int, chunkData: ChunkData) {
        mazeParser.placeCell(chunkX,chunkZ,chunkData, random)
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
}