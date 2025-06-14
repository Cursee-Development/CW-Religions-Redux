package com.cursee.cw_religions.core.registry;

import com.cursee.cw_religions.CWReligions;
import com.cursee.cw_religions.Constants;
import com.cursee.cw_religions.platform.Services;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.BiConsumer;

public class ModTabs {

    public static final CreativeModeTab.Builder CW_RELIGIONS = Services.PLATFORM.tabBuilder()
            .icon(() -> new ItemStack(ModItems.ICON))
            .title(Component.translatable("itemGroup.religionsCW"))
            .displayItems((itemDisplayParameters, output) -> output.accept(ModBlocks.ALTAR));

    public static void register(BiConsumer<CreativeModeTab, ResourceLocation> consumer) {
        consumer.accept(CW_RELIGIONS.build(), CWReligions.identifier(Constants.MOD_ID));
    }
}
