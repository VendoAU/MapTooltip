package com.vendoau.maptooltip;

import com.mojang.serialization.JavaOps;
import com.vendoau.maptooltip.mixin.ClientLevelAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.saveddata.maps.MapId;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.vendoau.maptooltip.Constants.CONFIG_DIR;
import static com.vendoau.maptooltip.Constants.LOGGER;

public class MapCache {

    private static final List<MapId> cachedIds = new ArrayList<>();

    public static void load(ServerData server, ClientLevel level) {
        if (server == null) return;

        LOGGER.info("Loading map data for {}", server.ip);
        final Path serverDir = getServerDir(server);
        if (!Files.exists(serverDir)) return;

        try {
            List<Path> paths = new ArrayList<>();
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(serverDir)) {
                for (Path path : stream) {
                    paths.add(path);
                }
            }

            cachedIds.clear();
            CompletableFuture.runAsync(() -> {
                for (Path path : paths) {
                    load(path, level);
                }
                Minecraft.getInstance().execute(() -> {
                    if (Minecraft.getInstance().player != null) {
                        Minecraft.getInstance().player.displayClientMessage(Component.translatable("message.maptooltip.loaded"), false);
                    }
                });
            });

        } catch (IOException e) {
            LOGGER.error("Failed to load map data");
            LOGGER.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
        }
    }

    private static void load(Path path, ClientLevel level) {
        final MapId mapId = getMapIdFromPath(path);
        try {
            final CompoundTag tag = NbtIo.readCompressed(path, NbtAccounter.unlimitedHeap()).getCompoundOrEmpty("data");
            final MapItemSavedData data = MapItemSavedData.CODEC.parse(level.registryAccess().createSerializationContext(JavaOps.INSTANCE), tag).getOrThrow();

            synchronized (level) {
                final Map<MapId, MapItemSavedData> mapIds = ((ClientLevelAccessor) level).getDataForMaps();
                mapIds.put(mapId, data);
            }

            synchronized (cachedIds) {
                cachedIds.add(mapId);
            }

            LOGGER.info("Loaded map data ({})", mapId.id());
        } catch (IOException e) {
            LOGGER.error("Failed to load map data ({})", mapId.id());
            LOGGER.error("Data is probably corrupted, deleting...");
            delete(path);
        }
    }

    private static void delete(Path path) {
        try {
            Files.deleteIfExists(path);
            LOGGER.info("File successfully deleted");
        } catch (IOException e) {
            LOGGER.error("Failed to delete file");
        }
    }

    private static MapId getMapIdFromPath(Path path) {
        final String fileName = path.getFileName().toString();
        final int id = Integer.parseInt(fileName.substring(4, fileName.length() - 4));
        return new MapId(id);
    }

    private static String getServerAddress(ServerData server) {
        final String[] split = server.ip.split(":");

        String ip = split[0];
        if (ip.equals("127.0.0.1")) {
            ip = "localhost";
        }
        if (split.length == 1) {
            return ip;
        }

        final String port = split[1];
        if (port.equals("25565")) {
            return ip;
        }
        // Use underscore instead of colon because windows can't have colons in paths
        return ip + "_" + port;
    }

    private static Path getServerDir(ServerData server) {
        final String address = getServerAddress(server);
        return CONFIG_DIR.resolve("cache").resolve(address);
    }

    public static void save(@Nullable ServerData server, ClientLevel level) {
        if (server == null) return;

        LOGGER.info("Saving map data for {}", server.ip);
        ((ClientLevelAccessor) level).getDataForMaps().forEach((mapId, data) -> {
            // Don't save if the map hasn't been changed
            if (cachedIds.contains(mapId)) return;
            try {
                final Path serverDir = getServerDir(server);
                Files.createDirectories(serverDir);
                final RegistryAccess provider = level.registryAccess();
                final CompoundTag savedData = (CompoundTag) MapItemSavedData.CODEC.encodeStart(provider.createSerializationContext(JavaOps.INSTANCE), data).getOrThrow();
                final Path path = serverDir.resolve(mapId.key() + ".dat");
                NbtIo.writeCompressed(savedData, path);
                LOGGER.info("Saved map data ({})", mapId.id());
            } catch (IOException e) {
                LOGGER.error("Failed to save map data ({})", mapId.id());
                LOGGER.error("{}: {}", e.getClass().getSimpleName(), e.getMessage());
            }
        });
    }

    public static void update(MapId mapId) {
        cachedIds.remove(mapId);
    }
}
