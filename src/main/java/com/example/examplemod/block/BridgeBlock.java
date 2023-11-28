package com.example.examplemod.block;

import com.example.examplemod.event.MyEventHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "examplemod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BridgeBlock extends Block {

    public BridgeBlock(Properties properties) {
        super(properties);
    }

    public void stepOn(Level world, BlockPos pos, Entity entity) {
        if (entity instanceof Player player) {
            MinecraftForge.EVENT_BUS.post(new MyEventHandler(player, pos));
        }
    }

//    public InteractionResult onItemUse(UseOnContext context) {
//        Player player = context.getPlayer();
//        if (player != null) {
//            Vec3 lookVector = player.getLookAngle();
//            Level world = player.level;
//            BlockPos playerPos = player.blockPosition();
//            double reachDistance = 5.0; // Расстояние до размещения блока (может быть изменено)
//            BlockPos placePos = new BlockPos(playerPos.getX() + lookVector.x * reachDistance,
//                    playerPos.getY() + lookVector.y * reachDistance,
//                    playerPos.getZ() + lookVector.z * reachDistance);
//                // Размещение блока на позиции placePos
//            BlockState blockState = MyBlock.MY_BLOCK.get().defaultBlockState();// Здесь нужно использовать ваш блок и состояние блока
//            world.setBlock(placePos, blockState, 3);// 3 - флаги для обновления блока
////             yourBlock.placeBlock(context.getLevel(), placePos, context.getPlayer());
//
//            return InteractionResult.SUCCESS;
//        }
//        return InteractionResult.PASS;
//    }


//    @SubscribeEvent
//    public static void onBlockPlace(PlayerInteractEvent.RightClickBlock event) {
//        if (event.getEntity() instanceof Player) {
//            Player player = (Player) event.getEntity();
//            BlockState blockState = event.getPlacedBlock();
//
//            // Проверка, что размещаемый блок - ваш блок
//            if (blockState.getBlock() instanceof BridgeBlock) {
//                BridgeBlock bridgeBlock = (BridgeBlock) blockState.getBlock();
//                // Вызов вашего метода onItemUse
//                bridgeBlock.onItemUse(new UseOnContext(player, InteractionHand.MAIN_HAND, event));
//            }
//        }

//        Player player = (Player) event.getEntity();
//        Level world = player.getLevel();
//        BlockPos clickedPos = event.getPos();
//
//        Vec3 lookVector = player.getLookAngle();
//        BlockPos playerPos = player.blockPosition();
//        double reachDistance = 5.0; // Расстояние до размещения блока (может быть изменено)
//        BlockPos placePos = new BlockPos(playerPos.getX() + lookVector.x * reachDistance,
//                playerPos.getY() + lookVector.y * reachDistance,
//                playerPos.getZ() + lookVector.z * reachDistance);
//
//        if (placePos.equals(clickedPos)) {
//            BlockHitResult hitResult = new BlockHitResult(player.getEyePosition(), player.getDirection(), clickedPos, false);
//            InteractionResult result = ((BridgeBlock) world.getBlockState(clickedPos).getBlock()).onItemUse(new UseOnContext(player, event.getHand(), hitResult));
//
//            if (result.consumesAction()) {
//                event.setCanceled(true);
//                event.setCancellationResult(result);
//            }
//        }
//    }



//    public static void placeBlockInDirection(UseOnContext context) {
//        if (context.getLevel().isClientSide()) {
//            return; // Игнорировать клиентский код
//        }
//
//        Vec3 lookVector = context.getClickLocation();
//        BlockPos blockPos = new BlockPos(lookVector.x, lookVector.y, lookVector.z);
////        BlockHitResult hitResult = context.getHitResult();
////        BlockPos blockPos = hitResult.getBlockPos().relative(hitResult.getDirection());
//
//        // Разместить ваш блок в направлении, в котором смотрит игрок
//        BlockState state = Blocks.BONE_BLOCK.defaultBlockState() /* создать BlockState вашего блока */;
//        context.getLevel().setBlock(blockPos, state, 3); // 3 для обновления блока
//    }
}
