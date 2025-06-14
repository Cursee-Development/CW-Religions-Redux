package com.cursee.cw_religions.core.registry;

import com.cursee.cw_religions.CWReligions;
import com.cursee.cw_religions.Constants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import java.util.function.BiConsumer;

public class ModItems {

    public static final Item ICON = new Item(new Item.Properties().stacksTo(1));

    public static void register(BiConsumer<Item, ResourceLocation> consumer) {
        consumer.accept(ICON, CWReligions.identifier(Constants.MOD_ID));
        consumer.accept(new BlockItem(ModBlocks.ALTAR, new Item.Properties().stacksTo(1)), BuiltInRegistries.BLOCK.getKey(ModBlocks.ALTAR));
    }
}
