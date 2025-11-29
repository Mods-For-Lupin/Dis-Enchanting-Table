package com.cursee.disenchanting_table.platform;

import com.cursee.disenchanting_table.platform.services.IPlatformHelper;
import java.nio.file.Path;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTab.Builder;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

public class ForgePlatformHelper implements IPlatformHelper {

  @Override
  public String getPlatformName() {

    return "Forge";
  }

  @Override
  public boolean isModLoaded(String modId) {

    return ModList.get().isLoaded(modId);
  }

  @Override
  public boolean isDevelopmentEnvironment() {

    return !FMLLoader.isProduction();
  }

  @Override
  public Path getGameDirectory() {
    return FMLLoader.getGamePath();
  }

  @Override
  public Builder tabBuilder() {
    return CreativeModeTab.builder();
  }
}