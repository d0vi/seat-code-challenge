package seat.code.infrastructure.framework.configuration

import org.koin.dsl.module
import seat.code.application.ConfigureSurfaceUseCase
import seat.code.application.RunMowersUseCase
import seat.code.application.ShowMowersPositionUseCase

val useCaseModule = module {

    single { ConfigureSurfaceUseCase(get()) }

    single { RunMowersUseCase() }

    single { ShowMowersPositionUseCase() }
}
