package com.example.examplemod.event;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.init.MyBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import static com.example.examplemod.block.BridgeBlock.createdBlockPositions;

public class ModEvents {
    @Mod.EventBusSubscriber(modid = ExampleMod.MODID)
    public static class ForgeEvents {

        private static int tickCounter = 0;

        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            if (event.side == LogicalSide.CLIENT) {
                BlockPos playerPos = event.player.blockPosition();
                BlockState blockState = event.player.level.getBlockState(playerPos);
                if (blockState.is(MyBlock.MY_BLOCK.get())) {
                    tickCounter = 0;
                } else {
                    tickCounter++;
                    if (tickCounter >= 100) {
                        int radius = 1;
                        boolean flag = false;
                        for (int x = -radius; x <= radius; x++) {
                            for (int y = -radius; y <= radius; y++) {
                                for (int z = -radius; z <= radius; z++) {
                                    BlockPos checkPos = playerPos.offset(x, y, z);
                                    BlockState checkBlockState = event.player.level.getBlockState(checkPos);
                                    if (checkBlockState.is(MyBlock.MY_BLOCK.get())) {
                                        flag = true;
                                    }
                                }
                            }
                        }
                        if (!flag) removeBlocks(event.player.level);
                        tickCounter = 0;
                    }
                }
            }
        }

        private static void removeBlocks(Level world) {
            for (BlockPos pos :  createdBlockPositions) {
                BlockState blockState = world.getBlockState(pos);
                if (blockState.equals(Blocks.WARPED_PLANKS.defaultBlockState())) {
                    world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                }
            }
            createdBlockPositions.clear();
        }

        @SubscribeEvent
        public static void onLivingHurt(LivingHurtEvent event) {
            if(event.getEntity() instanceof Sheep) {
                if(event.getSource().getEntity() instanceof Player player) {
                    if(player.getMainHandItem().getItem() == Items.BEEF) {
                        player.sendSystemMessage(Component.literal(player.getName().getString() + " hurt a Sheep with BEEF! But why?"));
                    } else {
                        player.sendSystemMessage(Component.literal(player.getName().getString() + " hurt a Sheep!"));
                    }
                }
            }
        }

    }

}
