package com.example.examplemod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = "examplemod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BridgeBlock extends Block {
    public static List<BlockPos> createdBlockPositions = new ArrayList<>();
    private BlockPos currentBlockPos = null;

    public BridgeBlock(Properties properties) {
        super(properties);
    }

    public void stepOn(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state, Entity entity) {
        Vec3 lookVector = entity.getLookAngle().normalize();
        Vec3 playerPos = entity.position();

        double reachDistance = 1.0;

        if (createdBlockPositions.isEmpty()) {
            currentBlockPos = new BlockPos(playerPos.add(lookVector.x() * reachDistance,
                    lookVector.y() * reachDistance,
                    lookVector.z() * reachDistance));
//            System.out.println("currentBlockPos : " + currentBlockPos);
        } else {
            BlockPos lastBlockPos = createdBlockPositions.get(createdBlockPositions.size() - 1);
            Vec3 ls = new Vec3(lastBlockPos.getX(), lastBlockPos.getY(), lastBlockPos.getZ());
            Vec3 nextBlockPos = ls.add(lookVector.x() * reachDistance,
                    lookVector.y() * reachDistance,
                    lookVector.z() * reachDistance);
            currentBlockPos = new BlockPos(Math.round(nextBlockPos.x), Math.round(nextBlockPos.y), Math.round(nextBlockPos.z));
//            System.out.println("currentBlockPos in else : " + currentBlockPos);
        }
//        System.out.println("createdBlockPositions : " + createdBlockPositions);
        BlockState myBlockState = Blocks.WARPED_PLANKS.defaultBlockState();
        BlockState blockState = world.getBlockState(currentBlockPos);
        if (blockState.equals(Blocks.AIR.defaultBlockState())) {
            world.setBlock(currentBlockPos, myBlockState, 3);
            createdBlockPositions.add(currentBlockPos);
        }
        super.stepOn(world, pos, state, entity);
    }
}