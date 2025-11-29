package com.cursee.disenchanting_table.platform;

import com.cursee.disenchanting_table.platform.services.IPlatformHelper;
import java.nio.file.Path;
import java.util.function.BiFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTab.Builder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
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

  @Override
  public <T extends BlockEntity> BlockEntityType.Builder<T> of(BiFunction<BlockPos, BlockState, ? extends T> factory, Block... validBlocks) {
    return BlockEntityType.Builder.of(factory::apply, validBlocks);
  }

  @Override
  public <T extends AbstractContainerMenu> MenuType<T> create(BiFunction<Integer, Inventory, ? extends T> factory) {
    return new MenuType<>(factory::apply, FeatureFlags.VANILLA_SET);
  }
}