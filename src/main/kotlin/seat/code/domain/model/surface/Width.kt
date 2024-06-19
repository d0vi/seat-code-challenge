package seat.code.domain.model.surface

data class Width(val value: Int) {
    init {
        require(value > 0) {
            "Surface width must be greater than 0"
        }
    }
}
