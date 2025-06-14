package com.cursee.cw_religions.core.world.inventory;

import com.cursee.cw_religions.core.registry.ModMenus;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;

public class AltarMenu extends AbstractContainerMenu {

    public AltarMenu(int containerID, Inventory playerInventory) {
        this(containerID, playerInventory, ContainerLevelAccess.NULL);
    }

    public AltarMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(ModMenus.ALTAR, containerId);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return !player.isDeadOrDying();
    }
}
