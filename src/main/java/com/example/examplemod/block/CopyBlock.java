package com.example.examplemod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CopyBlock extends Block {
    private Map<BlockPos, BlockState> mapCopyState = new HashMap<>();
    private final Map<BlockPos, Entity> mapEntities = new HashMap<>();
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
                    }
                }
            }
            return InteractionResult.SUCCESS;
        } else if (!mapCopyState.isEmpty()) {
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
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
