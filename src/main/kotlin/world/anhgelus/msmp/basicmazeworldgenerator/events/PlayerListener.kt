package world.anhgelus.msmp.basicmazeworldgenerator.events

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.Chest
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.loot.LootContext
import world.anhgelus.msmp.basicmazeworldgenerator.generator.MazeGenerator
import world.anhgelus.msmp.basicmazeworldgenerator.utils.loottables.LootTablesHelper
import world.anhgelus.msmp.msmpcore.utils.ChatHelper
import java.util.*

object PlayerListener: Listener {

    private val openedChests = mutableSetOf<Chest>()

    private var dpEnabled = false

    @EventHandler
    fun onChestOpen(event: PlayerInteractEvent) {
        if (event.clickedBlock == null) return
        if (event.clickedBlock?.type != Material.CHEST) return
        val chest = event.clickedBlock!!.state as Chest
        if (chest.lootTable != null) return
        for (i in 0 until chest.inventory.size) {
            if (chest.inventory.getItem(i) != null) {
                return
            }
        }
        if (openedChests.contains(chest)) return
        // set the loot tables the inventory
        chest.lootTable = LootTablesHelper.getChestLootTable(chest.location)
        chest.update()
        openedChests.add(chest)
    }

    @EventHandler
    fun onBreakBlock(event: BlockBreakEvent) {
        val loc = event.block.location
        val x = if (loc.blockX < 0) {
            ((loc.blockX%16)+16)%16
        } else {
            loc.blockX%16
        }
        val y = loc.blockY
        val z = if (loc.blockZ < 0) {
            ((loc.blockZ%16)+16)%16
        } else {
            loc.blockZ%16
        }
        if (y < 64) {
            event.isCancelled = true
            return
        }
        if (!(x == 0 || x == 15 || z == 0 || z == 15)) {
            println("x: $x, z: $z")
            return
        }
        val cell = MazeGenerator.mazeParser.getCell(loc.chunk.x, loc.chunk.z)
        println("${cell.wallSouth} ${cell.wallWest} ${cell.wallTop} ${cell.wallEast}")
        if (!((z == 0 && cell.wallSouth) ||
            (z == 15 && cell.wallTop) ||
            (x == 0 && cell.wallWest) ||
            (x == 15 && cell.wallEast))
        ) {
            return
        }
        event.isCancelled = true
    }

    @EventHandler
    fun onBlockPosed(event: BlockPlaceEvent) {
        val y = event.block.location.blockY
        event.isCancelled = y > 106-3
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        if (dpEnabled) return
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "datapack list")
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "datapack enable \"file/basicmazeworldgenerator\"")
        dpEnabled = true
    }
}