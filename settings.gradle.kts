pluginManagement {
    val kotlinVersion: String by settings
    val shadowVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion
        id("com.github.johnrengelman.shadow") version shadowVersion
    }
}

rootProject.name = "bashim-scraper"