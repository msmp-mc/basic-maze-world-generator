package world.anhgelus.msmp.basicmazeworldgenerator.utils

import org.apache.commons.io.FileUtils
import org.bukkit.World
import world.anhgelus.msmp.basicmazeworldgenerator.BasicMazeWorldGenerator
import java.io.File
import java.io.IOException
import java.io.InputStream

object Datapack {
    fun copyInDir(world: World): Boolean {
        val worldFolder = world.worldFolder

        val instance = BasicMazeWorldGenerator.INSTANCE

        instance.saveResource("datapack", true)

        val datapackFolder = File(worldFolder.path + File.separator + "datapacks")
        if (!datapackFolder.mkdirs()) return false
        val packFolder = File(datapackFolder.path + File.separator + "basicmazeworldgenerator")
        if (!packFolder.mkdirs()) return false
        try {
            FileUtils.copyDirectory(File(instance.dataFolder.path + File.separator + "datapack"), packFolder)
        } catch (e: IOException) {
            BasicMazeWorldGenerator.LOGGER.warning("Failed to copy datapack to world folder, exception: {$e}\n " +
                    "Stacktrace: ${e.stackTrace}")
            return false
        }
        return true
    }
}