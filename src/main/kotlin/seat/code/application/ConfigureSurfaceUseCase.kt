package seat.code.application

import jakarta.inject.Inject
import seat.code.domain.model.surface.Surface
import seat.code.domain.repository.surface.SurfaceRepository

class ConfigureSurfaceUseCase @Inject constructor(private val surfaceRepository: SurfaceRepository) {

    operator fun invoke(): Surface {
        return surfaceRepository.get()
    }
}
