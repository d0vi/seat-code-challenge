package seat.code.infrastructure.framework.configuration

import com.google.inject.AbstractModule
import com.google.inject.Provides
import seat.code.domain.repository.surface.SurfaceRepository
import seat.code.infrastructure.adapter.driven.persistence.surface.FileSurfaceRepository
import seat.code.infrastructure.adapter.driven.persistence.surface.reader.FileConfiguration
import seat.code.infrastructure.adapter.driven.persistence.surface.reader.FileReader
import seat.code.infrastructure.adapter.driven.persistence.surface.reader.InputCommandValidator
import java.nio.file.Path
import java.util.Properties

class RepositoryModule : AbstractModule() {

    @Provides
    fun provideSurfaceRepository(fileReader: FileReader): SurfaceRepository = FileSurfaceRepository(fileReader)

    @Provides
    fun provideFileReader(fileConfiguration: FileConfiguration, validator: InputCommandValidator): FileReader =
        FileReader(fileConfiguration, validator)

    @Provides
    fun provideFileConfiguration(): FileConfiguration {
        val properties = Properties()
        properties.load(this.javaClass.getResourceAsStream("/application.properties"))
        val file = properties.getProperty("config.file")
        return FileConfiguration(file, Path.of(file))
    }

    @Provides
    fun provideInputValidator(): InputCommandValidator = InputCommandValidator()
}
