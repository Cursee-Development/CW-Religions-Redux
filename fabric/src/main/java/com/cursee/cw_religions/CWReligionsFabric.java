package com.cursee.cw_religions;

import com.cursee.cw_religions.core.registry.ModRegistryFabric;
import net.fabricmc.api.ModInitializer;

public class CWReligionsFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        CWReligions.init();
        ModRegistryFabric.register();
    }
}
