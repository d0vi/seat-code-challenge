package seat.code.application

import seat.code.domain.model.surface.Surface

class RunMowersUseCase {

    operator fun invoke(surface: Surface) {
        surface.cutGrass()
    }
}
