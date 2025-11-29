package com.cursee.disenchanting_table;

import com.cursee.disenchanting_table.impl.client.gui.screens.DisenchantingTableScreen;
import com.cursee.disenchanting_table.impl.common.registry.ModMenus;
import java.util.function.Consumer;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class DisEnchantingTableClientForge {

  public DisEnchantingTableClientForge() {

    DisEnchantingTableClient.init();

    DisEnchantingTableForge.eventBus.addListener((Consumer<FMLClientSetupEvent>) event -> {
      event.enqueueWork(() -> {
        MenuScreens.register(ModMenus.DISENCHANTING_TABLE, DisenchantingTableScreen::new);
      });
    });
  }
}
