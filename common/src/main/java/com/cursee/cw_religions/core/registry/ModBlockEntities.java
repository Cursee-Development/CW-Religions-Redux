package com.cursee.cw_religions.core.registry;

import com.cursee.cw_religions.CWReligions;
import com.cursee.cw_religions.core.world.block.entity.AltarBlockEntity;
import com.cursee.cw_religions.platform.Services;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.BiConsumer;

public class ModBlockEntities {

    public static final BlockEntityType<AltarBlockEntity> ALTAR = Services.PLATFORM.blockEntity(AltarBlockEntity::new, ModBlocks.ALTAR);

    public static void register(BiConsumer<BlockEntityType<?>, ResourceLocation> consumer) {
        consumer.accept(ALTAR, CWReligions.identifier("altar"));
    }
}
