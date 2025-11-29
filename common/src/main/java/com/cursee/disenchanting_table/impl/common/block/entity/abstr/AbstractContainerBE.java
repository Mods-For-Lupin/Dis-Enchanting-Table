package com.cursee.disenchanting_table.impl.common.block.entity.abstr;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.multiplayer.chat.report.ReportEnvironment.Server;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BookItem;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractContainerBE extends BlockEntity implements WorldlyContainer {

  public static final int ENCHANTED_ITEM_SLOT = 0; // enchanted items/books
  public static final int NORMAL_BOOK_SLOT = 1; // books
  public static final int OUTPUT_SLOT = 2; // enchanted book
  public static final int SLOT_COUNT = 3;
  private static final int[] SLOTS_FOR_UP = new int[]{ENCHANTED_ITEM_SLOT, NORMAL_BOOK_SLOT};
  private static final int[] SLOTS_FOR_DOWN = new int[]{ENCHANTED_ITEM_SLOT, OUTPUT_SLOT};
  private static final int[] SLOTS_FOR_SIDES = new int[]{ENCHANTED_ITEM_SLOT, NORMAL_BOOK_SLOT};

  public static final Map<Enchantment, Integer> EMPTY_ENCHANTMENTS = EnchantmentHelper.getEnchantments(ItemStack.EMPTY);

  private NonNullList<ItemStack> items;

  public AbstractContainerBE(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
    super(type, pos, blockState);
    this.items = NonNullList.withSize(3, ItemStack.EMPTY);
  }

  @Override
  public int getContainerSize() {
    return SLOT_COUNT;
  }

  @Override
  public boolean isEmpty() {
    for (ItemStack itemStack : this.items) {
      if (!itemStack.isEmpty()) {
        return false;
      }
    }

    return true;
  }

  @Override
  public ItemStack getItem(int slot) {
    return slot >= 0 && slot < SLOT_COUNT ? this.items.get(slot) : ItemStack.EMPTY;
  }

  @Override
  public ItemStack removeItem(int slot, int amount) {
    return ContainerHelper.removeItem(this.items, slot, amount);
  }

  @Override
  public ItemStack removeItemNoUpdate(int slot) {
    return ContainerHelper.takeItem(this.items, slot);
  }

  @Override
  public void setItem(int slot, ItemStack stack) {
    if (slot >= 0 && slot < this.items.size()) {
      this.items.set(slot, stack);
    }
  }

  @Override
  public void clearContent() {
    this.items.clear();
  }

  @Override
  public int[] getSlotsForFace(Direction side) {
    if (side == Direction.UP) {
      return SLOTS_FOR_UP;
    } else {
      return side == Direction.DOWN ? SLOTS_FOR_DOWN : SLOTS_FOR_SIDES;
    }
  }

  @Override
  public boolean canPlaceItemThroughFace(int index, ItemStack itemStack, @Nullable Direction direction) {
    return this.canPlaceItem(index, itemStack);
  }

  @Override
  public boolean canTakeItemThroughFace(int slotIndex, ItemStack itemStack, Direction direction) {

    if (slotIndex == OUTPUT_SLOT) {
      return true;
    }
    else if (slotIndex == ENCHANTED_ITEM_SLOT) {
      if (itemStack.is(Items.ENCHANTED_BOOK) && EnchantmentHelper.getEnchantments(itemStack).size() == 1) {
        return true;
      }
      else {
        return EnchantmentHelper.getEnchantments(itemStack).isEmpty();
      }
    }

    return false;
  }

  @Override
  public boolean stillValid(Player player) {
    return Container.stillValidBlockEntity(this, player);
  }

  @Override
  public void load(CompoundTag tag) {
    super.load(tag);
    this.items = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);
    ContainerHelper.loadAllItems(tag, this.items);
  }

  @Override
  protected void saveAdditional(CompoundTag tag) {
    super.saveAdditional(tag);
    ContainerHelper.saveAllItems(tag, this.items);
  }

  @Override
  @SuppressWarnings("all")
  public boolean canPlaceItem(int slotIndex, ItemStack stack) {

    if (slotIndex == ENCHANTED_ITEM_SLOT && !EnchantmentHelper.getEnchantments(stack).isEmpty()) {
      return true;
    } else if (slotIndex == NORMAL_BOOK_SLOT && stack.getItem() instanceof BookItem) {
      return true;
    }

    return false;
  }

  protected void doServerTick(Level level, BlockPos pos, BlockState blockState) {

    ItemStack inputStack = this.getItem(ENCHANTED_ITEM_SLOT);
    ItemStack normalBookStack = this.getItem(NORMAL_BOOK_SLOT);
    ItemStack outputStack = this.getItem(OUTPUT_SLOT);

    if ((inputStack.isEmpty() || normalBookStack.isEmpty()) || !outputStack.isEmpty()) {
      return;
    }

    // gather enchantments from input item
    LinkedHashMap<Enchantment, Integer> inputItemEnchantments = (LinkedHashMap<Enchantment, Integer>) EnchantmentHelper.getEnchantments(inputStack);

    // disenchant
    if (!inputStack.is(Items.ENCHANTED_BOOK)) {

      // create a new enchanted book with no enchantments
      ItemStack returnedBook = new ItemStack(Items.ENCHANTED_BOOK);

      // copy all input enchantments to the book
      EnchantmentHelper.setEnchantments(inputItemEnchantments, returnedBook);

      // remove all enchantments from the input item
      EnchantmentHelper.setEnchantments(EMPTY_ENCHANTMENTS, inputStack);

      // decrease normal book count as fuel cost
      normalBookStack.shrink(1);

      // update slots
      this.setItem(ENCHANTED_ITEM_SLOT, inputStack);
      this.setItem(NORMAL_BOOK_SLOT, normalBookStack);
      this.setItem(OUTPUT_SLOT, returnedBook);

      BlockEntity.setChanged(level, pos, blockState);
    } else {

      // gather enchantments from input item as ordered keys
      ArrayList<Enchantment> inputEnchantmentKeys = new ArrayList<>(inputItemEnchantments.keySet());

      // copy the first enchantment
      Enchantment stolenEnchantment = inputEnchantmentKeys.get(0);
      Integer stolenEnchantmentLevel = inputItemEnchantments.get(stolenEnchantment);

      // create a new enchanted book with the copied enchantment
      ItemStack outputEnchantmentBook = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(stolenEnchantment, stolenEnchantmentLevel));

      // remove the copied enchantment from the input map
      inputItemEnchantments.remove(stolenEnchantment);

      // set input item stack to have updated input enchantments without copied enchantment
      ItemStack inputEnchantedBook = new ItemStack(Items.ENCHANTED_BOOK);
      EnchantmentHelper.setEnchantments(inputItemEnchantments, inputEnchantedBook);

      // decrease normal book count as fuel cost
      normalBookStack.shrink(1);

      // update slots
      this.setItem(ENCHANTED_ITEM_SLOT, inputEnchantedBook);
      this.setItem(NORMAL_BOOK_SLOT, normalBookStack);
      this.setItem(OUTPUT_SLOT, outputEnchantmentBook);

      BlockEntity.setChanged(level, pos, blockState);
    }
  }
}
