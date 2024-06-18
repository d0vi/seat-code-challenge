package seat.code.domain.model.mower

enum class Command(val value: Char) {
    TURN_LEFT('L'),
    TURN_RIGHT('R'),
    GO_FORWARD('M');
}
