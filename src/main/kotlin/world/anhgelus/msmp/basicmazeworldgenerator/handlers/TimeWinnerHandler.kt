package world.anhgelus.msmp.basicmazeworldgenerator.handlers

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.scheduler.BukkitTask
import world.anhgelus.msmp.basicmazeworldgenerator.BasicMazeWorldGenerator
import world.anhgelus.msmp.basicmazeworldgenerator.api.WinningHandler
import world.anhgelus.msmp.basicmazeworldgenerator.utils.ConfigAPI
import world.anhgelus.msmp.msmpcore.player.MPlayer
import world.anhgelus.msmp.msmpcore.utils.ChatHelper

/**
 * The time winner handler
 *
 * @param time The time in seconds
 */
class TimeWinnerHandler: WinningHandler {

    val time: Int

    constructor() {
        time = ConfigAPI.getConfig("config").get().getInt("game.win-condition.time", 30)
    }

    constructor(time: Int) {
        this.time = time
    }

    private lateinit var task: BukkitTask
    override fun newWinner(player: MPlayer) {
        player.player.gameMode = GameMode.SPECTATOR
        if (winners.isNotEmpty()) {
            winners[winners.size-1] = player
            ChatHelper.sendSuccess("The player ${player.player.displayName} (${winners.size}) found the exit!")
            return
        }
        winners[0] = player
        exitLocation = player.player.location
        var time = time
        task = Bukkit.getScheduler().runTaskTimer(BasicMazeWorldGenerator.INSTANCE, fun() {
            if (time == 0) {
                end()
                task.cancel()
            }
            if (time == this.time) {
                ChatHelper.sendSuccess("The game will end in $time seconds! The player ${player.player.displayName} is the " +
                        "first who found the exit!")
            } else {
                ChatHelper.sendInfo("The game will end in $time seconds!")
            }
            time--
        }, 0, 20L)
    }
}