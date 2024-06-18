plugins {
    kotlin("jvm") version "2.0.0"
}

group = "seat.code"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
