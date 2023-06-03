package world.anhgelus.msmp.basicmazeworldgenerator

import org.bukkit.generator.ChunkGenerator
import world.anhgelus.msmp.basicmazeworldgenerator.generator.MazeGenerator
import world.anhgelus.msmp.msmpcore.PluginBase

class BasicMazeWorldGenerator: PluginBase() {
    override val pluginName = "BasicMazeWorldGenerator"

    override fun disable() {

    }

    override fun enable() {

    }

    override fun getDefaultWorldGenerator(worldName: String, id: String?): ChunkGenerator? {
        return MazeGenerator()
    }

    companion object: CompanionBase()
}