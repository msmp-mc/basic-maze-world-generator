package world.anhgelus.msmp.basicmazeworldgenerator.handlers

import world.anhgelus.msmp.basicmazeworldgenerator.api.WinningHandler

enum class WinHandler(val id: Int, val handler: WinningHandler) {
    ONE_WINNER(0, OneWinnerHandler),
    THIRTY_SECONDS_WINNER(1, TimeWinnerHandler(30)),
    ONE_MINUTE_WINNER(2, TimeWinnerHandler(60)),
    TWO_MINUTES_WINNER(3, TimeWinnerHandler(120)),
    CUSTOM_MINUTES_WINNER(4, TimeWinnerHandler()),
}