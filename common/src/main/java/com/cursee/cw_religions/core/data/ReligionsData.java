package com.cursee.cw_religions.core.data;

import com.cursee.cw_religions.core.data.struct.Religion;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.*;

public class ReligionsData extends SavedData {

    private final Map<String, Religion> religions = new HashMap<>();

    public void encodeToByteBuffer(FriendlyByteBuf byteBuffer) {

        // how many religions to decode
        byteBuffer.writeVarInt(this.religions.size());

        for (Religion religion : this.religions.values()) {
            byteBuffer.writeUtf(String.valueOf(religion.symbol()));
            byteBuffer.writeUtf(religion.name());
            byteBuffer.writeVarInt(religion.color());
            byteBuffer.writeVarInt(religion.currentPiety());

            byteBuffer.writeUUID(religion.prophet());
            byteBuffer.writeCollection(religion.laymen(), FriendlyByteBuf::writeUUID);
            byteBuffer.writeCollection(religion.priests(), FriendlyByteBuf::writeUUID);
        }
    }

    public static ReligionsData decodeFromByteBuffer(FriendlyByteBuf byteBuffer) {

        ReligionsData data = new ReligionsData();
        int size = byteBuffer.readVarInt();

        for (int i = 0; i < size; i++) {

            char symbol = byteBuffer.readUtf().charAt(0);
            String name = byteBuffer.readUtf();
            int color = byteBuffer.readVarInt();
            int piety = byteBuffer.readVarInt();
            UUID prophet = byteBuffer.readUUID();
            Set<UUID> laymen = byteBuffer.readCollection(HashSet::new, FriendlyByteBuf::readUUID);
            Set<UUID> priests = byteBuffer.readCollection(HashSet::new, FriendlyByteBuf::readUUID);

            data.addReligion(new Religion(symbol, name, color, piety, prophet, laymen, priests));
        }

        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag religionList = new ListTag();
        for (Religion religion : getAllReligions()) {
            religionList.add(religion.getTag());
        }
        tag.put("religions", religionList);
        return tag;
    }

    public static ReligionsData fromTag(CompoundTag tag) {

        ReligionsData data = new ReligionsData();

        ListTag religions = tag.getList("religions", Tag.TAG_COMPOUND);
        for (Tag religionTag : religions) {
            Religion religion = Religion.fromTag((CompoundTag) religionTag);
            data.religions.put(religion.name(), religion);
        }

        return data;
    }

    public Collection<Religion> getAllReligions() {
        return religions.values();
    }

    public static ReligionsData fromServer(MinecraftServer server) {
        if (server == null) throw new IllegalStateException("Attempted to access data from NULL server");
        ServerLevel level = server.getLevel(Level.OVERWORLD);
        if (level == null) throw new IllegalStateException("Attempted to access level data from NULL overworld level.");
        return level.getDataStorage().computeIfAbsent(ReligionsData::fromTag, ReligionsData::new, "cw_religions_data");
    }

    public Religion getReligion(String name) {
        return religions.get(name);
    }

    public boolean addReligion(Religion religion) {
        if (religions.containsKey(religion.name())) return false;
        religions.put(religion.name(), religion);
        this.setDirty();
        return true;
    }
}
