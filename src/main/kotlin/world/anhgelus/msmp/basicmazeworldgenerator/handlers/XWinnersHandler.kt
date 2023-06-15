package world.anhgelus.msmp.basicmazeworldgenerator.handlers

import world.anhgelus.msmp.basicmazeworldgenerator.api.WinningHandler
import world.anhgelus.msmp.basicmazeworldgenerator.utils.ConfigAPI
import world.anhgelus.msmp.msmpcore.player.MPlayer
import world.anhgelus.msmp.msmpcore.utils.ChatHelper

class XWinnersHandler : WinningHandler {
    val count: Int

    constructor() {
        count = ConfigAPI.getConfig("config").get().getInt("game.win-condition.number", 1)
    }

    constructor(count: Int) {
        this.count = count
    }

    override fun newWinner(player: MPlayer) {
        winners[winners.size] = player
        playerFoundExit(player)
        exitLocation = player.player.location
        if (winners.size == count) {
            end()
            return
        }
        val remaining = count-winners.size
        if (remaining > 1) ChatHelper.sendInfo("The game will end when $remaining players will find the exit!")
        else ChatHelper.sendInfo("The game will end when 1 player will find the exit!")
    }
}