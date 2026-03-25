pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/") { name = "Fabric" }
        maven("https://maven.neoforged.net/releases/") { name = "Neoforged" }
        maven("https://maven.kikugie.dev/snapshots") { name = "KikuGie Snapshots" }
    }

    plugins {
        id("dev.kikugie.fletching-table") version "0.1.0-alpha.22"
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.9.1-beta.2"
}

stonecutter {
    create(rootProject) {
        fun match(version: String, vararg loaders: String) = loaders.forEach {
            if (it == "fabric" && stonecutter.eval(version, ">1.21.11")) {
                version("$version-$it", version).buildscript = "build.fabric_noremap.gradle.kts"
            } else {
                version("$version-$it", version).buildscript = "build.$it.gradle.kts"
            }
        }
        match("26.1", "fabric", "neoforge")
        match("1.21.11", "fabric", "neoforge")
        match("1.21.6", "fabric", "neoforge")
        match("1.21.3", "fabric", "neoforge")
        match("1.21", "fabric", "neoforge")
        match("1.20.6", "neoforge")
        match("1.20.5", "fabric")
        match("1.20", "fabric")
        match("1.19", "fabric")
        match("1.18", "fabric")
        match("1.17", "fabric")
        match("1.16", "fabric")
        vcsVersion = "26.1-fabric"
    }
}

rootProject.name = "Map Tooltip"