package com.cursee.cw_religions;

import com.cursee.cw_religions.core.data.ReligionsData;
import com.cursee.cw_religions.core.data.struct.Religion;
import com.cursee.cw_religions.platform.Services;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

import java.util.Set;
import java.util.UUID;

public class CWReligions {

    static boolean addDefaults = true;
    static MinecraftServer SERVER;

    public static void init() {}

    public static void attachToServer(MinecraftServer server) {
        if (SERVER == null) {
            SERVER = server;
            ReligionsData data = freshData(); // compute for the first time if it's absent

            if (addDefaults) {
                // add some default religions

                boolean failed = false;

                if (!data.addReligion(new Religion('╘', "Esquires", 0xFFFF0000, 40, UUID.randomUUID(), Set.of(UUID.randomUUID()), Set.of(UUID.randomUUID())))) failed = true;
                if (!data.addReligion(new Religion('▌', "La Bomba-Testo", 0xFF00FF00, 20, UUID.randomUUID(), Set.of(UUID.randomUUID()), Set.of(UUID.randomUUID())))) failed = true;
                if (!data.addReligion(new Religion('á', "Rick!Rick", 0xFF0000FF, 21, UUID.randomUUID(), Set.of(UUID.randomUUID()), Set.of(UUID.randomUUID())))) failed = true;

                if (failed) Constants.LOG.info("Attempted to add repeat religion");
            }
        }
        else throw new IllegalStateException("Attempted to write new reference to already assigned value: CWReligions.SERVER");
    }

    public static ReligionsData freshData() {
        if (SERVER == null) throw new IllegalStateException("Attempted to access data before SERVER was assigned.");
        return ReligionsData.fromServer(SERVER);
    }

    public static void onPlayerJoinedLevel(Entity entity) {
        if (!(entity instanceof ServerPlayer player)) return;
        Services.PLATFORM.sendDataSyncPacket(player);
    }

    public static ResourceLocation identifier(String path) {
        return new ResourceLocation(Constants.MOD_ID, path);
    }
}