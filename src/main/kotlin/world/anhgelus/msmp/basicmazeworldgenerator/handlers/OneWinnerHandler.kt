package world.anhgelus.msmp.basicmazeworldgenerator.handlers

import org.bukkit.Bukkit
import org.bukkit.GameMode
import world.anhgelus.msmp.basicmazeworldgenerator.api.WinningHandler
import world.anhgelus.msmp.msmpcore.player.MPlayer
import world.anhgelus.msmp.msmpcore.utils.ChatHelper

object OneWinnerHandler: WinningHandler() {
    override fun newWinner(player: MPlayer) {
        winners[winners.size-1] = player
        ChatHelper.sendSuccess("End of the game! The winner is ${player.player.displayName}!")
        val loc = player.player.location
        ChatHelper.sendInfo("The exit was located at x = ${loc.blockX}, z = ${loc.blockZ}")
        Bukkit.getOnlinePlayers().forEach {
            it.gameMode = GameMode.SPECTATOR
        }
    }
}