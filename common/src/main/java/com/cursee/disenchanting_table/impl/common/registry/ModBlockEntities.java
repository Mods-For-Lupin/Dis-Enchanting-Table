package com.cursee.disenchanting_table.impl.common.registry;

import com.cursee.disenchanting_table.Constants;
import com.cursee.disenchanting_table.impl.common.block.DisenchantingTableBlock;
import com.cursee.disenchanting_table.impl.common.block.entity.DisenchantingTableBlockEntity;
import com.cursee.disenchanting_table.platform.Services;
import java.util.function.BiConsumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {

  public static BlockEntityType<DisenchantingTableBlockEntity> DISENCHANTING_TABLE;

  public static void register(BiConsumer<BlockEntityType<?>, ResourceLocation> consumer) {

    // DISENCHANTING_TABLE = BlockEntityType.Builder.of(DisenchantingTableBlockEntity::new, ModBlocks.DISENCHANTING_TABLE).build(null);
    DISENCHANTING_TABLE = Services.PLATFORM.of(DisenchantingTableBlockEntity::new, ModBlocks.DISENCHANTING_TABLE).build(null);

    consumer.accept(DISENCHANTING_TABLE, new ResourceLocation(Constants.MOD_ID, Constants.MOD_ID));
  }

}
