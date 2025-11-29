package com.cursee.disenchanting_table.platform;

import com.cursee.disenchanting_table.platform.services.IPlatformHelper;
import java.nio.file.Path;
import java.util.function.BiFunction;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab.Builder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class FabricPlatformHelper implements IPlatformHelper {

  @Override
  public String getPlatformName() {

    return "Fabric";
  }

  @Override
  public boolean isModLoaded(String modId) {

    return FabricLoader.getInstance().isModLoaded(modId);
  }

  @Override
  public boolean isDevelopmentEnvironment() {

    return FabricLoader.getInstance().isDevelopmentEnvironment();
  }

  @Override
  public Path getGameDirectory() {
    return FabricLoader.getInstance().getGameDir();
  }

  @Override
  public Builder tabBuilder() {
    return FabricItemGroup.builder();
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
