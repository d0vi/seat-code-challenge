package seat.code.domain.model.surface

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import seat.code.domain.exception.surface.InvalidMowerPositionException
import seat.code.domain.exception.surface.NoAvailableMowersException
import seat.code.domain.model.mower.Coordinate
import seat.code.domain.model.mower.Direction
import seat.code.domain.model.mower.Mower

class SurfaceTest {

    @Test
    fun `should instantiate a surface`() {
        val surface = Surface(Width(5), Height(5))
        assertNotNull(surface)
        assertEquals(surface.width(), 5)
        assertEquals(surface.height(), 5)
        assertEquals(surface.mowers(), emptyList<Mower>())
    }

    @Test
    fun `should place a mower`() {
        val surface = Surface(Width(5), Height(5))
        surface.placeMower(Mower(Coordinate(0), Coordinate(4), Direction.SOUTH), "FOO")
        assertTrue(surface.mowers().isNotEmpty())
    }

    @Test
    fun `should throw exception when placing a mower outside the surface`() {
        val surface = Surface(Width(5), Height(5))
        assertThrows<InvalidMowerPositionException> {
            surface.placeMower(Mower(Coordinate(8), Coordinate(4), Direction.SOUTH), "FOO")
        }
    }

    @Test
    fun `should instruct mowers to cut grass`() {
        val surface = Surface(Width(5), Height(5))
        surface.placeMower(Mower(Coordinate(1), Coordinate(2), Direction.NORTH), "LMLMLMLMM")
        surface.cutGrass()
        val mower = surface.mowers().first()
        assertEquals(mower.xCoordinate(), 1)
        assertEquals(mower.yCoordinate(), 3)
        assertEquals(mower.direction(), Direction.NORTH)
    }

    @Test
    fun `should throw exception when there are no available mowers to cut grass`() {
        val surface = Surface(Width(5), Height(5))
        assertThrows<NoAvailableMowersException> {
            surface.cutGrass()
        }
    }
}