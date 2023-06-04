package world.anhgelus.msmp.basicmazeworldgenerator.generator

class MazeGeneratorException: Exception {
    constructor(message: String): super(message)
    constructor(message: String, cause: Throwable): super(message, cause)
    constructor(cause: Throwable): super(cause)
}