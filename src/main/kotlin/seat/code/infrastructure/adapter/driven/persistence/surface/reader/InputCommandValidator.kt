package seat.code.infrastructure.adapter.driven.persistence.surface.reader

class InputCommandValidator {

    fun validateSurfaceDimensionCommand(surfaceDimensionCommand: String) {
        check(surfaceDimensionCommand.matches(SURFACE_DIMENSION_PATTERN)) {
            """
                The surface's dimension command is not valid.
                A valid surface's dimension command is in the form of two integers values separated by a space.
            """.trimIndent()
        }
    }

    fun validateMowerPositionCommand(mowerPositionCommand: String) {
        check(mowerPositionCommand.matches(MOWER_POSITION_PATTERN)) {
            """
                The mower's position command is not valid.
                A mower's position command is represented by a combination of X and Y coordinates
                and a letter representing one of the four cardinal compass points.
            """.trimIndent()
        }
    }

    fun validateMowerControlCommand(mowerControlCommand: String) {
        check(mowerControlCommand.matches(MOWER_CONTROL_PATTERN)) {
            """
                The mower's control command is not valid.
                A mower's control command is a simple string of letters, where the possible letters are “L”, “R” 
                and ”M”.
            """.trimIndent()
        }
    }

    companion object {
        private val SURFACE_DIMENSION_PATTERN = "^\\d+\\s\\d+\$".toRegex()
        private val MOWER_POSITION_PATTERN = "^\\d+\\s\\d+\\s[NESW]\$".toRegex()
        private val MOWER_CONTROL_PATTERN = "^[LRM]+$".toRegex()
    }
}
