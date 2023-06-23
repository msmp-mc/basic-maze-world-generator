package world.anhgelus.msmp.basicmazeworldgenerator.handlers

import world.anhgelus.msmp.basicmazeworldgenerator.api.WinningHandler

/**
 * Basic types of win handlers
 *
 * @param id The id of the handler
 * @param handler The handler
 */
enum class WinHandler(val id: Int, val handler: WinningHandler) {
    ONE_WINNER(0, XWinnersHandler(1)),
    TWO_WINNERS(5, XWinnersHandler(2)),
    THREE_WINNERS(6, XWinnersHandler(3)),
    CUSTOM_WINNERS(7, XWinnersHandler()),
    THIRTY_SECONDS_WINNER(1, TimeWinnerHandler(30)),
    ONE_MINUTE_WINNER(2, TimeWinnerHandler(60)),
    TWO_MINUTES_WINNER(3, TimeWinnerHandler(120)),
    CUSTOM_MINUTES_WINNER(4, TimeWinnerHandler()),
}