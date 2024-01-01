package com.example.examplemod.event;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.entity.TileEntity;
import com.example.examplemod.entity.client.TigerRenderer;
import com.example.examplemod.entity.custom.TigerEntity;
import com.example.examplemod.init.ModEntities;
import com.example.examplemod.init.MyBlock;
import com.example.examplemod.init.MyBlockEntities;
import com.example.examplemod.render.TileRender;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

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

    @Mod.EventBusSubscriber(modid = ExampleMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {
        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
            event.put(ModEntities.TIGER.get(), TigerEntity.setAttributes());
        }
    }

    @Mod.EventBusSubscriber(modid = ExampleMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(MyBlockEntities.MY_TILE_ENTITY.get(), TileRender::new);
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.TIGER.get(), TigerRenderer::new);
        }
    }

    @SubscribeEvent
    public static void onInteract(PlayerInteractEvent.EntityInteract event) {
        if (event.getTarget() instanceof TigerEntity) {
            // Проверяем, что игрок действительно нажал правой кнопкой мыши (HAND_MAIN)
            if (event.getHand() == InteractionHand.MAIN_HAND) {
                // Получаем моба, на которого нажал игрок
                TigerEntity tigerEntity = (TigerEntity) event.getTarget();

                // Здесь вы можете добавить код для обработки действия при нажатии на моба
                // Например, вывод какого-то сообщения или выполнение определенного действия

                // Пример вывода сообщения в чат
                if (!event.getLevel().isClientSide) {
                    event.getEntity().sendSystemMessage(tigerEntity.getGreetingMessage);
                }
            }
        }
    }

}
