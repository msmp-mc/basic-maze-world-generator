package world.anhgelus.msmp.basicmazeworldgenerator.utils.loottables

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.loot.LootTable
import world.anhgelus.msmp.basicmazeworldgenerator.BasicMazeWorldGenerator
import java.util.*
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

object LootTablesHelper {
    /**
     * Generate a NamespacedKey for a loot table
     *
     * @param type The type of loot table
     * @param name The name of the loot table
     * @return The NamespacedKey
     */
    fun genKey(type: LootTablesType, name: String): NamespacedKey {
        return NamespacedKey(BasicMazeWorldGenerator.INSTANCE,"${type.path}/$name")
    }

    /**
     * Get a loot table for a chest
     *
     * @param blockLoc The location of the chest
     * @return The loot table
     */
    fun getChestLootTable(blockLoc: Location): LootTable {
        val dist = floor((sqrt((blockLoc.blockX*blockLoc.blockX - blockLoc.blockZ*blockLoc.blockZ).toDouble())/200)).toInt()
        println("Dist $dist")
        if (dist < 1) return Bukkit.getLootTable(genKey(LootTablesType.CHEST, "tier-one"))!!
        val rand = Random(blockLoc.world!!.seed)
        return when(rand.nextInt(dist+1+1)) {
            1 -> Bukkit.getLootTable(genKey(LootTablesType.CHEST, "tier-one"))!!
            2 -> Bukkit.getLootTable(genKey(LootTablesType.CHEST, "tier-two"))!!
            3 -> Bukkit.getLootTable(genKey(LootTablesType.CHEST, "tier-three"))!!
            4 -> Bukkit.getLootTable(genKey(LootTablesType.CHEST, "tier-four"))!!
            else -> Bukkit.getLootTable(genKey(LootTablesType.CHEST, "tier-five"))!!
        }
    }
}