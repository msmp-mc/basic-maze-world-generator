package world.anhgelus.msmp.basicmazeworldgenerator.generator

import org.bukkit.Material
import org.bukkit.generator.ChunkGenerator.ChunkData
import world.anhgelus.msmp.basicmazeworldgenerator.BasicMazeWorldGenerator
import world.anhgelus.msmp.basicmazeworldgenerator.api.Cell
import world.anhgelus.msmp.msmpcore.utils.config.Config
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Files
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
            throw FileNotFoundException("Cannot read file: $path")
        }
        if (!file.isFile) {
            throw FileNotFoundException("File is not a file: $path")
        }
        parse()
    }

    /**
     * Parse the file and create a list of cells
     */
    private fun parse() {
        val lines = Files.readAllLines(file.toPath())
        val cells = mutableListOf<Cell>()
        height = lines.size -1
        width = (lines[0].length - 2)/2
        for ((z, line) in lines.withIndex()) {
            for ((x, char) in line.withIndex()) {
                if (char == '|' || x%2 == 0) {
                    continue
                }
                var wSouth = true
                var wEast = true
                var wWest = true
                var wTop = true
                val builder = StringBuilder()
                if (x != 1) {
                    val previous = line[x-1]
                    if (previous == ' ' || previous == '_') {
                        wWest = false
                    }
                    builder.append(previous)
                }
                if (char == ' ') {
                    wSouth = false
                }
                builder.append(char)
                if (x != line.length -1) {
                    val next = line[x+1]
                    if (next == ' ' || next == '_') {
                        wEast = false
                    }
                    builder.append(next)
                }
                if (z != 0) {
                    val previousLine = lines[z-1]
                    if (previousLine[x] == ' ') {
                        wTop = false
                    }
                    builder.append(previousLine[x])
                }
                BasicMazeWorldGenerator.LOGGER.info("West - South - East - Top")
                BasicMazeWorldGenerator.LOGGER.info("$builder, $wWest, $wSouth, $wEast, $wTop")
                // x = 2*(nX-1)
                // ((x-1)/2)+1 = nX
                val nX = ((x-1)/2)
                val fX = nX - width/2
                val fZ = z - height/2
                cells.add(Cell(fX, fZ, wTop, wSouth, wWest, wEast))
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
        if (!(chunkX in -(width/2)+1 until width/2 && chunkZ in -(height/2)+1 until height/2)) {
            return
        }
        val cell = getCell(chunkX,chunkZ)
        BasicMazeWorldGenerator.LOGGER.info("Placing cell at $chunkX, $chunkZ")
        BasicMazeWorldGenerator.LOGGER.info("Datas: ${cell.wallTop} (top), ${cell.wallEast} (east), ${cell.wallSouth}" +
                " (south), ${cell.wallWest} (west)")
        for (x in 0..15) {
            for (z in 0..15) {
                for (y in data.minHeight until 64) {
                    data.setBlock(x, y, z, Material.BLACK_CONCRETE)
                }
                if (cell.wallWest || cell.wallTop || cell.wallEast || cell.wallSouth) {
                    for (y in 65..data.maxHeight) {
                        if ((x == 0 && cell.wallSouth) ||
                            (x == 15 && cell.wallTop) ||
                            (z == 0 && cell.wallWest) ||
                            (z == 15 && cell.wallEast)
                        ) {
                            data.setBlock(x, y, z, Material.YELLOW_CONCRETE)
                        }
                    }
                }
            }
        }
    }

    /**
     * Get the cell at the given coordinates
     * @param x the x coordinate of the chunk
     * @param z the z coordinate of the chunk
     * @throws MazeGeneratorException if the cell is not found
     */
    private fun getCell(x: Int, z: Int): Cell {
        for (cell in cells) {
            if (cell.x == x && cell.z == z) {
                return cell
            }
        }
        throw MazeGeneratorException("Cell not found at $x, $z")
    }
}