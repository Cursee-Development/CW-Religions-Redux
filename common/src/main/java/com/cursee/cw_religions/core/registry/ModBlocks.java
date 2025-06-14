package com.cursee.cw_religions.core.registry;

import com.cursee.cw_religions.CWReligions;
import com.cursee.cw_religions.core.world.block.AltarBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.BiConsumer;

public class ModBlocks {

    public static final Block ALTAR = new AltarBlock(BlockBehaviour.Properties.of());

    public static void register(BiConsumer<Block, ResourceLocation> consumer) {
        consumer.accept(ALTAR, CWReligions.identifier("altar"));
    }
}
