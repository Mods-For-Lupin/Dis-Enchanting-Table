package com.cursee.disenchanting_table.impl.common.registry;

import com.cursee.disenchanting_table.Constants;
import com.cursee.disenchanting_table.impl.common.inventory.DisenchantingTableMenu;
import com.cursee.disenchanting_table.platform.Services;
import java.util.function.BiConsumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;

public class ModMenus {

  public static MenuType<DisenchantingTableMenu> DISENCHANTING_TABLE;

  public static void register(BiConsumer<MenuType<?>, ResourceLocation> consumer) {

    DISENCHANTING_TABLE = Services.PLATFORM.create(DisenchantingTableMenu::new);

    consumer.accept(DISENCHANTING_TABLE, new ResourceLocation(Constants.MOD_ID, Constants.MOD_ID));
  }
}
