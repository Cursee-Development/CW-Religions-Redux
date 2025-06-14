package com.cursee.cw_religions.core.registry;

import com.cursee.cw_religions.CWReligions;
import com.cursee.cw_religions.core.world.inventory.AltarMenu;
import com.cursee.cw_religions.platform.Services;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

import java.util.function.BiConsumer;

public class ModMenus {

    public static final MenuType<AltarMenu> ALTAR = Services.PLATFORM.registerMenu(AltarMenu::new, FeatureFlags.VANILLA_SET);

    public static void register(BiConsumer<MenuType<?>, ResourceLocation> consumer) {
        consumer.accept(ALTAR, CWReligions.identifier("altar"));
    }
}
