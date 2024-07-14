package seat.code.testcase

import com.google.inject.Guice
import com.google.inject.Injector
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import seat.code.application.ConfigureSurfaceUseCase
import seat.code.application.RunMowersUseCase
import seat.code.domain.model.mower.Direction
import seat.code.infrastructure.framework.configuration.RepositoryModule
import seat.code.infrastructure.framework.configuration.UseCaseModule

class RunApplicationTest {

    private val injector: Injector = Guice.createInjector(RepositoryModule(), UseCaseModule())

    @Inject
    private lateinit var configureSurface: ConfigureSurfaceUseCase

    @Inject
    private lateinit var runMowers: RunMowersUseCase

    @BeforeEach
    fun beforeEach() {
        injector.injectMembers(this)
    }

    @Test
    fun `should configure the surface and run the mowers`() {
        val surface = configureSurface()
        runMowers(surface)
        val mower = surface.mowers().first()
        assertEquals(5, mower.xCoordinate())
        assertEquals(1, mower.yCoordinate())
        assertEquals(Direction.EAST, mower.direction())
    }
}
