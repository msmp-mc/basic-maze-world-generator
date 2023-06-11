package world.anhgelus.msmp.basicmazeworldgenerator

import org.bukkit.Bukkit
import org.bukkit.generator.ChunkGenerator
import world.anhgelus.msmp.basicmazeworldgenerator.events.MobListener
import world.anhgelus.msmp.basicmazeworldgenerator.events.PlayerListener
import world.anhgelus.msmp.basicmazeworldgenerator.events.SetupListener
import world.anhgelus.msmp.basicmazeworldgenerator.generator.MazeGenerator
import world.anhgelus.msmp.basicmazeworldgenerator.utils.ConfigAPI
import world.anhgelus.msmp.msmpcore.PluginBase
import java.util.Collections

class BasicMazeWorldGenerator: PluginBase() {
    override val configHelper = ConfigAPI
    override val pluginName = "BasicMazeWorldGenerator"

    override fun disable() {

    }

    override fun enable() {
        INSTANCE = this
        LOGGER = logger

        events.add(MobListener)
        events.add(PlayerListener)
        events.add(SetupListener)
    }

    override fun getDefaultWorldGenerator(worldName: String, id: String?): ChunkGenerator {
        return MazeGenerator()
    }

    companion object: CompanionBase()
}