package seat.code.application

import seat.code.domain.model.surface.Surface
import seat.code.domain.repository.surface.SurfaceRepository

class ConfigureSurfaceUseCase(private val surfaceRepository: SurfaceRepository) {

    operator fun invoke(): Surface {
        return surfaceRepository.get()
    }
}
