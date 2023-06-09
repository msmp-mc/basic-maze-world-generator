package world.anhgelus.msmp.basicmazeworldgenerator.events

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.Chest
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.loot.LootContext
import world.anhgelus.msmp.basicmazeworldgenerator.generator.MazeGenerator
import world.anhgelus.msmp.basicmazeworldgenerator.utils.LootTablesHelper
import world.anhgelus.msmp.basicmazeworldgenerator.utils.LootTablesType
import java.util.*

class PlayerListener: Listener {
    @EventHandler
    fun onChestOpen(event: PlayerInteractEvent) {
        if (event.clickedBlock == null) return
        if (event.clickedBlock?.type != Material.CHEST) return
        val chest = event.clickedBlock!!.state as Chest
        if (chest.inventory.size != 0) return
        if (chest.lootTable == null) return
        val player = event.player
        // set the loot table
        Bukkit.getLootTable(LootTablesHelper.genKey(LootTablesType.CHEST, "maze"))!!
            .fillInventory(chest.blockInventory, Random(player.world.seed), LootContext.Builder(player.location).build())
    }

    @EventHandler
    fun onBreakBlock(event: BlockBreakEvent) {
        val loc = event.block.location
        val x = loc.blockX%16
        val y = loc.blockY
        val z = loc.blockZ%16
        if (y < 65) {
            event.isCancelled = true
            return
        }
        if (!(x == 0 || x == 15 || z == 0 || z == 15)) {
            return
        }
        val cell = MazeGenerator.mazeParser.getCell(loc.chunk.x, loc.chunk.z)
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
        val loc = event.block.location
        val y = loc.blockY
        event.isCancelled = y > (384/3)-3
    }
}