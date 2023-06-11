package world.anhgelus.msmp.basicmazeworldgenerator

import org.bukkit.Bukkit
import org.bukkit.generator.ChunkGenerator
import world.anhgelus.msmp.basicmazeworldgenerator.events.MobListener
import world.anhgelus.msmp.basicmazeworldgenerator.events.PlayerListener
import world.anhgelus.msmp.basicmazeworldgenerator.events.SetupListener
import world.anhgelus.msmp.basicmazeworldgenerator.generator.MazeGenerator
import world.anhgelus.msmp.basicmazeworldgenerator.handlers.WinHandler
import world.anhgelus.msmp.basicmazeworldgenerator.utils.ConfigAPI
import world.anhgelus.msmp.msmpcore.PluginBase

class BasicMazeWorldGenerator: PluginBase() {
    override val configHelper = ConfigAPI
    override val pluginName = "BasicMazeWorldGenerator"

    override fun disable() {

    }

    override fun enable() {
        INSTANCE = this
        LOGGER = logger

        //TODO: improve this code
        Bukkit.getPluginManager().registerEvents(MobListener, this)
        //TODO: add config option for win handler
        Bukkit.getPluginManager().registerEvents(PlayerListener(WinHandler.ONE_WINNER), this)
        Bukkit.getPluginManager().registerEvents(SetupListener, this)
    }

    override fun getDefaultWorldGenerator(worldName: String, id: String?): ChunkGenerator {
        return MazeGenerator()
    }

    companion object: CompanionBase()
}