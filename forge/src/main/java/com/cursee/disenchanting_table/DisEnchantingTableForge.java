package com.cursee.disenchanting_table;

import com.cursee.disenchanting_table.impl.common.registry.ModBlocks;
import com.cursee.disenchanting_table.impl.common.registry.ModItems;
import com.cursee.disenchanting_table.impl.common.registry.ModTabs;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.registries.RegisterEvent;

@Mod(Constants.MOD_ID)
public class DisEnchantingTableForge {

  public static IEventBus eventBus;

  public DisEnchantingTableForge(final FMLJavaModLoadingContext context) {

    DisEnchantingTableForge.eventBus = context.getModEventBus();

    bind(Registries.BLOCK, ModBlocks::register);
    bind(Registries.ITEM, ModItems::register);
    bind(Registries.CREATIVE_MODE_TAB, ModTabs::register);

    DisEnchantingTable.init();

    if (FMLLoader.getDist() == Dist.CLIENT || FMLEnvironment.dist == Dist.CLIENT) {
      new DisEnchantingTableClientForge();
    }
  }

  public static <T> void bind(ResourceKey<Registry<T>> registryKey, Consumer<BiConsumer<T, ResourceLocation>> source) {

    eventBus.addListener((Consumer<RegisterEvent>) event -> {
      if (registryKey.equals(event.getRegistryKey())) {
        source.accept((t, rl) -> event.register(registryKey, rl, () -> t));
      }
    });
  }

  public DisEnchantingTableForge() {
    this(FMLJavaModLoadingContext.get());
  }
}