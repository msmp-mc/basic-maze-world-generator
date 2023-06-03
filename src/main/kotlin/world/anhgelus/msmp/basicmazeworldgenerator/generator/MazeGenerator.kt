package world.anhgelus.msmp.basicmazeworldgenerator.generator

import org.bukkit.generator.ChunkGenerator
import org.bukkit.generator.WorldInfo
import java.util.Random


class MazeGenerator: ChunkGenerator() {
    var mazeParser = MazeParser()
    override fun generateNoise(worldInfo: WorldInfo, random: Random, chunkX: Int, chunkZ: Int, chunkData: ChunkData) {
        mazeParser.placeCell(chunkX,chunkZ,chunkData, random)
    }
}