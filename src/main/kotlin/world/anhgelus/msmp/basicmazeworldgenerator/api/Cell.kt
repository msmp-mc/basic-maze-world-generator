package world.anhgelus.msmp.basicmazeworldgenerator.api

data class Cell(
    val x: Int,
    val z: Int,
    val wallTop: Boolean,
    val wallSouth: Boolean,
    val wallWest: Boolean,
    val wallEast: Boolean,
)
