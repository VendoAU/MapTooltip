package com.vendoau.maptooltip;

import net.minecraft.client.Minecraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class Constants {

    public static final String MOD_ID = "maptooltip";
    public static final String MOD_NAME = "MapTooltip";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);
    public static final Path CONFIG_DIR = Minecraft.getInstance().gameDirectory.toPath().resolve("config").resolve("MapTooltip");
}
