package world.anhgelus.msmp.basicmazeworldgenerator.api

import org.bukkit.entity.Player
import world.anhgelus.msmp.msmpcore.player.MPlayer
import world.anhgelus.msmp.msmpcore.player.MPlayerManager

abstract class WinningHandler {
    /**
     * Winners list (Place, Player)
     */
    protected val winners = mutableMapOf<Int, MPlayer>()

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

    fun getWinners(): Map<Int, MPlayer> {
        return winners
    }
}