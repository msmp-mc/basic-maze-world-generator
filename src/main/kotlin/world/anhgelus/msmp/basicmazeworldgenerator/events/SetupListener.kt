package world.anhgelus.msmp.basicmazeworldgenerator.events

import org.bukkit.Bukkit
import org.bukkit.GameRule
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.world.WorldLoadEvent
import world.anhgelus.msmp.basicmazeworldgenerator.BasicMazeWorldGenerator
import world.anhgelus.msmp.basicmazeworldgenerator.utils.Datapack

class SetupListener: Listener {
    @EventHandler
    fun onWorldGenerationComplete(event: WorldLoadEvent) {
        if (!Datapack.copyInDir(event.world)) BasicMazeWorldGenerator.LOGGER.info("The datapack was not copied to the world folder. " +
                "It must be a bug if it's the first time you generate the world.")
        event.world.setGameRule(GameRule.SPAWN_RADIUS, 0)
        val loc = if (event.world.getBlockAt(0, 65, 0).type == Material.AIR) {
            Location(event.world, 0.0, 65.0, 0.0)
        } else if (event.world.getBlockAt(1, 65, 0).type == Material.AIR) {
            Location(event.world, 1.0, 65.0, 0.0)
        } else if (event.world.getBlockAt(0, 65, 1).type == Material.AIR) {
            Location(event.world, 0.0, 65.0, 1.0)
        } else if (event.world.getBlockAt(1, 65, 1).type == Material.AIR) {
            Location(event.world, 1.0, 65.0, 1.0)
        } else {
            Location(event.world, 2.0, 65.0, 1.0)
        }
        event.world.setSpawnLocation(loc)
    }
}