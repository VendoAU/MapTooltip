plugins {
    id("fabric-loom") version "0.12-SNAPSHOT"
}

val minecraftVersion: String by project
val yarnMappings: String by project
val fabricLoaderVersion: String by project

version = "2.0.0"

base {
    archivesName.set("maptooltip-fabric-${minecraftVersion}")
}

dependencies {
    minecraft("com.mojang:minecraft:${minecraftVersion}")
    mappings("net.fabricmc:yarn:${yarnMappings}:v2")
    modImplementation("net.fabricmc:fabric-loader:${fabricLoaderVersion}")
}

tasks {
    processResources {
        filesMatching("fabric.mod.json") {
            expand(project.properties)
        }
    }
}