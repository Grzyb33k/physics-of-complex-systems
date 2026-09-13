plugins {
    kotlin("jvm") version "2.3.21"
    application
}

group = "pl.kamil"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.apache.commons:commons-math3:3.6.1")
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

