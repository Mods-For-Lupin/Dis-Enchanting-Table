package com.cursee.disenchanting_table.impl.common.block.entity;

import com.cursee.disenchanting_table.impl.common.block.entity.abstr.AbstractContainerBE;
import com.cursee.disenchanting_table.impl.common.registry.ModBlockEntities;
import com.cursee.disenchanting_table.impl.common.registry.ModMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class DisenchantingTableBlockEntity extends AbstractContainerBE implements MenuProvider {

  public DisenchantingTableBlockEntity(BlockPos pos, BlockState blockState) {
    super(ModBlockEntities.DISENCHANTING_TABLE, pos, blockState);
  }

  public static void clientTick(Level level, BlockPos pos, BlockState blockState, DisenchantingTableBlockEntity table) {
    // System.out.println("on the client " + pos);
  }

  public static void serverTick(Level level, BlockPos pos, BlockState blockState, DisenchantingTableBlockEntity table) {
    // System.out.println("on the server " + pos);
    table.doServerTick(level, pos, blockState);
  }

  @Override
  public Component getDisplayName() {
    return Component.translatable("itemGroup.disenchantingTable");
  }

  @Override
  public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
    return ModMenus.DISENCHANTING_TABLE.create(i, inventory);
  }
}
