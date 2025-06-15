package com.cursee.cw_religions;

import com.cursee.cw_religions.core.data.ReligionsData;
import com.cursee.cw_religions.window.ModSecondaryWindowRunnable;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.function.Consumer;

public class CWReligionsClientForge {

    public static CWReligionsClientForge instance;
    private ReligionsData religionsData;

    public CWReligionsClientForge(final IEventBus modEventBus) {
        instance = this;
        init(modEventBus);
    }

    public void init(final IEventBus modEventBus) {
        modEventBus.addListener((Consumer<FMLClientSetupEvent>) event -> event.enqueueWork(CWReligionsClient::init));
        MinecraftForge.EVENT_BUS.addListener((Consumer<TickEvent.ClientTickEvent>) event -> {
            if (event.phase != TickEvent.Phase.START) return;

            if (CWReligionsClientForge.instance.religionsData != CWReligionsClient.religionsData) {
                Constants.LOG.info("Client received an update to religionsData.");
                CWReligionsClientForge.instance.religionsData = CWReligionsClient.religionsData;

                CWReligionsClientForge.instance.religionsData.getAllReligions().forEach(religion -> {
                    Constants.LOG.info("{} {}", religion.symbol(), religion.name());
                });
            }
        });

        MinecraftForge.EVENT_BUS.addListener((Consumer<TickEvent.ClientTickEvent>) event -> {
            if (event.phase == TickEvent.Phase.START) return;
            if (Minecraft.getInstance().isRunning()) return;
            ModSecondaryWindowRunnable.shutdown();
        });
    }
}
