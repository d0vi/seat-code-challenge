package seat.code.infrastructure.framework.configuration

import org.koin.dsl.module
import seat.code.domain.repository.surface.SurfaceRepository
import seat.code.infrastructure.adapter.driven.persistence.surface.FileSurfaceRepository
import seat.code.infrastructure.adapter.driven.persistence.surface.reader.FileConfiguration
import seat.code.infrastructure.adapter.driven.persistence.surface.reader.FileReader
import seat.code.infrastructure.adapter.driven.persistence.surface.reader.InputCommandValidator
import java.nio.file.Path
import java.util.Properties

val repositoryModule = module {

    single<SurfaceRepository> { FileSurfaceRepository(get()) }

    single { FileReader(get(), get()) }

    single {
        val properties = Properties()
        properties.load(this::class.java.getResourceAsStream("/application.properties"))
        val file = properties.getProperty("config.file")
        FileConfiguration(file, Path.of(file))
    }

    single { InputCommandValidator() }
}
