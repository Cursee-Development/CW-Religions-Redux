package com.cursee.cw_religions;

import com.cursee.cw_religions.core.registry.ModRegistryFabric;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class CWReligionsFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        CWReligions.init();
        ModRegistryFabric.register();

        ServerLifecycleEvents.SERVER_STARTED.register(CWReligions::attachToServer);
    }
}
