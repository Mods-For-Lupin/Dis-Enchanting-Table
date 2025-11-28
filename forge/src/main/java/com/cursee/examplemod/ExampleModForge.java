package com.cursee.examplemod;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLLoader;

@Mod(Constants.MOD_ID)
public class ExampleModForge {

  public static IEventBus eventBus;

  public ExampleModForge(final FMLJavaModLoadingContext context) {

    ExampleModForge.eventBus = context.getModEventBus();

    ExampleMod.init();

    if (FMLLoader.getDist() == Dist.CLIENT || FMLEnvironment.dist == Dist.CLIENT) {
      new ExampleModClientForge();
    }
  }

  public ExampleModForge() {
    this(FMLJavaModLoadingContext.get());
  }
}