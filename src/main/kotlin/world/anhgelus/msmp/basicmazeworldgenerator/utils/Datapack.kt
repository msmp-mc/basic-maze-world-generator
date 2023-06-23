package world.anhgelus.msmp.basicmazeworldgenerator.utils

import org.apache.commons.io.FileUtils
import org.bukkit.World
import world.anhgelus.msmp.basicmazeworldgenerator.BasicMazeWorldGenerator
import java.io.File
import java.io.IOException

/**
 * Datapack utils
 */
object Datapack {
    /**
     * Copy the datapack into a world folder
     *
     * @return true if the copy was successful
     */
    fun copyInDir(world: World): Boolean {
        val worldFolder = world.worldFolder

        val instance = BasicMazeWorldGenerator.INSTANCE

        val mcmeta = instance.getResource("datapack/pack.mcmeta")
        val one = instance.getResource("datapack/data/tier-one.json")
        val two = instance.getResource("datapack/data/tier-two.json")
        val three = instance.getResource("datapack/data/tier-three.json")
        val four = instance.getResource("datapack/data/tier-four.json")
        val five = instance.getResource("datapack/data/tier-five.json")

        val packFolder = File(worldFolder.path + File.separator + "datapacks" + File.separator + "basicmazeworldgenerator")
        val chests = File(packFolder.path + File.separator + "data" + File.separator + "basicmazeworldgenerator"
                + File.separator + "loot_tables" + File.separator + "chests")
        if (!packFolder.mkdirs()) return false
        if (!chests.mkdirs()) return false

        val mcmetaFile = File(packFolder.path + File.separator + "pack.mcmeta")
        val oneFile = File(chests.path + File.separator + "tier-one.json")
        val twoFile = File(chests.path + File.separator + "tier-two.json")
        val threeFile = File(chests.path + File.separator + "tier-three.json")
        val fourFile = File(chests.path + File.separator + "tier-four.json")
        val fiveFile = File(chests.path + File.separator + "tier-five.json")

        if (!mcmetaFile.createNewFile()) return false
        oneFile.createNewFile()
        twoFile.createNewFile()
        threeFile.createNewFile()
        fourFile.createNewFile()
        fiveFile.createNewFile()

        try {
            FileUtils.copyInputStreamToFile(mcmeta, mcmetaFile)
            FileUtils.copyInputStreamToFile(one, oneFile)
            FileUtils.copyInputStreamToFile(two, twoFile)
            FileUtils.copyInputStreamToFile(three, threeFile)
            FileUtils.copyInputStreamToFile(four, fourFile)
            FileUtils.copyInputStreamToFile(five, fiveFile)
        } catch (e: IOException) {
            BasicMazeWorldGenerator.LOGGER.warning("Failed to copy datapack to world folder, exception: {$e}\n " +
                    "Stacktrace: ${e.stackTrace}")
            return false
        }
        return true
    }
}