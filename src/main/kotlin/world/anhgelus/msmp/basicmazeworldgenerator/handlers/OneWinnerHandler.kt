package world.anhgelus.msmp.basicmazeworldgenerator.handlers

import world.anhgelus.msmp.basicmazeworldgenerator.api.WinningHandler
import world.anhgelus.msmp.msmpcore.player.MPlayer

object OneWinnerHandler: WinningHandler() {
    override fun newWinner(player: MPlayer) {
        winners[winners.size] = player
        exitLocation = player.player.location
        end()
    }
}