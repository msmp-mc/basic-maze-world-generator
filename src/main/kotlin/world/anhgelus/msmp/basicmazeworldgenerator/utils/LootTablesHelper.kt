package world.anhgelus.msmp.basicmazeworldgenerator.utils

import org.bukkit.NamespacedKey
import world.anhgelus.msmp.basicmazeworldgenerator.BasicMazeWorldGenerator

object LootTablesHelper {
    fun genKey(type: LootTablesType, name: String): NamespacedKey {
        return NamespacedKey(BasicMazeWorldGenerator.INSTANCE,"chests/loot_tables/${type.path}/$name")
    }
}