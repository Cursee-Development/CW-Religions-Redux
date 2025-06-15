package com.cursee.cw_religions.core.data.struct;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Religion {

    public static Religion NULL = new Religion(' ', "", 0xFFFFFFFF, 0, UUID.randomUUID(), Set.of(UUID.randomUUID()), Set.of(UUID.randomUUID()));

    private final char symbol;
    private final String name;
    private int chatColor;
    private int piety;

    private final UUID prophetUUID;
    private Set<UUID> laymen;
    private Set<UUID> priests;

    public Religion(char symbol, String name, int chatColor, int piety, UUID prophetUUID, Set<UUID> laymen, Set<UUID> priests) {
        this.symbol = symbol;
        this.name = name;
        this.chatColor = chatColor;
        this.piety = piety;
        this.prophetUUID = prophetUUID;
        this.laymen = laymen;
        this.priests = priests;
    }

    public CompoundTag getTag() {
        return saveToTag(this, new CompoundTag());
    }

    private static CompoundTag saveToTag(Religion religion, CompoundTag tag) {

        tag.putString("symbol", String.valueOf(religion.symbol()));
        tag.putString("name", religion.name());
        tag.putInt("color", religion.color());
        tag.putInt("piety", religion.currentPiety());
        tag.putString("prophet", religion.prophet().toString());

        ListTag laymanList = new ListTag();
        for (UUID layman : religion.laymen()) {
            laymanList.add(StringTag.valueOf(layman.toString()));
        }
        tag.put("laymen", laymanList);

        ListTag priestList = new ListTag();
        for (UUID priest : religion.priests()) {
            priestList.add(StringTag.valueOf(priest.toString()));
        }
        tag.put("priests", priestList);

        return tag;
    }

    public static Religion fromTag(CompoundTag tag) {

        char symbol = tag.getString("symbol").charAt(0);
        String name = tag.getString("name");
        int chatColor = tag.getInt("color");
        int piety = tag.getInt("piety");
        UUID prophet = UUID.fromString(tag.getString("prophet"));

        Set<UUID> laymen = new HashSet<>();
        ListTag laymanList = tag.getList("laymen", Tag.TAG_STRING);
        for (Tag entry : laymanList) {
            laymen.add(UUID.fromString(entry.getAsString()));
        }

        Set<UUID> priests = new HashSet<>();
        ListTag priestList = tag.getList("priests", Tag.TAG_STRING);
        for (Tag entry : priestList) {
            priests.add(UUID.fromString(entry.getAsString()));
        }

        return new Religion(symbol, name, chatColor, piety, prophet, laymen, priests);
    }

    public String symbol() {
        return String.valueOf(symbol);
    }

    public String name() {
        return name;
    }

    public UUID prophet() {
        return prophetUUID;
    }

    public int color() {
        return chatColor;
    }

    public int currentPiety() {
        return piety;
    }

    public int maxPiety() {

        // "When a Religion is created, the base Piety Cap should be 100"
        // "For every member, the religion should gain 100 maximum piety points."
        int gainedPiety = (laymen().size() + priests().size()) * 100;
        return 100 + gainedPiety;
    }

    public Set<UUID> laymen() {
        return laymen;
    }

    public void addLaymen(UUID laymen) {
        this.laymen.add(laymen);
    }

    public Set<UUID> priests() {
        return priests;
    }

    public void addPriest(UUID priest) {
        this.priests.add(priest);
    }
}
