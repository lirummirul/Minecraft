package com.example.examplemod.block;

import com.example.examplemod.entity.TileEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TileBlock extends Block implements EntityBlock {
    public TileBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return Shapes.box(0.125D, 0.125D, 0.125D, 0.875D, 0.875D, 0.875D);
    }

    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack heldItem = player.getItemInHand(hand);
        BlockEntity blockEntity = level.getBlockEntity(pos);

        if (blockEntity instanceof TileEntity tileEntity) {
            ItemStack storedItemStack = tileEntity.getItem();
            BlockPos blockAbovePos = pos.above();
            if (!heldItem.isEmpty() && storedItemStack.isEmpty()) { // Если в руке игрока есть предмет, попробуйте положить его на блок
                if (level.getBlockState(blockAbovePos).isAir()) {
                    tileEntity.setItem(heldItem.copy()); // Положить предмет из руки в блок
//                    if (!player.isCreative()) { // Убрать предмет из руки игрока
                        heldItem.shrink(1); // Уменьшить количество предметов в руке
//                    }
                    return InteractionResult.SUCCESS;
                }
            } else if (heldItem.isEmpty() && !storedItemStack.isEmpty()) { // Если в руке игрока ничего нет, попробуйте взять предмет из блока
                player.setItemInHand(hand, storedItemStack.copy()); // Очистить предмет из блока
                tileEntity.removeItem();
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState state) {
        return new TileEntity(blockPos, state);
    }
}

