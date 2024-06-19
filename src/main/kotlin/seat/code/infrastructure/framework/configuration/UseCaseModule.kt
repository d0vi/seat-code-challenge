package seat.code.infrastructure.framework.configuration

import com.google.inject.AbstractModule
import com.google.inject.Provides
import seat.code.application.ConfigureSurfaceUseCase
import seat.code.application.RunMowersUseCase
import seat.code.application.ShowMowersPositionUseCase
import seat.code.domain.repository.surface.SurfaceRepository

class UseCaseModule : AbstractModule() {

    @Provides
    fun provideConfigureSurfaceUseCase(surfaceRepository: SurfaceRepository): ConfigureSurfaceUseCase =
        ConfigureSurfaceUseCase(surfaceRepository)

    @Provides
    fun provideRunMowersUseCase(): RunMowersUseCase = RunMowersUseCase()

    @Provides
    fun provideShowMowersPositionUseCase(): ShowMowersPositionUseCase = ShowMowersPositionUseCase()
}
