package seat.code

import com.google.inject.Guice
import jakarta.inject.Inject
import seat.code.application.ConfigureSurfaceUseCase
import seat.code.application.RunMowersUseCase
import seat.code.application.ShowMowersPositionUseCase
import seat.code.infrastructure.framework.configuration.RepositoryModule
import seat.code.infrastructure.framework.configuration.UseCaseModule

class Application @Inject constructor(
    val configureSurface: ConfigureSurfaceUseCase,
    val runMowers: RunMowersUseCase,
    val showMowersPosition: ShowMowersPositionUseCase
) {

    fun bootstrap() {
        // first, we set up our surface area and our mowers in their respective positions
        val surface = configureSurface()
        // then, we fire up the mowers and put them to work
        runMowers(surface)
        // finally, we show the final position of our mowers
        showMowersPosition(surface)
    }
}

fun main() {
    val injector = Guice.createInjector(RepositoryModule(), UseCaseModule())
    val app = injector.getInstance(Application::class.java)

    app.bootstrap()
}
