package world.anhgelus.msmp.basicmazeworldgenerator.events

import org.bukkit.Bukkit
import org.bukkit.GameRule
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.world.ChunkLoadEvent
import org.bukkit.event.world.WorldLoadEvent
import world.anhgelus.msmp.basicmazeworldgenerator.BasicMazeWorldGenerator
import world.anhgelus.msmp.basicmazeworldgenerator.generator.MazeGenerator
import world.anhgelus.msmp.basicmazeworldgenerator.generator.MazeParser
import world.anhgelus.msmp.basicmazeworldgenerator.handlers.WinHandler
import world.anhgelus.msmp.basicmazeworldgenerator.utils.Datapack

class SetupListener(private val winHandler: WinHandler): Listener {

    @EventHandler
    fun onWorldGenerationComplete(event: WorldLoadEvent) {
        if (generated) return
        generated = true
        val world = event.world
        if (!Datapack.copyInDir(world)) BasicMazeWorldGenerator.LOGGER.info("The datapack was not copied to the world folder. " +
                "It must be a bug if it's the first time you generate the world.")
        world.setGameRule(GameRule.SPAWN_RADIUS, 0)
        world.setGameRule(GameRule.SPECTATORS_GENERATE_CHUNKS, false)
        val loc = if (world.getBlockAt(0, 65, 0).type == Material.AIR) {
            Location(world, 0.0, 65.0, 0.0)
        } else if (world.getBlockAt(1, 65, 0).type == Material.AIR) {
            Location(world, 1.0, 65.0, 0.0)
        } else if (world.getBlockAt(0, 65, 1).type == Material.AIR) {
            Location(world, 0.0, 65.0, 1.0)
        } else if (world.getBlockAt(1, 65, 1).type == Material.AIR) {
            Location(world, 1.0, 65.0, 1.0)
        } else {
            Location(world, 2.0, 65.0, 1.0)
        }
        world.setSpawnLocation(loc)

//        MazeParser.placeArmorStands(world)

        val handler = winHandler.handler
        Bukkit.getScheduler().runTaskTimer(BasicMazeWorldGenerator.INSTANCE, Runnable {
             Bukkit.getOnlinePlayers().forEach {
                 if (handler.isAlreadyWinner(it)) return@forEach
                 val pLoc = it.location
                 if (!MazeGenerator.isBlockOutside(pLoc.blockX,pLoc.blockZ)) return@forEach
                 handler.newWinner(it)
             }
        }, 20L, 10L)
    }

    private var isGenerating = false

    @EventHandler
    fun onChunkLoad(event: ChunkLoadEvent) {
        if (isGenerating) return
        if (!generated) return
        if (event.chunk.world.name.endsWith("_nether") || event.chunk.world.name.endsWith("_the_end")) return
        isGenerating = true
        if (event.isNewChunk) MazeParser.placeArmorStands(event.chunk.world)
        isGenerating = false
    }

    companion object {
        private var generated = false

        fun isGenerated(): Boolean {
            return generated
        }
    }

}