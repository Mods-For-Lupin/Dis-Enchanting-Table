package com.cursee.disenchanting_table.impl.common.inventory;

import com.cursee.disenchanting_table.impl.common.registry.ModMenus;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class DisenchantingTableMenu extends AbstractContainerMenu {

  public DisenchantingTableMenu(int containerId, Inventory playerInventory) {
    super(ModMenus.DISENCHANTING_TABLE, containerId);
  }

  @Override
  public ItemStack quickMoveStack(Player player, int slotIndex) {
    return ItemStack.EMPTY;
  }

  @Override
  public boolean stillValid(Player player) {
    return !player.isDeadOrDying();
  }
}
