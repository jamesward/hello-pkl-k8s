plugins {
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.power-assert") version "2.0.0"
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    testImplementation("org.testcontainers:k3s:1.20.0")
    testImplementation("io.fabric8:kubernetes-client:6.13.1")
    testImplementation(kotlin("test"))
    testRuntimeOnly("org.bouncycastle:bcpkix-jdk15on:1.70")
    testRuntimeOnly("org.slf4j:slf4j-simple:2.0.13")
}
