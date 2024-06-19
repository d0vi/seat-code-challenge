package seat.code.domain.model.mower

enum class Direction(val value: Char) {
    NORTH('N'),
    EAST('E'),
    SOUTH('S'),
    WEST('W');

    companion object {
        @JvmStatic
        fun fromValue(value: Char): Direction = entries.first { it.value == value }
    }
}
