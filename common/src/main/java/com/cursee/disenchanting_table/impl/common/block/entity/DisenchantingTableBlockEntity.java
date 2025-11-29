package com.cursee.disenchanting_table.impl.common.block.entity;

import com.cursee.disenchanting_table.impl.common.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DisenchantingTableBlockEntity extends BlockEntity {

  public DisenchantingTableBlockEntity(BlockPos pos, BlockState blockState) {
    super(ModBlockEntities.DISENCHANTING_TABLE, pos, blockState);
  }

  public static void clientTick(Level level, BlockPos pos, BlockState blockState, DisenchantingTableBlockEntity table) {
    System.out.println("on the client " + pos);
  }

  public static void serverTick(Level level, BlockPos pos, BlockState blockState, DisenchantingTableBlockEntity table) {
    System.out.println("on the server " + pos);
  }
}
