plugins {
    application
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("com.github.johnrengelman.shadow")
}

val projectVersion: String by project
val jsoupVersion: String by project
val kotlinxCoroutinesVersion: String by project
val jvmTarget: String by project
val kotlinStdlib: String by project
val kotlinxSerializationVersion: String by project
val progressbarVersion: String by project

group = "net.pearx.scraper.bashim"
version = projectVersion

repositories {
    jcenter()
}

application {
    mainClassName = "$group.Main"
}


dependencies {
    implementation(kotlin("stdlib${if (kotlinStdlib.isEmpty()) "" else "-$kotlinStdlib"}"))
    implementation("org.jsoup:jsoup:$jsoupVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxSerializationVersion")
    implementation("me.tongfei:progressbar:$progressbarVersion")
}

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = jvmTarget
    }
}