plugins {
    application
    kotlin("jvm") version "2.1.10"
}

group = "seat.code"
version = "1.0.0"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

dependencies {
    implementation("com.google.inject:guice:7.0.0")

    testImplementation(kotlin("test"))
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter()

            dependencies {
                implementation("org.mockito:mockito-core:5.14.2")
                implementation("org.mockito:mockito-junit-jupiter:5.15.2")
            }
        }

        val e2e by registering(JvmTestSuite::class) {
            dependencies {
                implementation(project())
                implementation("com.google.inject:guice:7.0.0")
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
