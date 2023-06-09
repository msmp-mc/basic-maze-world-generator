package world.anhgelus.msmp.basicmazeworldgenerator.utils

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.loot.LootTable
import world.anhgelus.msmp.basicmazeworldgenerator.BasicMazeWorldGenerator
import java.util.Random
import kotlin.math.abs

object LootTablesHelper {
    fun genKey(type: LootTablesType, name: String): NamespacedKey {
        return NamespacedKey(BasicMazeWorldGenerator.INSTANCE,"${type.path}/$name")
    }

    fun getChestLootTable(blockLoc: Location): LootTable {
        val dist = abs(blockLoc.blockX*blockLoc.blockX - blockLoc.blockZ*blockLoc.blockZ)
        if (dist < 100) return Bukkit.getLootTable(genKey(LootTablesType.CHEST, "tier-one"))!!
        val rand = Random(blockLoc.world!!.seed)
        return when(rand.nextInt((dist/200)+1+1)) {
            1 -> Bukkit.getLootTable(genKey(LootTablesType.CHEST, "tier-one"))!!
            2 -> Bukkit.getLootTable(genKey(LootTablesType.CHEST, "tier-two"))!!
            3 -> Bukkit.getLootTable(genKey(LootTablesType.CHEST, "tier-three"))!!
            4 -> Bukkit.getLootTable(genKey(LootTablesType.CHEST, "tier-four"))!!
            else -> Bukkit.getLootTable(genKey(LootTablesType.CHEST, "tier-five"))!!
        }
    }
}