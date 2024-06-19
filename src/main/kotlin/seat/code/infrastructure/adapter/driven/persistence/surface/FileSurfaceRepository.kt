package seat.code.infrastructure.adapter.driven.persistence.surface

import seat.code.domain.model.mower.Coordinate
import seat.code.domain.model.mower.Direction
import seat.code.domain.model.mower.Mower
import seat.code.domain.model.surface.Height
import seat.code.domain.model.surface.Surface
import seat.code.domain.model.surface.Width
import seat.code.domain.repository.surface.SurfaceRepository
import seat.code.infrastructure.adapter.driven.persistence.surface.reader.FileReader

class FileSurfaceRepository(private val fileReader: FileReader) : SurfaceRepository {

    override fun get(): Surface {
        val (width, height) = fileReader.getSurfaceDimension()
        val surface = Surface(Width(width), Height(height))

        val mowersConfiguration = fileReader.getMowersConfiguration()
        mowersConfiguration.map {
            Mower(
                xCoordinate = Coordinate(it.key.first),
                yCoordinate = Coordinate(it.key.second),
                direction = Direction.fromValue(it.key.third)
            ) to it.value
        }.forEach { surface.placeMower(it.first, it.second) }

        return surface
    }
}