package seat.code.domain.repository.surface

import seat.code.domain.model.surface.Surface

interface SurfaceRepository {

    fun get(): Surface
}