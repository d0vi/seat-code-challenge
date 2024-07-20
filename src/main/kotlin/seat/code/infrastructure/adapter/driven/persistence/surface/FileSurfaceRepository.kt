package seat.code.infrastructure.adapter.driven.persistence.surface

import jakarta.inject.Inject
import seat.code.domain.model.mower.Mower
import seat.code.domain.model.surface.Surface
import seat.code.domain.repository.surface.SurfaceRepository
import seat.code.infrastructure.adapter.driven.persistence.surface.reader.FileReader

class FileSurfaceRepository @Inject constructor(private val fileReader: FileReader) : SurfaceRepository {

    override fun get(): Surface {
        val (width, height) = fileReader.getSurfaceDimension()
        val surface = Surface(width, height)

        val mowersConfiguration = fileReader.getMowersConfiguration()
        mowersConfiguration.map {
            Mower(
                xCoordinate = it.key.first,
                yCoordinate = it.key.second,
                direction = it.key.third
            ) to it.value
        }.forEach { surface.placeMower(it.first, it.second) }

        return surface
    }
}