package com.cursee.disenchanting_table.impl.common.registry;

import com.cursee.disenchanting_table.Constants;
import com.cursee.disenchanting_table.platform.Services;
import java.util.function.BiConsumer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ModTabs {

  public static CreativeModeTab DISENCHANTING_TABLE;

  public static void register(BiConsumer<CreativeModeTab, ResourceLocation> consumer) {

    DISENCHANTING_TABLE = Services.PLATFORM.tabBuilder()
        .icon(() -> new ItemStack(ModBlocks.DISENCHANTING_TABLE))
        .title(Component.translatable("itemGroup.disenchantingTable"))
        .displayItems(((itemDisplayParameters, output) -> {
          output.accept(ModBlocks.DISENCHANTING_TABLE);
        }))
        .build();

    consumer.accept(DISENCHANTING_TABLE, new ResourceLocation(Constants.MOD_ID, Constants.MOD_ID));
  }
}
