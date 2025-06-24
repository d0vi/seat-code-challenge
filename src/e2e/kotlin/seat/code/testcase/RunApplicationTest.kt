package seat.code.testcase

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import seat.code.application.ConfigureSurfaceUseCase
import seat.code.application.RunMowersUseCase
import seat.code.infrastructure.framework.configuration.repositoryModule
import seat.code.infrastructure.framework.configuration.useCaseModule

class RunApplicationTest : KoinTest {

    private val configureSurface: ConfigureSurfaceUseCase by inject()
    private val runMowers: RunMowersUseCase by inject()

    @BeforeEach
    fun beforeEach() {
        startKoin {
            modules(repositoryModule, useCaseModule)
        }
    }

    @AfterEach
    fun afterEach() {
        stopKoin()
    }

    @Test
    fun `should configure the surface and run the mowers`() {
        val surface = configureSurface()
        runMowers(surface)
        val mower = surface.mowers().first()
        assertEquals(5, mower.xCoordinate())
        assertEquals(1, mower.yCoordinate())
        assertEquals('E', mower.direction())
    }
}
