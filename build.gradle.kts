plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "ch.usi.si.codelounge"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.0")
    implementation("org.jetbrains.kotlin:kotlin-script-util:1.8.22")
    implementation("org.jetbrains.kotlin:kotlin-script-runtime:1.9.0")
    implementation("org.jetbrains.kotlin:kotlin-scripting-jvm-host:1.9.0")
    implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.9.0")
    implementation("org.jetbrains.kotlin:kotlin-scripting-compiler-embeddable:1.9.0")
    implementation("org.jetbrains.kotlin:kotlin-scripting-ide-services:1.9.0")

}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}