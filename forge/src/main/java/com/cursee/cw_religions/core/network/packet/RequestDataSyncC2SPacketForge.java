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

public record RequestDataSyncC2SPacketForge() {

    public void encode(FriendlyByteBuf byteBuffer) {}

    public static RequestDataSyncC2SPacketForge decode(FriendlyByteBuf data) {
        return new RequestDataSyncC2SPacketForge();
    }

//    public static void handle(RequestDataSyncC2SPacketForge packet, Supplier<NetworkEvent.Context> contextSupplier) {
//        contextSupplier.get().enqueueWork(() ->
//                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
//                        DataSyncClientHandlerForge.registerS2CPacketHandler(packet, contextSupplier))
//        );
//        contextSupplier.get().setPacketHandled(true);
//    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        System.out.println("server received request");
        DataSyncS2CPacketForge.createAndSend(contextSupplier.get().getSender());
    }

    public static void createAndSend() {
        System.out.println("client requested data sync packet");
        CWReligionsNetworkForge.sendToServer(new RequestDataSyncC2SPacketForge());
    }
}
