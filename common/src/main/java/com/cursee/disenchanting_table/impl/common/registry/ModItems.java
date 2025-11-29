package com.cursee.disenchanting_table.impl.common.registry;

import com.cursee.disenchanting_table.Constants;
import java.util.function.BiConsumer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public class ModItems {

  public static Item DISENCHANTING_TABLE;

  public static void register(BiConsumer<Item, ResourceLocation> consumer) {

    DISENCHANTING_TABLE = new BlockItem(ModBlocks.DISENCHANTING_TABLE, new Item.Properties());

    consumer.accept(DISENCHANTING_TABLE, BuiltInRegistries.BLOCK.getKey(ModBlocks.DISENCHANTING_TABLE));
  }
}
