plugins {
    kotlin("jvm") version "2.0.0"
}

group = "seat.code"
version = "1.0.0"

repositories {
    mavenCentral()
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
                implementation("org.mockito:mockito-core:5.12.0")
                implementation("org.mockito:mockito-junit-jupiter:5.12.0")
            }
        }
    }
}
