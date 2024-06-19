package seat.code.infrastructure.adapter.driven.persistence.surface.reader

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import seat.code.infrastructure.adapter.driven.persistence.surface.reader.exception.ConfigurationFileNotFoundException
import seat.code.infrastructure.adapter.driven.persistence.surface.reader.exception.InvalidConfigurationFileException

@ExtendWith(MockitoExtension::class)
class FileReaderTest {

    @Mock
    private lateinit var fileConfiguration: FileConfiguration

    @Mock
    private lateinit var validator: InputCommandValidator

    @InjectMocks
    private lateinit var fileReader: FileReader

    @BeforeEach
    fun beforeEach() {
        `when`(fileConfiguration.name()).thenReturn(FILE_NAME)
    }

    @Test
    fun `should get surface dimension`() {
        val (width, height) = fileReader.getSurfaceDimension()
        assertEquals(SURFACE_WIDTH, width)
        assertEquals(SURFACE_HEIGHT, height)
    }

    @Test
    fun `should get mowers configuration`() {
        val mowerConfiguration = fileReader.getMowersConfiguration().entries.first()
        assertEquals(MOWER_X, mowerConfiguration.key.first)
        assertEquals(MOWER_Y, mowerConfiguration.key.second)
        assertEquals(MOWER_DIRECTION, mowerConfiguration.key.third)
        assertEquals(MOWER_COMMANDS, mowerConfiguration.value)
    }

    @Test
    fun `should throw exception when the file does not exist`() {
        `when`(fileConfiguration.name()).thenReturn("foo")
        assertThrows<ConfigurationFileNotFoundException> {
            fileReader.getSurfaceDimension()
        }
    }

    @Test
    fun `should throw exception when the file is not valid`() {
        `when`(fileConfiguration.name()).thenReturn(INVALID_FILE_NAME)
        assertThrows<InvalidConfigurationFileException> {
            fileReader.getSurfaceDimension()
        }
    }

    companion object {
        private const val FILE_NAME = "test.txt"
        private const val INVALID_FILE_NAME = "invalid-test.txt"

        private const val SURFACE_WIDTH = 5
        private const val SURFACE_HEIGHT = 5

        private const val MOWER_X = 1
        private const val MOWER_Y = 2
        private const val MOWER_DIRECTION = 'N'
        private const val MOWER_COMMANDS = "LMLMLMLMM"
    }
}