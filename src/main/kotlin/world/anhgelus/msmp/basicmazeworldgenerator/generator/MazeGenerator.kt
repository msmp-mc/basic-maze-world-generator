package world.anhgelus.msmp.basicmazeworldgenerator.generator

import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.generator.ChunkGenerator
import org.bukkit.generator.WorldInfo
import java.util.*
import kotlin.math.abs


class MazeGenerator: ChunkGenerator() {
    override fun generateNoise(worldInfo: WorldInfo, random: Random, chunkX: Int, chunkZ: Int, chunkData: ChunkData) {
        if (worldInfo.name.endsWith("_nether") || worldInfo.name.endsWith("_the_end")) return
        if (isInHole(chunkX, chunkZ) || isChunkOutside(chunkX, chunkZ)) return
        parser.placeCell(chunkX,chunkZ,chunkData, random)
    }

    override fun shouldGenerateNoise(worldInfo: WorldInfo, random: Random, chunkX: Int, chunkZ: Int): Boolean {
        return shouldGenerate(worldInfo, chunkX, chunkZ)
    }

    override fun shouldGenerateSurface(worldInfo: WorldInfo, random: Random, chunkX: Int, chunkZ: Int): Boolean {
        return shouldGenerate(worldInfo, chunkX, chunkZ)
    }

    override fun shouldGenerateCaves(worldInfo: WorldInfo, random: Random, chunkX: Int, chunkZ: Int): Boolean {
        return shouldGenerate(worldInfo, chunkX, chunkZ)
    }

    override fun shouldGenerateDecorations(worldInfo: WorldInfo, random: Random, chunkX: Int, chunkZ: Int): Boolean {
        return shouldGenerate(worldInfo, chunkX, chunkZ)
    }

    override fun shouldGenerateStructures(worldInfo: WorldInfo, random: Random, chunkX: Int, chunkZ: Int): Boolean {
        return shouldGenerate(worldInfo, chunkX, chunkZ)
    }

    private fun shouldGenerate(worldInfo: WorldInfo, chunkX: Int, chunkZ: Int): Boolean {
        if (worldInfo.name.endsWith("_nether") || worldInfo.name.endsWith("_the_end")) return false
        if (isChunkOutside(chunkX, chunkZ)) return false
        return isInHole(chunkX, chunkZ)
    }

    /*
     * Just here because we must put it here
     */
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

    override fun shouldGenerateStructures(): Boolean {
        return false
    }

    companion object {
        lateinit var parser: MazeParser

        fun isBlockOutside(x: Int, z: Int): Boolean {
            return abs(x) > parser.width*8 || abs(z) > parser.height*8
        }

        fun isChunkOutside(x: Int, z: Int): Boolean {
            return abs(x)+1 > parser.width/2 || abs(z)+1 > parser.height/2
        }

        fun isInHole(x: Int, z: Int): Boolean {
            if (isChunkOutside(x, z)) return false
            return parser.isCellDisabled(x, z)
        }

        fun isInHole(location: Location): Boolean {
            return isInHole(location.chunk)
        }

        fun isInHole(chunk: Chunk): Boolean {
            return isInHole(chunk.x, chunk.z)
        }
    }
}