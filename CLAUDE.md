# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is **Skynet**, a robotic lawn mower simulation application written in Kotlin. The application simulates mowers moving on a rectangular surface following predefined commands (L=left, R=right, M=move forward).

## Build System & Commands

This project uses **Gradle** with Kotlin DSL and requires **JDK 26**.

### Essential Commands
- `./gradlew run` - Run the main application (processes input.txt and outputs mower positions)
  - Expected output: `1 3 N` and `5 1 E`
- `./gradlew build` - Full build including compilation, tests, and e2e tests
- `./gradlew test` - Run unit tests only
- `./gradlew e2e` - Run end-to-end tests only
- `./gradlew check` - Run all tests (unit + e2e)
- `./gradlew test --tests "seat.code.domain.model.mower.MowerTest"` - Run a single test class
- `./gradlew test --tests "seat.code.domain.model.mower.MowerTest.should turn accordingly"` - Run a single test method

### Testing Structure
- **Unit tests**: `src/test/kotlin/` - Uses JUnit Jupiter + Mockito
- **E2E tests**: `src/e2e/kotlin/` - Uses KoinTest; starts/stops Koin context in `@BeforeEach`/`@AfterEach`
- `./gradlew check` runs both suites (e2e depends on unit tests passing first)

## Architecture

The codebase follows **Hexagonal Architecture** (Ports & Adapters) with clean separation:

### Core Structure
- **Domain Layer** (`src/main/kotlin/seat/code/domain/`):
  - `model/`: Core entities (Surface, Mower, Direction, Command, Coordinate)
  - `repository/`: Interfaces for data access
  - `exception/`: Domain-specific exceptions

- **Application Layer** (`src/main/kotlin/seat/code/application/`):
  - Use cases: `ConfigureSurfaceUseCase`, `RunMowersUseCase`, `ShowMowersPositionUseCase`

- **Infrastructure Layer** (`src/main/kotlin/seat/code/infrastructure/`):
  - `adapter/driven/persistence/`: File-based repository implementation
  - `framework/configuration/`: Dependency injection modules (Koin)

### Key Patterns
- **Dependency Injection**: Uses Koin with modules in `infrastructure/framework/configuration/`
- **Repository Pattern**: `SurfaceRepository` interface with `FileSurfaceRepository` implementation
- **Command Pattern**: Mower movements are commands (L, R, M) processed sequentially
- **Value Objects**: `Width`, `Height`, `Coordinate` validate their int input on construction
- **Surface as aggregate root**: `Surface` owns the mowers list; mowers are added via `placeMower(mower, commands)` which stores command sequences alongside each mower

### Application Flow
1. `ConfigureSurfaceUseCase` - Loads surface dimensions and mower positions from input file
2. `RunMowersUseCase` - Executes movement commands for each mower  
3. `ShowMowersPositionUseCase` - Outputs final mower positions

### Dependency Injection Setup
- **Modules**: `repositoryModule` and `useCaseModule` define dependency bindings
- **Koin Integration**: Main application uses `startKoin` to initialize DI container
- **Component Access**: Classes implement `KoinComponent` and use `by inject()` for dependencies

## Configuration

- **Input file**: Configured via `src/main/resources/application.properties` (`config.file=input.txt`)
- **Default input**: `src/main/resources/input.txt`
- **Input format**: line 1 = `<width> <height>`; then repeating pairs: `<x> <y> <N|E|S|W>` followed by `<LRM commands>`
- **Parsing**: `FileReader` reads and validates each line via `InputCommandValidator` (regex-based); `FileSurfaceRepository` assembles the domain model from parsed output

## Dependencies

- **Koin 4.2.1** - Kotlin-native dependency injection framework
- **Kotlin Plugin 2.4.0** - Kotlin JVM plugin
- **JUnit Jupiter** - Testing framework
- **Mockito 5.23.0** - Mocking framework for tests
