package world.anhgelus.msmp.basicmazeworldgenerator.events

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.block.Chest
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerPortalEvent
import world.anhgelus.msmp.basicmazeworldgenerator.generator.MazeGenerator
import world.anhgelus.msmp.basicmazeworldgenerator.generator.MazeGeneratorException
import world.anhgelus.msmp.basicmazeworldgenerator.utils.loottables.LootTablesHelper

object PlayerListener : Listener {

    private val openedChests = mutableSetOf<Chest>()

    private var dpEnabled = false

    @EventHandler
    fun onChestOpen(event: PlayerInteractEvent) {
        val wName = event.player.location.world!!.name
        if (wName.endsWith("_nether") || wName.endsWith("_the_end")) return
        if (event.clickedBlock == null) return
        if (event.clickedBlock?.type != Material.CHEST) return
        val chest = event.clickedBlock!!.state as Chest
        if (chest.lootTable != null) return
        if (MazeGenerator.isInHole(chest.location)) return
        if (openedChests.contains(chest)) return
        var x = chest.location.blockX%16
        var z = chest.location.blockZ%16
        if (x < 0) {
           x = (x+16)%16
        }
        if (z < 0) {
            z = (z+16)%16
        }
        if (!(x == 0 || z == 0 || x == 15 || z == 15)) return
        for (i in 0 until chest.inventory.size) {
            if (chest.inventory.getItem(i) != null) {
                return
            }
        }
        // set the loot tables the inventory
        chest.lootTable = LootTablesHelper.getChestLootTable(chest.location)
        chest.update()
        openedChests.add(chest)
    }

    @EventHandler
    fun onBreakBlock(event: BlockBreakEvent) {
        if (MazeGenerator.isInHole(event.block.location)) return
        if (event.block.location.blockY < 64) {
            event.isCancelled = true
            return
        }
        blockEvent(event)
    }

    @EventHandler
    fun onPlaceBlock(event: BlockPlaceEvent) {
        if (MazeGenerator.isInHole(event.block.location)) return
        if (event.blockPlaced.location.blockY > 106-3) {
            event.isCancelled = true
            breakBlock(event)
            return
        }
        blockEvent(event)
    }

    private fun breakBlock(event: BlockPlaceEvent) {
        val item = event.itemInHand
        if (event.player.gameMode != GameMode.CREATIVE) item.amount++
        event.player.inventory.setItemInMainHand(item)
        event.block.type = Material.AIR
    }

    private fun blockEvent(event: BlockEvent) {
        if (!(event is BlockBreakEvent || event is BlockPlaceEvent)) {
            return
        }
        val loc = event.block.location
        val x = if (loc.blockX < 0) {
            ((loc.blockX%16)+16)%16
        } else {
            loc.blockX%16
        }
        val z = if (loc.blockZ < 0) {
            ((loc.blockZ%16)+16)%16
        } else {
            loc.blockZ%16
        }
        if (!(x == 0 || x == 15 || z == 0 || z == 15)) {
            return
        }
        val cell = MazeGenerator.parser.getCell(loc.chunk.x, loc.chunk.z)
        if (!((z == 0 && cell.wallSouth) ||
              (z == 15 && cell.wallTop) ||
              (x == 0 && cell.wallWest) ||
              (x == 15 && cell.wallEast))
            ) {
            return
        }
        if (event is BlockPlaceEvent) breakBlock(event)
        else (event as BlockBreakEvent).isCancelled = true
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
    
    @EventHandler
    fun onDimensionChange(event: PlayerPortalEvent) {
        event.isCancelled = true
    }
}