package com.cursee.cw_religions.core.world.block.entity;

import com.cursee.cw_religions.core.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AltarBlockEntity extends BlockEntity {

    public AltarBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.ALTAR, pos, blockState);
    }

    public static void clientTick(Level level, BlockPos blockPos, BlockState blockState, AltarBlockEntity altar) {
    }

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, AltarBlockEntity altar) {
    }
}
