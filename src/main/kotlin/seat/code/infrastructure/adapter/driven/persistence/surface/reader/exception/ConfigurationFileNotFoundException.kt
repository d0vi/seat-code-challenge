package seat.code.infrastructure.adapter.driven.persistence.surface.reader.exception

import java.nio.file.Path

class ConfigurationFileNotFoundException(path: Path) : RuntimeException("File $path could not be found")
