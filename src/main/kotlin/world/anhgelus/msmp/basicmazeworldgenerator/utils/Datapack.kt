package world.anhgelus.msmp.basicmazeworldgenerator.utils

import org.apache.commons.io.FileUtils
import org.bukkit.World
import world.anhgelus.msmp.basicmazeworldgenerator.BasicMazeWorldGenerator
import java.io.File
import java.io.IOException
import java.io.InputStream

object Datapack {
    var datapack: InputStream? = null

    fun copyInDir(world: World): Boolean {
        if (datapack == null) return false
        val worldFolder = world.worldFolder
        val datapackFolder = File(worldFolder.path + File.separator + "datapacks")
        if (!datapackFolder.mkdirs()) return false
        val packFolder = File(datapackFolder.path + File.separator + "basicmazeworldgenerator")
        if (!packFolder.mkdirs()) return false
        try {
            FileUtils.copyInputStreamToFile(datapack, packFolder)
        } catch (e: IOException) {
            BasicMazeWorldGenerator.LOGGER.warning("Failed to copy datapack to world folder, exception: {$e}\n " +
                    "Stacktrace: ${e.stackTrace}")
            return false
        }
        return true
    }
}