package world.anhgelus.msmp.basicmazeworldgenerator.api

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.entity.Player
import world.anhgelus.msmp.msmpcore.player.MPlayer
import world.anhgelus.msmp.msmpcore.player.MPlayerManager
import world.anhgelus.msmp.msmpcore.utils.ChatHelper

/**
 * Base of the winning handlers
 */
abstract class WinningHandler {
    /**
     * Winners list (Place, Player)
     */
    protected val winners: MutableMap<Int, MPlayer> = mutableMapOf()

    /**
     * One of the possible exit location
     */
    protected lateinit var exitLocation: Location

    /**
     * Add a winner
     *
     * @param player The player who won
     */
    abstract fun newWinner(player: MPlayer)

    /**
     * Add a winner
     *
     * @param player The player who won
     */
    fun newWinner(player: Player) {
        newWinner(MPlayerManager.get(player))
    }

    /**
     * Get the winners map
     *
     * @return The winners map (Place, Player)
     */
    fun getWinnersMap(): Map<Int, MPlayer> {
        return winners
    }

    /**
     * Check if the player is already a winner
     *
     * @param player The player to check
     */
    fun isAlreadyWinner(player: MPlayer): Boolean {
        return winners.containsValue(player)
    }

    /**
     * Check if the player is already a winner
     *
     * @param player The player to check
     */
    fun isAlreadyWinner(player: Player): Boolean {
        return winners.containsValue(MPlayerManager.get(player))
    }

    /**
     * Handle the end of the game
     */
    protected open fun end() {
        ChatHelper.sendSuccess("End of the game!")
        Bukkit.getOnlinePlayers().forEach {
            it.gameMode = GameMode.SPECTATOR
            if (!winners.containsValue(MPlayerManager.get(it))) {
                it.sendTitle("You lose!", "", 0, 40, 0)
            }
        }
        if (winners.size == 1) {
            val winner = winners[0]!!
            ChatHelper.sendSuccess("The winner is ${winner.player.displayName}!")
            winner.player.sendTitle("You win!", "", 0, 40, 0)
            winner.player.gameMode = GameMode.CREATIVE
            return
        }
        ChatHelper.sendSuccess("And the winners are:")
        winners.forEach {
            ChatHelper.send("${ChatColor.RESET}${it.key}- ${ChatHelper.success}${it.value.player.displayName}")
            it.value.player.sendTitle("You win!", "Your place: ${it.key}", 0, 40, 0)
        }
        ChatHelper.sendInfo("One of the possible exit was located at x = ${exitLocation.blockX}, z = ${exitLocation.blockZ}")
    }

    protected fun playerFoundExit(player: MPlayer) {
        ChatHelper.sendSuccess("The player ${player.player.displayName} (${winners.size}) found the exit!")
    }
}