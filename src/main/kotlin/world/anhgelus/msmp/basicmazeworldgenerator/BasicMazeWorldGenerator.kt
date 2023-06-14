package world.anhgelus.msmp.basicmazeworldgenerator

import org.bukkit.entity.EntityType
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

        MobListener.enabled.add(EntityType.ARMOR_STAND)
        MobListener.enabled.add(EntityType.DROPPED_ITEM)
        MobListener.enabled.add(EntityType.ARROW)
        MobListener.enabled.add(EntityType.SPECTRAL_ARROW)
        MobListener.enabled.add(EntityType.SPLASH_POTION)
        MobListener.enabled.add(EntityType.EXPERIENCE_ORB)
        MobListener.enabled.add(EntityType.THROWN_EXP_BOTTLE)
        MobListener.enabled.add(EntityType.LIGHTNING)
        MobListener.enabled.add(EntityType.FALLING_BLOCK)

        events.add(MobListener)
        events.add(PlayerListener)
        events.add(SetupListener)
    }

    override fun getDefaultWorldGenerator(worldName: String, id: String?): ChunkGenerator {
        return MazeGenerator()
    }

    private fun getWinHandler(): WinHandler {
        val id = ConfigAPI.getConfig("config").get().getInt("game.win-condition.id", 0)
        WinHandler.values().forEach {
            if (it.id == id) return it
        }
        throw IllegalArgumentException("The win handler with id $id doesn't exist!")
    }

    companion object: CompanionBase()
}