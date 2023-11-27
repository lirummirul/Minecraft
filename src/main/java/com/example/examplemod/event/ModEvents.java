package com.example.examplemod.event;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.block.MyBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ModEvents {
    @Mod.EventBusSubscriber(modid = ExampleMod.MODID)
    public static class ForgeEvents {

        @SubscribeEvent
        public static void onPlayerEnterBlock(PlayerEvent event) {
            Player player = event.getEntity();
            if (player != null) {
                Vec3 lookVector = player.getLookAngle();
                Level world = player.level;
                BlockPos playerPos = player.blockPosition();
                double reachDistance = 5.0;
                BlockPos placePos = new BlockPos(playerPos.getX() + lookVector.x * reachDistance,
                        playerPos.getY() + lookVector.y * reachDistance,
                        playerPos.getZ() + lookVector.z * reachDistance);
                BlockState blockState = MyBlock.MY_BLOCK.get().defaultBlockState();
                world.setBlock(placePos, blockState, 3);
            }
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
