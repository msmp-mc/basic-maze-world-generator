package world.anhgelus.msmp.basicmazeworldgenerator.handlers

import world.anhgelus.msmp.basicmazeworldgenerator.api.WinningHandler

enum class WinHandler(val id: Int, val handler: WinningHandler) {
    ONE_WINNER(0, OneWinnerHandler)
}