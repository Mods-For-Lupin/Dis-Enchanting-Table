package com.cursee.disenchanting_table;

import com.cursee.disenchanting_table.impl.client.gui.screens.DisenchantingTableScreen;
import com.cursee.disenchanting_table.impl.common.registry.ModMenus;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

public class DisEnchantingTableClientFabric implements ClientModInitializer {

  @Override
  public void onInitializeClient() {

    DisEnchantingTableClient.init();

    MenuScreens.register(ModMenus.DISENCHANTING_TABLE, DisenchantingTableScreen::new);
  }
}
