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
        val mower = Mower(1, 2, 'N')
        assertNotNull(mower)
        assertEquals(mower.xCoordinate(), 1)
        assertEquals(mower.yCoordinate(), 2)
        assertEquals(mower.direction(), 'N')
    }

    @Test
    fun `should print a good toString`() {
        val mower = Mower(1, 2, 'N')
        assertEquals(mower.toString(), "1 2 N")
    }

    @ParameterizedTest
    @MethodSource("getMowerDirectionsWithTurnCommands")
    fun `should turn accordingly`(initialDirection: Char, command: Char, finalDirection: Char) {
        val mower = Mower(5, 5, initialDirection)
        mower.move(command, SURFACE_WIDTH, SURFACE_HEIGHT)
        assertEquals(mower.direction(), finalDirection)
    }

    @ParameterizedTest
    @MethodSource("getMowersWithForwardCommands")
    fun `should go forward accordingly`(initialMower: Mower, command: Char, finalMower: Mower) {
        initialMower.move(command, SURFACE_WIDTH, SURFACE_HEIGHT)
        assertEquals(initialMower.toString(), finalMower.toString())
    }

    @Test
    fun `should throw exception when moving out of the surface bounds`() {
        val mower = Mower(0, 0, 'S')
        assertThrows<MowerPositionOutOfBoundsException> {
            mower.move('M', SURFACE_WIDTH, SURFACE_HEIGHT)
        }
    }

    companion object {
        private const val SURFACE_WIDTH = 10
        private const val SURFACE_HEIGHT = 10

        @JvmStatic
        private fun getMowerDirectionsWithTurnCommands(): Stream<Arguments> =
            Stream.of(
                Arguments.of(
                    'N',
                    'R',
                    'E'
                ),
                Arguments.of(
                    'E',
                    'R',
                    'S'
                ),
                // rest of cases omitted for simplicity's sake
            )

        @JvmStatic
        private fun getMowersWithForwardCommands(): Stream<Arguments> =
            Stream.of(
                Arguments.of(
                    Mower(5, 5, 'N'),
                    'M',
                    Mower(5, 6, 'N')
                ),
                Arguments.of(
                    Mower(5, 5, 'E'),
                    'M',
                    Mower(6, 5, 'E')
                ),
                // rest of cases omitted for simplicity's sake
            )
    }
}