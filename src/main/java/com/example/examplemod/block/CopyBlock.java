package com.example.examplemod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;

import javax.print.attribute.standard.Copies;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CopyBlock extends Block {
    private Map<BlockPos, BlockState> mapCopyState = new HashMap<>();
    private final Map<BlockPos, Entity> mapEntities = new HashMap<>();
    private Map<BlockPos, CompoundTag> mapTileEntities = new HashMap<>();
    private  Map<BlockPos, BlockEntity> mapBlockEntity = new HashMap<>();
    private Map<BlockPos, ItemStack> mapItemBlockEntities = new HashMap<>();
    private List<ItemStack> itemStack2 = new ArrayList<>();
    ItemStack itemStack;
//    private Map<BlockPos, BlockEntity> mapBlockEntities = new HashMap<>();
    public CopyBlock(Properties properties) {
        super(properties);
    }

    // use - ПКM
    // attack - ЛКМ

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        int radius = 5;

        if (player.isShiftKeyDown()) {
            mapCopyState.clear();
            mapEntities.clear();
            mapTileEntities.clear();
            mapBlockEntity.clear();

            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        BlockPos targetPos = pos.offset(x, y, z);
                        BlockState targetBlockState = level.getBlockState(targetPos);
                        BlockPos offset = new BlockPos(x, y, z);
                        mapCopyState.put(offset, targetBlockState);
                        List<Entity> entities = level.getEntities(null, new AABB(targetPos));
                        if (!entities.isEmpty()) {
                            mapEntities.put(offset, entities.get(0));
                        }
//                        System.out.println(level.getBlockState(targetPos).getBlock());
                        BlockEntity blockEntity = level.getBlockEntity(targetPos);
//                        System.out.println("blockEntity : " + blockEntity);
                        if (blockEntity != null) {
                            CompoundTag tag = blockEntity.saveWithFullMetadata();
//                            itemStack2.add(blockEntity.saveToItem(itemStack));
                            blockEntity.saveToItem(itemStack);

                            mapTileEntities.put(offset, tag);
                            mapBlockEntity.put(offset, blockEntity);

//                            Inventory inventory = (Inventory) blockEntity;
//                            for (int i = 0; i < inventory.getContainerSize(); i++) {
//                                ItemStack stack = inventory.getItem(i);
//                                itemStacks.add(stack);
//                            }
//                            System.out.println("tag : " + tag);
//                            System.out.println("pos : " + pos);
                        }
                    }
                }

            }
            return InteractionResult.SUCCESS;
        } else if (!mapCopyState.isEmpty()) {
//            System.out.println(mapTileEntities);
            for (Map.Entry<BlockPos, BlockState> entry : mapCopyState.entrySet()) {
                BlockPos offset = entry.getKey();
                BlockPos copyTargetPos = pos.offset(offset);
                BlockState copyTargetState = entry.getValue();
                level.setBlock(copyTargetPos, copyTargetState, 3);

                Entity copiedEntity = mapEntities.get(offset);
                if (copiedEntity != null) {
                    Entity newEntity = copiedEntity.getType().create(level);
                    if (newEntity != null) {
                        newEntity.moveTo(copyTargetPos.getX(), copyTargetPos.getY(), copyTargetPos.getZ());
                        level.addFreshEntity(newEntity);
                    }
                }

                if (!mapTileEntities.isEmpty()) {
                    CompoundTag tag = mapTileEntities.get(offset);
                    if (tag != null) {
                        BlockEntity blockEntity = mapBlockEntity.get(offset);
//                        blockEntity.loadStatic(offset, copyTargetState, tag);
                        System.out.println("blockEntity : " + blockEntity);
                        System.out.println("tag : " + tag);

//                        ((Copier)blockEntity).copyData(tag);
                        blockEntity.load(tag);
                        blockEntity.setChanged();
//                        Inventory inventory = (Inventory) blockEntity;
//                        for (int i = 0; i < itemStacks.size(); i++) {
//                            inventory.setItem(i, itemStacks.get(i));
//                        }
                    }
                }
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
