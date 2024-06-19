package seat.code.infrastructure.adapter.driven.persistence.surface.reader

import java.nio.file.Path

class FileConfiguration(private val name: String, private val path: Path) {
    fun name() = name

    fun path() = path
}
