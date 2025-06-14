package com.cursee.cw_religions.core.registry;

import com.cursee.cw_religions.core.registry.ModBlocks;
import com.cursee.cw_religions.core.registry.ModItems;
import com.terraformersmc.modmenu.ModMenu;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiConsumer;

public class ModRegistryFabric {

    public static void register() {
        ModBlocks.register(bind(BuiltInRegistries.BLOCK));
        ModItems.register(bind(BuiltInRegistries.ITEM));
        ModMenus.register(bind(BuiltInRegistries.MENU));

        ModTabs.register(bind(BuiltInRegistries.CREATIVE_MODE_TAB));
        ModBlockEntities.register(bind(BuiltInRegistries.BLOCK_ENTITY_TYPE));
    }

    private static <T> BiConsumer<T, ResourceLocation> bind(Registry<? super T> registry) {
        return (t, id) -> Registry.register(registry, id, t);
    }
}
