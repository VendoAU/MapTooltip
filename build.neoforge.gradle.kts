plugins {
    id("net.neoforged.moddev") version "2.0.141"
    id("dev.kikugie.fletching-table") version "0.1.0-alpha.22"
}

version = "${property("mod.version")}+${sc.current.version}-neoforge"
base.archivesName = property("mod.id") as String

val requiredJava = when {
    sc.current.parsed >= "26.1" -> JavaVersion.VERSION_25
    sc.current.parsed >= "1.20.5" -> JavaVersion.VERSION_21
    sc.current.parsed >= "1.18" -> JavaVersion.VERSION_17
    sc.current.parsed >= "1.17" -> JavaVersion.VERSION_16
    else -> JavaVersion.VERSION_1_8
}

java {
    sourceCompatibility = requiredJava
    targetCompatibility = requiredJava
}

neoForge {
    version = property("deps.neoforge") as String

    runs {
        register("client") {
            gameDirectory = file("run/")
            client()
        }
    }

    mods {
        register(property("mod.id") as String) {
            sourceSet(sourceSets["main"])
        }
    }
}

tasks {
    processResources {
        exclude("fabric.mod.json")

        inputs.property("id", project.property("mod.id"))
        inputs.property("name", project.property("mod.name"))
        inputs.property("version", project.property("mod.version"))
        inputs.property("minecraft", project.property("mod.mc_dep"))
        inputs.property("description", project.property("mod.description"))
        inputs.property("neoforge", project.property("deps.neoforge"))

        val props = mapOf(
            "id" to project.property("mod.id"),
            "name" to project.property("mod.name"),
            "version" to project.property("mod.version"),
            "minecraft" to project.property("mod.mc_dep"),
            "description" to project.property("mod.description"),
            "neoforge" to project.property("deps.neoforge")
        )

        filesMatching("META-INF/neoforge.mods.toml") { expand(props) }

        val mixinJava = "JAVA_${requiredJava.majorVersion}"
        filesMatching("*.mixins.json") { expand("java" to mixinJava) }
    }

    named("createMinecraftArtifacts") {
        dependsOn("stonecutterGenerate")
    }
}

fletchingTable {
    j52j.register("main") {
        extension("json", "maptooltip.mixins.json5")
    }
}