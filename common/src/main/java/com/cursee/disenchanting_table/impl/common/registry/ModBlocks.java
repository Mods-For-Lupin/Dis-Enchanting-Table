package com.cursee.disenchanting_table.impl.common.registry;

import com.cursee.disenchanting_table.Constants;
import com.cursee.disenchanting_table.impl.common.block.DisenchantingTableBlock;
import java.util.function.BiConsumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModBlocks {

  public static Block DISENCHANTING_TABLE;

  public static void register(BiConsumer<Block, ResourceLocation> consumer) {

    DISENCHANTING_TABLE = new DisenchantingTableBlock(BlockBehaviour.Properties.of());

    consumer.accept(DISENCHANTING_TABLE, new ResourceLocation(Constants.MOD_ID, Constants.MOD_ID));
  }
}
