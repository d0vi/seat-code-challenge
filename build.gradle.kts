plugins {
    application
    kotlin("jvm") version "2.3.0"
}

group = "seat.code"
version = "1.0.0"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

dependencies {
    implementation("io.insert-koin:koin-core:4.1.1")

    testImplementation(kotlin("test"))
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter()

            dependencies {
                implementation("org.mockito:mockito-core:5.21.0")
                implementation("org.mockito:mockito-junit-jupiter:5.21.0")
            }
        }

        val e2e by registering(JvmTestSuite::class) {
            dependencies {
                implementation(project())
                implementation("io.insert-koin:koin-core:4.1.1")
                implementation("io.insert-koin:koin-test:4.1.1")
            }

            targets {
                all {
                    testTask.configure {
                        shouldRunAfter(test)
                    }
                }
            }
        }
    }
}

tasks.check {
    dependsOn(testing.suites.named("e2e"))
}

application {
    mainClass = "seat.code.ApplicationKt"
}
