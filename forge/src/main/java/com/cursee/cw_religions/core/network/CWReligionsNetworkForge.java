package com.cursee.cw_religions.core.network;

import com.cursee.cw_religions.CWReligions;
import com.cursee.cw_religions.Constants;
import com.cursee.cw_religions.core.network.packet.DataSyncS2CPacketForge;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class CWReligionsNetworkForge {

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            CWReligions.identifier(Constants.MOD_ID),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int packetID = 0;
    private static int createNewPacketID() {
        return packetID = packetID + 1;
    }

    public static void init() {
        CWReligionsNetworkForge.INSTANCE.registerMessage(createNewPacketID(), DataSyncS2CPacketForge.class, DataSyncS2CPacketForge::encode, DataSyncS2CPacketForge::decode, DataSyncS2CPacketForge::handle);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        CWReligionsNetworkForge.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToServer(MSG message) {
        CWReligionsNetworkForge.INSTANCE.sendToServer(message);
    }
}
