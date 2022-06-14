plugins {
    id("net.minecraftforge.gradle") version "5.1.+"
}

val mcpMappingsChannel: String by project
val mcpMappingsVersion: String by project
val forgeVersion: String by project

val minecraftVersion: String by project

val modId: String by project

val archivesBaseName = "${modId}-forge-${minecraftVersion}"

dependencies {
    "minecraft"("net.minecraftforge:forge:${minecraftVersion}-${forgeVersion}")
}

minecraft {
    mappings(mcpMappingsChannel, mcpMappingsVersion)

    runs {
        create("client") {
            workingDirectory(project.file("run"))

            mods {
                create(modId) {
                    source(sourceSets.main.get())
                }
            }
        }
    }
}

tasks {
    processResources {
        inputs.property("version", rootProject.version)

        filesMatching("META-INF/mods.toml") {
            expand("version" to rootProject.version)
        }
    }

    jar {
        finalizedBy("reobfJar")
    }
}
