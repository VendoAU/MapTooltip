pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
        maven("https://maven.kikugie.dev/snapshots") { name = "KikuGie Snapshots" }
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.9-alpha.7"
}

stonecutter {
    create(rootProject) {
        // See https://stonecutter.kikugie.dev/wiki/start/#choosing-minecraft-versions
         versions("1.16", "1.17", "1.18", "1.19", "1.20", "1.21", "1.21.3", "1.21.4", "1.21.5", "1.21.6", "1.21.11")
        vcsVersion = "1.21.11"
    }
}

rootProject.name = "Map Tooltip"