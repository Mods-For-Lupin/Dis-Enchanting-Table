package com.cursee.disenchanting_table.impl.common.block;

import com.cursee.disenchanting_table.impl.common.block.entity.DisenchantingTableBlockEntity;
import com.cursee.disenchanting_table.impl.common.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.EnchantmentTableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class DisenchantingTableBlock extends Block implements EntityBlock {

  public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 12, 16);

  public DisenchantingTableBlock(Properties properties) {
    super(properties);
  }

  @Override
  public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
    return SHAPE;
  }

  @Override
  public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
    return new DisenchantingTableBlockEntity(blockPos, blockState);
  }

  @Nullable
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
    return level.isClientSide ? createTickerHelper(blockEntityType, ModBlockEntities.DISENCHANTING_TABLE, DisenchantingTableBlockEntity::clientTick) : createTickerHelper(blockEntityType, ModBlockEntities.DISENCHANTING_TABLE, DisenchantingTableBlockEntity::serverTick);
  }

  @SuppressWarnings("unchecked") @Nullable
  protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> serverType, BlockEntityType<E> clientType, BlockEntityTicker<? super E> ticker) {
    return clientType == serverType ? (BlockEntityTicker<A>) ticker : null;
  }

  @Override
  public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

    if (level instanceof ServerLevel serverLevel && serverLevel.getBlockEntity(pos) instanceof MenuProvider menuProvider) {
      player.openMenu(menuProvider);
      return InteractionResult.SUCCESS;
    }

    return super.use(state, level, pos, player, hand, hit);
  }
}
