package com.cursee.cw_religions;

import com.cursee.cw_religions.core.network.CWReligionsNetworkForge;
import com.cursee.cw_religions.core.registry.ModRegistryForge;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.function.Consumer;

@Mod(Constants.MOD_ID)
public class CWReligionsForge {

    public static IEventBus EVENT_BUS;

    public CWReligionsForge(FMLJavaModLoadingContext context) {
        CWReligions.init();
        EVENT_BUS = context.getModEventBus();
        if (FMLEnvironment.dist == Dist.CLIENT) new CWReligionsClientForge(EVENT_BUS);
        ModRegistryForge.register(EVENT_BUS);

        CWReligionsNetworkForge.init();

        MinecraftForge.EVENT_BUS.addListener((Consumer<ServerStartedEvent>) event -> CWReligions.attachToServer(event.getServer()));
        MinecraftForge.EVENT_BUS.addListener((Consumer<EntityJoinLevelEvent>) event -> CWReligions.onPlayerJoinedLevel(event.getEntity()));
    }

    @SuppressWarnings("removal")
    public CWReligionsForge() {
        this(FMLJavaModLoadingContext.get());
    }
}