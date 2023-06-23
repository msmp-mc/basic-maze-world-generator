package world.anhgelus.msmp.basicmazeworldgenerator.generator

import org.bukkit.*
import org.bukkit.block.structure.StructureRotation
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.generator.ChunkGenerator.ChunkData
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.LeatherArmorMeta
import world.anhgelus.msmp.basicmazeworldgenerator.BasicMazeWorldGenerator
import world.anhgelus.msmp.basicmazeworldgenerator.api.Cell
import world.anhgelus.msmp.basicmazeworldgenerator.utils.ConfigAPI
import world.anhgelus.msmp.basicmazeworldgenerator.utils.MathRand
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Files
import java.util.*
import kotlin.math.abs


class MazeParser {
    val path: String
    val file: File
    lateinit var cells: MutableList<Cell>
        private set
    var width = 0
        private set
    var height = 0
        private set
    var disabledSize = 0
        private set
    private val disabledCellsLoc = mutableListOf<Pair<Int,Int>>()


    init {
        val plugin = BasicMazeWorldGenerator.INSTANCE as BasicMazeWorldGenerator
        val config = ConfigAPI.getConfig("config")
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
        width = (lines[0].length - 2)/2 + 1
        var niceLine= 0
        for ((z, line) in lines.withIndex()) {
            if (z == 0) {
                continue
            }
            for ((x, char) in line.withIndex()) {
                if (x%2 == 0 || char == '|' || char == '-') {
                    continue
                }
                // x = 2*(nX-1)
                // ((x-1)/2) = nX
                val nX = ((x-1)/2)
                val fX = nX - width/2
                val fZ = -(z - height/2)

                if (char == 'X') {
                    if (niceLine == 0) niceLine = z
                    cells.add(Cell(fX, fZ, true))
                    disabledCellsLoc.add(Pair(fX,fZ))
                    if (niceLine == z) disabledSize++
                    continue
                }

                val wSouth = char == '_'
                var wEast = false
                var wWest = false
                var wTop = false

                val previous = line[x-1]
                if (previous == '|' || previous == '-') wWest = true

                if (x != line.length-1) {
                    val next = line[x+1]
                    if (next == '|' || next == '-') wEast = true
                }

                if (z != lines.size-1) {
                    val c = lines[z-1][x]
                    if (c == '_' || c == 'X') wTop = true
                }
                cells.add(Cell(fX, fZ, wTop, wSouth, wWest, wEast))
            }
        }
        if (cells.size != width*height) {
            throw MazeGeneratorException("Invalid maze size! Expected ${width*height} ($width x $height) cells, got ${cells.size}")
        }
        this.cells = cells
    }

    /**
     * Check if the cell is disabled with its coordinates
     *
     * @param x the x coordinate of the chunk
     * @param z the z coordinate of the chunk
     * @return true if the cell is disabled, false otherwise
     */
    fun isCellDisabled(x: Int, z: Int): Boolean {
        return disabledCellsLoc.contains(Pair(x,z))
    }

    /**
     * Place a cell at the given coordinates
     *
     * @param chunkX the x coordinate of the chunk
     * @param chunkZ the z coordinate of the chunk
     * @param data the chunk data
     * @param random the random generator
     */
    fun placeCell(chunkX: Int, chunkZ: Int, data: ChunkData, random: Random) {
        if (MazeGenerator.isChunkOutside(chunkX, chunkZ)) {
            BasicMazeWorldGenerator.LOGGER.warning("Cell at $chunkX $chunkZ is outside!")
            return
        }
        val cell = getCell(chunkX,chunkZ)
        if (cell.disabled) {
            BasicMazeWorldGenerator.LOGGER.warning("Cell at $chunkX $chunkZ is disabled!")
            return
        }
        for (x in 0..15) {
            for (z in 0..15) {
                for (y in data.minHeight until 65) {
                    if (y != 64) {
                        data.setBlock(x, y, z, Material.STONE)
                        continue
                    }
                    when (random.nextInt(20)) {
                        0 -> data.setBlock(x, y, z, Material.MOSSY_STONE_BRICKS)
                        1 -> data.setBlock(x, y, z, Material.CRACKED_STONE_BRICKS)
                        2 -> data.setBlock(x, y, z, Material.CRACKED_STONE_BRICKS)
                        3 -> data.setBlock(x, y, z, Material.COBBLESTONE)
                        4 -> data.setBlock(x, y, z, Material.COBBLESTONE)
                        5 -> data.setBlock(x, y, z, Material.STONE_BRICKS)
                        6 -> data.setBlock(x, y, z, Material.STONE_BRICKS)
                        7 -> data.setBlock(x, y, z, Material.STONE_BRICKS)
                        8 -> data.setBlock(x, y, z, Material.ANDESITE)
                        9 -> data.setBlock(x, y, z, Material.ANDESITE)
                        10 -> data.setBlock(x, y, z, Material.ANDESITE)
                        11 -> data.setBlock(x, y, z, Material.ANDESITE)
                        else -> data.setBlock(x, y, z, Material.STONE)
                    }
                }
                if (!(cell.wallWest || cell.wallTop || cell.wallEast || cell.wallSouth)) {
                    continue
                }
                if (!((z == 0 && cell.wallSouth) ||
                    (z == 15 && cell.wallTop) ||
                    (x == 0 && cell.wallWest) ||
                    (x == 15 && cell.wallEast))
                ) {
                    continue
                }
                for (y in 65..data.maxHeight/3) {
                    when (random.nextInt(15)) {
                        1 -> data.setBlock(x, y, z, Material.CRACKED_DEEPSLATE_BRICKS)
                        2 -> data.setBlock(x, y, z, Material.CRACKED_DEEPSLATE_BRICKS)
                        3 -> data.setBlock(x, y, z, Material.DEEPSLATE_TILES)
                        4 -> data.setBlock(x, y, z, Material.DEEPSLATE_TILES)
                        5 -> data.setBlock(x, y, z, Material.DEEPSLATE_TILES)
                        6 -> data.setBlock(x, y, z, Material.CHISELED_DEEPSLATE)
                        7 -> data.setBlock(x, y, z, Material.CRACKED_DEEPSLATE_TILES)
                        8 -> data.setBlock(x, y, z, Material.POLISHED_DEEPSLATE)
                        else -> data.setBlock(x, y, z, Material.DEEPSLATE_BRICKS)
                    }
                }
            }
        }
        if (!(cell.wallWest || cell.wallTop || cell.wallEast || cell.wallSouth)) {
            return
        }
        val coef = ConfigAPI.getConfig("config").get().getConfigurationSection("maze.coefficient")!!
        if (random.nextInt(coef.getInt("chest.spawn")) != 0) {
            return
        }
        Material.CHEST.createBlockData {
            val x: Int
            val z: Int
            if (cell.wallSouth) {
                it.rotate(StructureRotation.CLOCKWISE_180)
                x = 16/2
                z = 0
            } else if (cell.wallEast) {
                it.rotate(StructureRotation.COUNTERCLOCKWISE_90)
                x = 15
                z = 16/2
            } else if (cell.wallWest) {
                it.rotate(StructureRotation.CLOCKWISE_90)
                x = 0
                z = 16/2
            } else {
                x = 16/2
                z = 15
            }
            generateArmorStand(data, random, cell)
            data.setBlock(x, 67, z, Material.DEEPSLATE_BRICK_SLAB)
            data.setBlock(x, 66, z, it)
        }
    }

    /**
     * Get the cell at the given coordinates
     *
     * @param x the x coordinate of the chunk
     * @param z the z coordinate of the chunk
     * @throws MazeGeneratorException if the cell is not found
     */
    fun getCell(x: Int, z: Int): Cell {
        return cells.find {
            it.x == x && it.z == z
        } ?: throw MazeGeneratorException("Cell not found at $x, $z")
    }

    /**
     * Generate an armor stand in the cell
     *
     * @param data The chunk data
     * @param random The random
     * @param cell The cell
     */
    private fun generateArmorStand(data: ChunkData, random: Random, cell: Cell) {
        if (random.nextInt(2) == 0) {
            return
        }
        val x: Int
        val z: Int
        if (cell.wallSouth) {
            x = 16/2+1
            z = 0
        } else if (cell.wallEast) {
            x = 15
            z = 16/2+1
        } else if (cell.wallWest) {
            x = 0
            z = 16/2+1
        } else {
            x = 16/2+1
            z = 15
        }
        if (MazeGenerator.isBlockOutside(abs(x)-1, abs(z)-1)) return
        data.setBlock(x, 67, z, Material.AIR)
        data.setBlock(x, 66, z, Material.AIR)
        armorStands.add(SLocation(x, z, cell))
    }

    data class SLocation(val x: Int, val z: Int, val cell: Cell, var placed: Boolean = false) {
        fun toLocation(world: World): Location {
            return cell.relativeToAbsoluteLocation(world, x.toFloat(), 66f, z.toFloat())
        }
    }

    companion object {
        val armorStands = mutableListOf<SLocation>()

        /**
         * Place the armor stands
         *
         * @param world The world
         */
        fun placeArmorStands(world: World) {
            armorStands.forEach {
                if (it.placed) return@forEach
                val loc = it.toLocation(world)
                if (MazeGenerator.isBlockOutside(abs(loc.blockX)-1, abs(loc.blockZ)-1)) return@forEach
                val entity = world.spawnEntity(loc, EntityType.ARMOR_STAND) as ArmorStand
                val cell = it.cell
                if (cell.wallSouth) {
                    entity.setRotation(0f, 0f)
                } else if (cell.wallEast) {
                    entity.setRotation(90f, 0f)
                } else if (cell.wallWest) {
                    entity.setRotation(-90f, 0f)
                } else if (cell.wallTop) {
                    entity.setRotation(180f, 0f)
                }
                entity.isInvulnerable = true
                entity.setArms(true)
                entity.setBasePlate(false)

                val item = ItemStack(Material.LEATHER_HELMET)
                val data = item.itemMeta as LeatherArmorMeta
                val color = when(MathRand.zeroToFour(cell.distToCenter(), Random(world.seed+1))) {
                    0 -> DyeColor.WHITE
                    1 -> DyeColor.GRAY
                    2 -> DyeColor.GREEN
                    3 -> DyeColor.BLUE
                    else -> DyeColor.RED
                }
                data.setColor(color.color)
                item.itemMeta = data
                entity.equipment!!.helmet = item
                it.placed = true
            }
        }
    }
}