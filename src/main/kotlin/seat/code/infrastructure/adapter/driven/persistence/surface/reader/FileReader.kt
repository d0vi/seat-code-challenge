package seat.code.infrastructure.adapter.driven.persistence.surface.reader

import seat.code.infrastructure.adapter.driven.persistence.surface.reader.exception.ConfigurationFileNotFoundException
import seat.code.infrastructure.adapter.driven.persistence.surface.reader.exception.EmptyConfigurationFileException
import seat.code.infrastructure.adapter.driven.persistence.surface.reader.exception.InvalidConfigurationFileException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

class FileReader(
    private val fileConfiguration: FileConfiguration,
    private val validator: InputCommandValidator
) {

    fun getSurfaceDimension(): Pair<Int, Int> {
        val lines = readFile()
        val surfaceConfigLine = lines[SURFACE_CONFIG_INDEX]
        validator.validateSurfaceDimensionCommand(surfaceConfigLine)
        val surfaceRawValues = surfaceConfigLine.split(SURFACE_CONFIG_DELIMITER)
        return Pair(surfaceRawValues[WIDTH_INDEX].toInt(), surfaceRawValues[HEIGHT_INDEX].toInt())
    }

    fun getMowersConfiguration(): Map<Triple<Int, Int, Char>, String> {
        val lines = readFile()
        val mowersConfig = mutableMapOf<Triple<Int, Int, Char>, String>()

        var i = MOWER_CONFIGURATION_START_INDEX
        while (i < lines.size) {
            // mower's initial position and orientation
            validator.validateMowerPositionCommand(lines[i])
            val mowerPositionRawValues = lines[i].split(MOWER_POSITION_DELIMITER)
            val x = mowerPositionRawValues[X_COORDINATE_INDEX].toInt()
            val y = mowerPositionRawValues[Y_COORDINATE_INDEX].toInt()
            val direction = mowerPositionRawValues[DIRECTION_INDEX]
            val mowerPosition = Triple(x, y, direction.first())

            // mower's control command are in the next line
            val controlCommand = lines[i + 1]
            validator.validateMowerControlCommand(controlCommand)
            mowersConfig[mowerPosition] = controlCommand
            // next mower's configuration
            i += 2
        }
        return mowersConfig
    }

    // pretty obscure method, this is what needs to be done in order to read from classpath's resources folder
    private fun readFile(): List<String> {
        val `is` = javaClass.classLoader.getResourceAsStream(fileConfiguration.name())
            ?: throw ConfigurationFileNotFoundException()
        val lines = try {
            InputStreamReader(`is`, StandardCharsets.UTF_8).use { streamReader ->
                BufferedReader(streamReader).lines().toList()
            }
        } catch (e: IOException) {
            throw InvalidConfigurationFileException()
        }
        if (!isValidFileContent(lines)) {
            throw InvalidConfigurationFileException()
        }
        return lines
    }

    private fun isValidFileContent(lines: List<String>): Boolean {
        if (lines.isEmpty()) {
            throw EmptyConfigurationFileException()
        }
        // returns true if the file has:
        // - one surface configuration
        // - one mower configuration (including mower commands)
        return lines.size >= 3 && lines.size % 2 == 1
    }

    companion object {
        private const val SURFACE_CONFIG_INDEX = 0
        private const val SURFACE_CONFIG_DELIMITER = " "
        private const val WIDTH_INDEX = 0
        private const val HEIGHT_INDEX = 0

        private const val MOWER_CONFIGURATION_START_INDEX = 1
        private const val MOWER_POSITION_DELIMITER = " "
        private const val X_COORDINATE_INDEX = 0
        private const val Y_COORDINATE_INDEX = 1
        private const val DIRECTION_INDEX = 2
    }
}
