package com.example.examplemod.event;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.block.MyBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

public class ModEvents {
    @Mod.EventBusSubscriber(modid = ExampleMod.MODID)
    public static class ForgeEvents {
        private static int tickCounter = 0;

        @SubscribeEvent
        public static void onPlayerEnterBlock(PlayerEvent event) {
            Player player = event.getEntity();
            if (player != null) {
                // Получаем вектор направления взгляда игрока
                Vec3 lookVector = player.getLookAngle();
                // Получаем текущий мир, в котором находится игрок
                Level world = player.level;
                // Получаем позицию игрока в мире
                BlockPos playerPos = player.blockPosition();
                // Устанавливаем расстояние, на котором должен происходить "поиск" блока перед игроком
                double reachDistance = 2.0;
                // Вычисляем позицию, на которой должен быть установлен блок
                BlockPos placePos = new BlockPos(playerPos.getX() + lookVector.x * reachDistance,
                        playerPos.getY() + lookVector.y * reachDistance,
                        playerPos.getZ() + lookVector.z * reachDistance);
                // Получаем состояние блока, который мы хотим установить
                BlockState blockState = MyBlock.MY_BLOCK.get().defaultBlockState();
                // Устанавливаем блок в мире на вычисленной позиции
                world.setBlock(placePos, blockState, 3);
                tickCounter++;
            }
        }

        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            if(event.side == LogicalSide.SERVER) {
                if (tickCounter >= 100) {
                    if (event.player.isOnGround()) { // Проверка, что игрок находится на земле
                        BlockPos playerPos = event.player.blockPosition();
                        BlockState blockState = event.player.level.getBlockState(playerPos);
                        if (blockState.getBlock() == MyBlock.MY_BLOCK.get()) { // Проверка, что блок - ваш блок
                            // Ваш блок
                            // Удаляем блок, если игрок покинул его
                            event.player.level.setBlock(playerPos, Blocks.AIR.defaultBlockState(), 3);
//                                    world.setBlock(playerPos, Blocks.AIR.defaultBlockState(), 3);
                        }
                    }
                }
                tickCounter = 0;
            }
        }



//        // Метод для отслеживания тиков
//        @SubscribeEvent
//        public void onServerTick(TickEvent event) {
//            if (event.side == LogicalSide.CLIENT || event.phase != TickEvent.Phase.END) {
//                return;
//            }
//
//            tickCounter++;
//            if (tickCounter >= 20) {
//                // Удаляем вашу конструкцию или блоки через 20 тиков
//                // Например, устанавливаем воздух на позиции блока
//                Level world = event.world;
//                // Пример удаления конструкции на позиции pos
//                BlockPos pos = /* позиция вашей конструкции */;
//                world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
//                // Сбрасываем счетчик тиков
//                tickCounter = 0;
//            }
//        }

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
