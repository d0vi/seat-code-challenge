package seat.code.application

import seat.code.domain.model.surface.Surface

class ShowMowersPositionUseCase {

    operator fun invoke(surface: Surface) {
        val mowers = surface.mowers()
        mowers.forEach { println(it) }
    }
}
