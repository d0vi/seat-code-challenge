package seat.code.domain.model.surface

data class Height(val value: Int) {
    init {
        require(value > 0) {
            "Surface height must be greater than 0"
        }
    }
}
