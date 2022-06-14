rootProject.name = "MapTooltip"
include("Fabric")
include("Forge")

pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/")
        maven("https://files.minecraftforge.net/maven")
        mavenCentral()
        gradlePluginPortal()
    }
}
