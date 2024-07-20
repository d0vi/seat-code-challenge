package seat.code.domain.model.surface

import seat.code.domain.exception.surface.InvalidMowerPositionException
import seat.code.domain.exception.surface.NoAvailableMowersException
import seat.code.domain.model.mower.Command
import seat.code.domain.model.mower.Mower

class Surface private constructor(
    private val width: Width,
    private val height: Height,
    private val mowers: MutableMap<Mower, String>
) {

    constructor(width: Int, height: Int) : this(Width(width), Height(height), mutableMapOf())

    fun width() = width.value

    fun height() = height.value

    fun mowers() = mowers.keys.toList()

    fun mowersWithCommands() = mowers.toMap()

    fun placeMower(mower: Mower, instructions: String) {
        if (!hasValidPosition(mower)) {
            throw InvalidMowerPositionException()
        }
        mowers[mower] = instructions
    }

    fun cutGrass() {
        if (mowers.isEmpty()) {
            throw NoAvailableMowersException()
        }
        mowers.forEach { (mower, commands) ->
            commands.forEach {
                mower.move(
                    command = it,
                    maxX = width.value,
                    maxY = height.value
                )
            }
        }
    }

    private fun hasValidPosition(mower: Mower): Boolean =
        mower.xCoordinate() >= 0 && mower.yCoordinate() >= 0
                && mower.xCoordinate() <= width.value && mower.yCoordinate() <= height.value
}
