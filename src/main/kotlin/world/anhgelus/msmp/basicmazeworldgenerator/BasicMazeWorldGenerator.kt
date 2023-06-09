package world.anhgelus.msmp.basicmazeworldgenerator

import org.bukkit.Bukkit
import org.bukkit.generator.ChunkGenerator
import world.anhgelus.msmp.basicmazeworldgenerator.events.MobListener
import world.anhgelus.msmp.basicmazeworldgenerator.events.PlayerListener
import world.anhgelus.msmp.basicmazeworldgenerator.events.SetupListener
import world.anhgelus.msmp.basicmazeworldgenerator.generator.MazeGenerator
import world.anhgelus.msmp.basicmazeworldgenerator.utils.Datapack
import world.anhgelus.msmp.msmpcore.PluginBase
import world.anhgelus.msmp.msmpcore.utils.config.Config

class BasicMazeWorldGenerator: PluginBase() {
    override val pluginName = "BasicMazeWorldGenerator"

    override fun disable() {

    }

    override fun enable() {
        INSTANCE = this
        LOGGER = logger

        Bukkit.getPluginManager().registerEvents(MobListener(), this)
        Bukkit.getPluginManager().registerEvents(PlayerListener(), this)
        Bukkit.getPluginManager().registerEvents(SetupListener(), this)
    }

    override fun getDefaultWorldGenerator(worldName: String, id: String?): ChunkGenerator {
        return MazeGenerator()
    }

    companion object: CompanionBase()
}