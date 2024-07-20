package seat.code.domain.model.mower

import seat.code.domain.exception.mower.InvalidMowerDirectionException
import seat.code.domain.exception.mower.MowerPositionOutOfBoundsException

class Mower private constructor(
    private var xCoordinate: Coordinate,
    private var yCoordinate: Coordinate,
    private var direction: Direction
) {

    constructor(xCoordinate: Int, yCoordinate: Int, direction: Char) : this(
        Coordinate(xCoordinate),
        Coordinate(yCoordinate),
        Direction.fromValue(direction)
    )

    fun xCoordinate() = xCoordinate.value

    fun yCoordinate() = yCoordinate.value

    fun direction() = direction.value

    override fun toString(): String = "${xCoordinate.value} ${yCoordinate.value} ${direction.value}"

    fun move(command: Char, maxX: Int, maxY: Int) {
        when (Command.fromValue(command)) {
            Command.TURN_LEFT -> turnLeft()
            Command.TURN_RIGHT -> turnRight()
            Command.GO_FORWARD -> goForward(maxX, maxY)
        }
    }

    private fun goForward(maxX: Int, maxY: Int) {
        val (newXCoordinate, newYCoordinate) = when (direction) {
            Direction.NORTH -> xCoordinate.value to yCoordinate.value + 1
            Direction.EAST -> xCoordinate.value + 1 to yCoordinate.value
            Direction.SOUTH -> xCoordinate.value to yCoordinate.value - 1
            Direction.WEST -> xCoordinate.value - 1 to yCoordinate.value
        }
        if (newXCoordinate > maxX || newYCoordinate > maxY || newXCoordinate < 0 || newYCoordinate < 0) {
            throw MowerPositionOutOfBoundsException()
        }
        xCoordinate = Coordinate(newXCoordinate)
        yCoordinate = Coordinate(newYCoordinate)
    }

    private fun turnRight() {
        direction = RIGHT_TURNS.getOrElse(direction) { throw InvalidMowerDirectionException() }
    }

    private fun turnLeft() {
        direction = LEFT_TURNS.getOrElse(direction) { throw InvalidMowerDirectionException() }
    }

    companion object {
        private val RIGHT_TURNS: Map<Direction, Direction> =
            mapOf(
                Direction.NORTH to Direction.EAST, Direction.EAST to Direction.SOUTH,
                Direction.SOUTH to Direction.WEST, Direction.WEST to Direction.NORTH
            )
        private val LEFT_TURNS: Map<Direction, Direction> =
            mapOf(
                Direction.NORTH to Direction.WEST, Direction.WEST to Direction.SOUTH,
                Direction.SOUTH to Direction.EAST, Direction.EAST to Direction.NORTH
            )
    }
}
