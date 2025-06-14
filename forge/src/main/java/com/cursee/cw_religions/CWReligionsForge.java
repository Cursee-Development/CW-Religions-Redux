package com.cursee.cw_religions;

import com.cursee.cw_religions.core.registry.ModRegistryForge;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(Constants.MOD_ID)
public class CWReligionsForge {

    public static IEventBus EVENT_BUS;

    public CWReligionsForge(FMLJavaModLoadingContext context) {
        CWReligions.init();
        EVENT_BUS = context.getModEventBus();
        if (FMLEnvironment.dist == Dist.CLIENT) new CWReligionsClientForge(EVENT_BUS);
        ModRegistryForge.register(EVENT_BUS);
    }

    @SuppressWarnings("removal")
    public CWReligionsForge() {
        this(FMLJavaModLoadingContext.get());
    }
}