plugins {
    id("fabric-loom") version "0.12-SNAPSHOT"
}

val minecraftVersion: String by project
val yarnMappings: String by project
val fabricLoaderVersion: String by project

dependencies {
    minecraft("com.mojang:minecraft:${minecraftVersion}")
    mappings("net.fabricmc:yarn:${yarnMappings}:v2")
    modImplementation("net.fabricmc:fabric-loader:${fabricLoaderVersion}")
}

tasks {
    processResources {
        inputs.property("version", rootProject.version)

        filesMatching("fabric.mod.json") {
            expand("version" to rootProject.version)
        }
    }
}