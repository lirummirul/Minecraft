package com.example.examplemod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = "examplemod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BridgeBlock extends Block {
    public static List<BlockPos> createdBlockPositions = new ArrayList<>();
    public BridgeBlock(Properties properties) {
        super(properties);
    }

    public void stepOn(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state, Entity entity) {
        Vec3 lookVector = entity.getLookAngle().normalize();
        Vec3 playerPos = entity.position();
        double reachDistance = 16.0;
        Vec3 endOfLook = playerPos.add(lookVector.x() * reachDistance, lookVector.y() * reachDistance, lookVector.z() * reachDistance);
        BlockHitResult result = world.clip(new ClipContext(entity.getEyePosition(1.0F), endOfLook, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));
        if (result.getType() == HitResult.Type.BLOCK) {
            BlockPos placePos = result.getBlockPos().relative(result.getDirection());
            double distanceToPlayer = placePos.distToCenterSqr(playerPos);
            if (distanceToPlayer > 1) {
                BlockState myBlockState = Blocks.WARPED_PLANKS.defaultBlockState();
                world.setBlock(placePos, myBlockState, 3);
                createdBlockPositions.add(placePos);
            }
        }
        super.stepOn(world, pos, state, entity);
    }


}