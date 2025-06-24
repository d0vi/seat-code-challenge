package seat.code

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import seat.code.application.ConfigureSurfaceUseCase
import seat.code.application.RunMowersUseCase
import seat.code.application.ShowMowersPositionUseCase
import seat.code.infrastructure.framework.configuration.repositoryModule
import seat.code.infrastructure.framework.configuration.useCaseModule

class Application : KoinComponent {
    private val configureSurface: ConfigureSurfaceUseCase by inject()
    private val runMowers: RunMowersUseCase by inject()
    private val showMowersPosition: ShowMowersPositionUseCase by inject()

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
    startKoin {
        modules(repositoryModule, useCaseModule)
    }
    
    val app = Application()
    app.bootstrap()
}
