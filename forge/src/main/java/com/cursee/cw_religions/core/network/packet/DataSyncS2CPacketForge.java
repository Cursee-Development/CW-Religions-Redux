package com.cursee.cw_religions.core.network.packet;

import com.cursee.cw_religions.CWReligions;
import com.cursee.cw_religions.client.network.packet.DataSyncClientHandlerForge;
import com.cursee.cw_religions.core.data.ReligionsData;
import com.cursee.cw_religions.core.network.CWReligionsNetworkForge;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record DataSyncS2CPacketForge(ReligionsData religionsData) {

    public void encode(FriendlyByteBuf byteBuffer) {
        religionsData.encodeToByteBuffer(byteBuffer);
    }

    public static DataSyncS2CPacketForge decode(FriendlyByteBuf data) {
        return new DataSyncS2CPacketForge(ReligionsData.decodeFromByteBuffer(data));
    }

    public static void handle(DataSyncS2CPacketForge packet, Supplier<NetworkEvent.Context> contextSupplier) {
        System.out.println("client received data sync packet");
        contextSupplier.get().enqueueWork(() ->
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                        DataSyncClientHandlerForge.registerS2CPacketHandler(packet, contextSupplier))
        );
        contextSupplier.get().setPacketHandled(true);
    }

    public static void createAndSend(ServerPlayer player) {
        System.out.println("server sent data sync packet");
        CWReligionsNetworkForge.sendToPlayer(new DataSyncS2CPacketForge(CWReligions.freshData()), player);
    }
}
