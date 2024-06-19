package seat.code.infrastructure.adapter.driven.file

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import seat.code.infrastructure.adapter.driven.persistence.surface.reader.InputCommandValidator
import java.util.stream.Stream

class InputCommandValidatorTest {
    private val inputCommandValidator = InputCommandValidator()

    @ParameterizedTest
    @MethodSource("getSurfaceDimensionCommands")
    fun `should validate a valid surface's dimension command`(command: String) {
        assertDoesNotThrow { inputCommandValidator.validateSurfaceDimensionCommand(command) }
    }

    @Test
    fun `should not validate an invalid surface's dimension command`() {
        assertThrows<IllegalStateException> { inputCommandValidator.validateSurfaceDimensionCommand("A A") }
    }

    @ParameterizedTest
    @MethodSource("getMowerPositionCommands")
    fun `should validate a valid mower's position command`(command: String) {
        assertDoesNotThrow { inputCommandValidator.validateMowerPositionCommand(command) }
    }

    @Test
    fun `should not validate an invalid mower's position command`() {
        assertThrows<IllegalStateException> { inputCommandValidator.validateMowerPositionCommand("44E") }
    }

    @ParameterizedTest
    @MethodSource("getMowerControlCommands")
    fun `should validate a valid mower's control command`(command: String) {
        assertDoesNotThrow { inputCommandValidator.validateMowerControlCommand(command) }
    }

    @Test
    fun `should not validate an invalid mower's control command`() {
        assertThrows<IllegalStateException> { inputCommandValidator.validateMowerControlCommand("FOO") }
    }

    companion object {
        @JvmStatic
        fun getSurfaceDimensionCommands(): Stream<String> = Stream.of("5 5", "1 6", "10 15")

        @JvmStatic
        fun getMowerPositionCommands(): Stream<String> = Stream.of("1 2 N", "3 3 E", "5 1 S", "12 11 W")

        @JvmStatic
        fun getMowerControlCommands(): Stream<String> = Stream.of("LMLMLMLMM", "MMRMMRMRRM", "RRRRR", "MMMMM", "M")
    }
}
