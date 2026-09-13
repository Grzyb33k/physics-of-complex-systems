plugins {
    kotlin("jvm") version "2.3.20"
    application
}

group = "pl.kamil"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(25)
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("pl.kamil.MainKt")
}

