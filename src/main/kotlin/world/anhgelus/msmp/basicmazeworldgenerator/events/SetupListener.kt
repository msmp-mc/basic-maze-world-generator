package world.anhgelus.msmp.basicmazeworldgenerator.events

import org.bukkit.Bukkit
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
    }
}