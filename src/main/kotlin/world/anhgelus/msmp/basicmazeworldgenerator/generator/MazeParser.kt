package world.anhgelus.msmp.basicmazeworldgenerator.generator

import org.bukkit.Material
import org.bukkit.generator.ChunkGenerator.ChunkData
import world.anhgelus.msmp.basicmazeworldgenerator.BasicMazeWorldGenerator
import world.anhgelus.msmp.basicmazeworldgenerator.api.Cell
import world.anhgelus.msmp.basicmazeworldgenerator.utils.FileHelper
import world.anhgelus.msmp.msmpcore.utils.config.Config
import java.io.File
import java.util.Random


class MazeParser {
    val path: String
    val file: File
    lateinit var cells: MutableList<Cell>
        private set
    var width = 0
        private set
    var height = 0
        private set


    init {
        val plugin = BasicMazeWorldGenerator.INSTANCE as BasicMazeWorldGenerator
        val config = Config(plugin, "config")
        path = config.get().getString("maze.path","maze.txt")!!
        file = File(plugin.dataFolder.path+ File.separator + path)
        if (!file.canRead()) {
            throw Exception("Cannot read file: $path")
        }
        if (!file.isFile) {
            throw Exception("File is not a file: $path")
        }
        parse()
    }

    /**
     * Parse the file and create a list of cells
     */
    private fun parse() {
        val content = FileHelper.readFile(file.inputStream())
        val lines = content.split("\n")
        val cells = mutableListOf<Cell>()
        height = lines.size -1
        for ((z, line) in lines.withIndex()) {
            for ((x, char) in line.withIndex()) {
                if (char == '|') {
                    continue
                }
                var wSouth = true
                var wEast = true
                var wWest = true
                var wTop = true
                if (char == ' ') {
                     wSouth = false
                }
                if (x != line.length -1) {
                    val next = line[x+1]
                    if (next == ' ') {
                        wEast = false
                    }
                } else {
                    width = x
                }
                if (x != 0) {
                    val previous = line[x-1]
                    if (previous == ' ') {
                        wWest = false
                    }
                }
                if (z != 0) {
                    val previousLine = lines[z-1]
                    if (previousLine[x] == ' ') {
                        wTop = false
                    }
                }
                // x = 2*(nX-1) + 1
                // ((x-1)/2)+1 = nX
                val nX = ((x-1)/2)+1
                cells.add(Cell(nX, z, wTop, wSouth, wWest, wEast))
            }
        }
        this.cells = cells
    }

    /**
     * Place a cell at the given coordinates
     *
     * @param x the x coordinate of the chunk
     * @param z the z coordinate of the chunk
     * @param data the chunk data
     */
    fun placeCell(chunkX: Int, chunkZ: Int, data: ChunkData, random: Random) {
        if (chunkX > width/2 || chunkZ > height/2 || chunkX < -width/2 || chunkZ < -height/2) {
            return
        }
        val id = genIDFromXZ(chunkX,chunkZ)
        val cell = cells[id]
        for (x in 0..15) {
            for (z in 0..15) {
                if (cell.wallWest || cell.wallTop || cell.wallEast || cell.wallSouth) {
                    for (y in 65..data.maxHeight) {
                        if ((x == 0 && cell.wallSouth) ||
                            (x == 15 && cell.wallSouth) ||
                            (z == 0 && cell.wallWest) ||
                            (z == 15 && cell.wallEast)
                        ) {
                            data.setBlock(x, y, z, Material.YELLOW_CONCRETE)
                        }
                    }
                    continue
                }
                data.setBlock(x, 65, z, Material.BLACK_CONCRETE)
            }
        }
    }

    fun genIDFromXZ(x: Int, z: Int): Int {
        return (z+z/2)*width + (x+x/2)
    }
}