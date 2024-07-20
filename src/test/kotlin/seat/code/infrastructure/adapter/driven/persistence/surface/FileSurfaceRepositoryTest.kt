package seat.code.infrastructure.adapter.driven.persistence.surface

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import seat.code.infrastructure.adapter.driven.persistence.surface.reader.FileReader
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class FileSurfaceRepositoryTest {

    @Mock
    private lateinit var fileReader: FileReader

    @InjectMocks
    private lateinit var fileSurfaceRepository: FileSurfaceRepository

    @Test
    fun `should return a surface`() {
        `when`(fileReader.getSurfaceDimension()).thenReturn(5 to 5)
        `when`(fileReader.getMowersConfiguration())
            .thenReturn(mapOf(Triple(1, 2, 'N') to "LMLMLMLMM"))

        val surface = fileSurfaceRepository.get()

        assertEquals(SURFACE_WIDTH, surface.width())
        assertEquals(SURFACE_HEIGHT, surface.height())
        assertEquals(MOWER_X, surface.mowers().first().xCoordinate())
        assertEquals(MOWER_Y, surface.mowers().first().yCoordinate())
        assertEquals(MOWER_DIRECTION, surface.mowers().first().direction())
        assertEquals(MOWER_COMMANDS, surface.mowersWithCommands().entries.first().value)
    }

    companion object {
        private const val SURFACE_WIDTH = 5
        private const val SURFACE_HEIGHT = 5

        private const val MOWER_X = 1
        private const val MOWER_Y = 2
        private const val MOWER_DIRECTION = 'N'
        private const val MOWER_COMMANDS = "LMLMLMLMM"
    }
}