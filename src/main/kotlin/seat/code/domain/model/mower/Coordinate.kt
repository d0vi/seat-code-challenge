package seat.code.domain.model.mower

data class Coordinate(val value: Int) {
    init {
        require(value >= 0) {
            "Coordinate should be equal or greater than 0"
        }
    }
}
