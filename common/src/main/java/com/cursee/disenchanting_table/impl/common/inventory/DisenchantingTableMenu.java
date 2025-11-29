package com.cursee.disenchanting_table.impl.common.inventory;

import com.cursee.disenchanting_table.impl.common.block.entity.abstr.AbstractContainerBE;
import com.cursee.disenchanting_table.impl.common.registry.ModMenus;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class DisenchantingTableMenu extends AbstractContainerMenu {

  private final Container container;

  public DisenchantingTableMenu(int containerId, Inventory playerInventory) {
    this(containerId, playerInventory, new SimpleContainer(3));
  }

  public DisenchantingTableMenu(int containerId, Inventory inventory, Container container) {
    super(ModMenus.DISENCHANTING_TABLE, containerId);

    this.container = container;

    this.addSlot(new Slot(container, AbstractContainerBE.ENCHANTED_ITEM_SLOT, 36, 47) {

      @Override
      public boolean mayPlace(ItemStack stack) {
        return DisenchantingTableMenu.canPlaceItem(AbstractContainerBE.ENCHANTED_ITEM_SLOT, stack);
      }
    });
    this.addSlot(new Slot(container, AbstractContainerBE.NORMAL_BOOK_SLOT, 76, 47) {

      @Override
      public boolean mayPlace(ItemStack stack) {
        return DisenchantingTableMenu.canPlaceItem(AbstractContainerBE.NORMAL_BOOK_SLOT, stack);
      }
    });
    this.addSlot(new Slot(container, AbstractContainerBE.OUTPUT_SLOT, 134, 47) {

      @Override
      public boolean mayPlace(ItemStack stack) {
        return DisenchantingTableMenu.canPlaceItem(AbstractContainerBE.OUTPUT_SLOT, stack);
      }
    });

    this.createInventorySlots(inventory);
  }

  public static boolean canPlaceItem(int slotIndex, ItemStack stack) {

    if (slotIndex == AbstractContainerBE.ENCHANTED_ITEM_SLOT && !EnchantmentHelper.getEnchantments(stack).isEmpty()) {
      return true;
    } else
      return slotIndex == AbstractContainerBE.NORMAL_BOOK_SLOT && stack.getItem() instanceof BookItem;
  }

  private void createInventorySlots(Inventory inventory) {
    for (int i = 0; i < 3; ++i) {
      for (int j = 0; j < 9; ++j) {
        this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
      }
    }

    for (int k = 0; k < 9; ++k) {
      this.addSlot(new Slot(inventory, k, 8 + k * 18, 142));
    }
  }

  @Override
  public ItemStack quickMoveStack(Player pPlayer, int slotIndex) {

    ItemStack returnedItemStack = ItemStack.EMPTY;

    Slot slot = this.slots.get(slotIndex);

    // empty slot
    if (!slot.hasItem()) {
      return returnedItemStack;
    }

    // item in slot
    ItemStack originalStack = slot.getItem();
    returnedItemStack = originalStack.copy();

    final boolean itemMovedToPlayer = slotIndex < this.container.getContainerSize();
    if (itemMovedToPlayer) {
      if (!this.moveItemStackTo(originalStack, this.container.getContainerSize(), this.slots.size(), true)) {
        return ItemStack.EMPTY;
      }
    } else if (!this.moveItemStackTo(originalStack, 0, this.container.getContainerSize(), false)) {
      return ItemStack.EMPTY;
    }

    if (originalStack.isEmpty()) {
      slot.setByPlayer(ItemStack.EMPTY);
    } else {
      slot.setChanged();
    }

    return returnedItemStack;
  }

  @Override
  public boolean stillValid(Player player) {
    return !player.isDeadOrDying();
  }
}
