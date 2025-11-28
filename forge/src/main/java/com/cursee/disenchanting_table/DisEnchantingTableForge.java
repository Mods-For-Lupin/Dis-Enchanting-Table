package com.cursee.disenchanting_table;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLLoader;

@Mod(Constants.MOD_ID)
public class DisEnchantingTableForge {

  public static IEventBus eventBus;

  public DisEnchantingTableForge(final FMLJavaModLoadingContext context) {

    DisEnchantingTableForge.eventBus = context.getModEventBus();

    DisEnchantingTable.init();

    if (FMLLoader.getDist() == Dist.CLIENT || FMLEnvironment.dist == Dist.CLIENT) {
      new DisEnchantingTableClientForge();
    }
  }

  public DisEnchantingTableForge() {
    this(FMLJavaModLoadingContext.get());
  }
}