package com.cursee.cw_religions.client.network.packet;

import com.cursee.cw_religions.CWReligionsClient;
import com.cursee.cw_religions.core.network.packet.DataSyncS2CPacketForge;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DataSyncClientHandlerForge {

    public static void registerS2CPacketHandler(DataSyncS2CPacketForge packet, Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            CWReligionsClient.religionsData = packet.religionsData();
        });
    }
}
