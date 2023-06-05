package world.anhgelus.msmp.basicmazeworldgenerator.events

import org.bukkit.Material
import org.bukkit.block.Chest
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class PlayerListener: Listener {
    @EventHandler
    fun onChestOpen(event: PlayerInteractEvent) {
        if (event.clickedBlock == null) return
        if (event.clickedBlock?.type != Material.CHEST) return
        event.isCancelled = true
        val chest = event.clickedBlock!!.state as Chest
        if (chest.inventory.size != 0) {
            return
        }
        // set the loot table
        //chest.lootTable = null
        chest.open()
        val player = event.player
        player.openInventory(chest.blockInventory)
    }
}