package seat.code.domain.model.mower

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import seat.code.domain.exception.mower.MowerPositionOutOfBoundsException
import java.util.stream.Stream

class MowerTest {

    @Test
    fun `should instantiate a mower`() {
        val mower = Mower(Coordinate(1), Coordinate(2), Direction.NORTH)
        assertNotNull(mower)
        assertEquals(mower.xCoordinate(), 1)
        assertEquals(mower.yCoordinate(), 2)
        assertEquals(mower.direction(), Direction.NORTH)
    }

    @Test
    fun `should print a good toString`() {
        val mower = Mower(Coordinate(1), Coordinate(2), Direction.NORTH)
        assertEquals(mower.toString(), "1 2 N")
    }

    @ParameterizedTest
    @MethodSource("getMowerDirectionsWithTurnCommands")
    fun `should turn accordingly`(initialDirection: Direction, command: Command, finalDirection: Direction) {
        val mower = Mower(Coordinate(5), Coordinate(5), initialDirection)
        mower.move(command, SURFACE_WIDTH, SURFACE_HEIGHT)
        assertEquals(mower.direction(), finalDirection)
    }

    @ParameterizedTest
    @MethodSource("getMowersWithForwardCommands")
    fun `should go forward accordingly`(initialMower: Mower, command: Command, finalMower: Mower) {
        initialMower.move(command, SURFACE_WIDTH, SURFACE_HEIGHT)
        assertEquals(initialMower.toString(), finalMower.toString())
    }

    @Test
    fun `should throw exception when moving out of the surface bounds`() {
        val mower = Mower(Coordinate(0), Coordinate(0), Direction.SOUTH)
        assertThrows<MowerPositionOutOfBoundsException> {
            mower.move(Command.GO_FORWARD, SURFACE_WIDTH, SURFACE_HEIGHT)
        }
    }

    companion object {
        private const val SURFACE_WIDTH = 10
        private const val SURFACE_HEIGHT = 10

        @JvmStatic
        private fun getMowerDirectionsWithTurnCommands(): Stream<Arguments> =
            Stream.of(
                Arguments.of(
                    Direction.NORTH,
                    Command.TURN_RIGHT,
                    Direction.EAST
                ),
                Arguments.of(
                    Direction.EAST,
                    Command.TURN_RIGHT,
                    Direction.SOUTH
                ),
                // rest of cases omitted for simplicity's sake
            )

        @JvmStatic
        private fun getMowersWithForwardCommands(): Stream<Arguments> =
            Stream.of(
                Arguments.of(
                    Mower(Coordinate(5), Coordinate(5), Direction.NORTH),
                    Command.GO_FORWARD,
                    Mower(Coordinate(5), Coordinate(6), Direction.NORTH)
                ),
                Arguments.of(
                    Mower(Coordinate(5), Coordinate(5), Direction.EAST),
                    Command.GO_FORWARD,
                    Mower(Coordinate(6), Coordinate(5), Direction.EAST)
                ),
                // rest of cases omitted for simplicity's sake
            )
    }
}