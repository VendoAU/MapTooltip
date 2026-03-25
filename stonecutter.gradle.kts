plugins {
    id("dev.kikugie.stonecutter")
    id("net.fabricmc.fabric-loom-remap") version "1.15-SNAPSHOT" apply false
}

stonecutter active "26.1-fabric"

// See https://stonecutter.kikugie.dev/wiki/config/params
stonecutter parameters {
    swaps["mod_version"] = "\"${property("mod.version")}\";"
    swaps["minecraft"] = "\"${node.metadata.version.split("-")[0]}\";"
    constants["release"] = property("mod.id") != "template"
    constants.match(node.metadata.project.substringAfterLast("-"), "fabric", "neoforge")

    replacements.string(current.parsed < "26.1") {
        replace("GuiGraphicsExtractor", "GuiGraphics")
        replace("ClientTooltipComponentCallback", "TooltipComponentCallback")
    }
    replacements.string(current.parsed < "1.20") {
        replace("GuiGraphics", "PoseStack")
    }
}