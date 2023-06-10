package world.anhgelus.msmp.basicmazeworldgenerator.utils.loottables

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.loot.LootTable
import world.anhgelus.msmp.basicmazeworldgenerator.BasicMazeWorldGenerator
import world.anhgelus.msmp.basicmazeworldgenerator.utils.MathRand
import java.util.*
import kotlin.math.abs

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
        val dist = abs(blockLoc.blockX)+abs(blockLoc.blockZ)
        return when(MathRand.zeroToFour(dist, Random(blockLoc.world!!.seed))) {
            0 -> Bukkit.getLootTable(genKey(LootTablesType.CHEST, "tier-one"))!!
            1 -> Bukkit.getLootTable(genKey(LootTablesType.CHEST, "tier-two"))!!
            2 -> Bukkit.getLootTable(genKey(LootTablesType.CHEST, "tier-three"))!!
            3 -> Bukkit.getLootTable(genKey(LootTablesType.CHEST, "tier-four"))!!
            else -> Bukkit.getLootTable(genKey(LootTablesType.CHEST, "tier-five"))!!
        }
    }
}