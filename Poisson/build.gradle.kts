import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    application
}

group = "me.edmiikk"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.web3j.eth2:beacon-node-api:1.0.0")
    implementation("org.iq80.leveldb:leveldb:0.12")
    implementation("org.iq80.leveldb:leveldb-api:0.12")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
    doLast {

    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}