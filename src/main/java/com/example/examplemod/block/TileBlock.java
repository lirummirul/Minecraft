package com.example.examplemod.block;

import com.example.examplemod.entity.TileEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
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

public class TileBlock extends Block implements EntityBlock {
    public TileBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        ItemStack heldItem = player.getItemInHand(hand);
        BlockEntity blockEntity = level.getBlockEntity(pos);

        if (blockEntity instanceof TileEntity tileEntity) {
            ItemStack storedItemStack = tileEntity.getItem();
            BlockPos blockAbovePos = pos.above();
            // Если в руке игрока есть предмет, попробуйте положить его на блок
            if (!heldItem.isEmpty()) {
                System.out.println("heldItem : " + heldItem);
                // Проверяем, что в блоке ничего не хранится
//                if (storedItemStack.isEmpty()) {
                if (storedItemStack.isEmpty() || ItemStack.isSame(storedItemStack, heldItem)) {
                    System.out.println("storedItemStack : " + storedItemStack);
                    // Положить предмет из руки в блок
                    tileEntity.setItem(heldItem.copy());
                    // Убрать предмет из руки игрока
//                    if (!player.isCreative()) {
                        heldItem.shrink(1); // Уменьшить количество предметов в руке
//                    }
                    return InteractionResult.SUCCESS;
                }
            }
            // Если в руке игрока ничего нет, попробуйте взять предмет из блока
            else if (heldItem.isEmpty() && !storedItemStack.isEmpty()) {
                System.out.println("Внутри else");
                // Проверяем, что в блоке есть предмет
                if (level.isClientSide()) {
                    System.out.println("Внутри else => внутри if ");
                    // Выполнить действия на клиентской стороне (если необходимо)
                    return InteractionResult.SUCCESS;
                } else {
                    System.out.println("Внутри else => внутри else ");
                    // Очистить предмет из блока
                    player.setItemInHand(hand, storedItemStack.copy());
                    tileEntity.removeItem();
                    return InteractionResult.SUCCESS;
                }
            }
        }

        return InteractionResult.PASS;
    }



//    public InteractionResult use(BlockState state, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult result) {
////            ItemStack itemstack = player.getItemInHand(interactionHand);
////            return !itemstack.isEmpty() ? InteractionResult.CONSUME : InteractionResult.PASS;
//        // Получаем предмет из руки игрока
//        ItemStack itemInHand = player.getItemInHand(InteractionHand.MAIN_HAND);
//
//        // Проверяем, что предмет из руки не пустой и является тем, который нужно положить на ваш предмет
//        if (!itemInHand.isEmpty()) {
//            // Получаем предмет на который нажали правой кнопкой мыши
//            ItemStack yourItemStack = player.getItemInHand(interactionHand);
//
//            // Положить предмет из руки на ваш предмет
//            if (yourItemStack.getCount() < yourItemStack.getMaxStackSize()) {
//                yourItemStack.grow(1); // Увеличиваем стопку предметов на вашем предмете на 1
//                if (!player.isCreative()) {
//                    itemInHand.shrink(1); // Уменьшаем количество предметов в руке игрока на 1
//                }
//                return InteractionResult.SUCCESS; // Взаимодействие успешно выполнено
//            }
//        }
//
//        return InteractionResult.PASS; // Взаимодействие не выполнено
//    }

    // Метод для обработки события клика правой кнопкой мыши на блоке
//    public InteractionResult use(BlockState state, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult result) {
//        // Получаем предмет в руке игрока
//        ItemStack itemStack = player.getItemInHand(interactionHand);
////        BlockPos bp = new BlockPos(blockPos.getX() + 1, blockPos.getY(), blockPos.getZ());
////        BlockPos bp2 = new BlockPos(blockPos.getX() + 2, blockPos.getY(), blockPos.getZ());
//        BlockEntity blockEntity = level.getBlockEntity(blockPos);
////        BlockEntity blockEntity2 = level.getBlockEntity(bp);
////        BlockEntity blockEntity3 = level.getBlockEntity(bp2);
//
//        System.out.println("blockEntity : " + blockEntity);
////        System.out.println("blockEntity : " + blockEntity2);
////        System.out.println("blockEntity : " + blockEntity3);
//        System.out.println("blockPos : " + blockPos);
//
//        // Проверяем, что предмет в руке не пустой
//        if (!itemStack.isEmpty()) {
//            // Проверяем, что позиция блока не пустая
//            if (level.getBlockState(blockPos).isSolidRender(level, blockPos)) {
//                // Получаем позицию блока, на который хотим положить предмет (например, блок над нажатым блоком)
//                BlockPos blockAbovePos = blockPos.above();
//                // Проверяем, что блок выше существует и не занят
//                if (level.getBlockState(blockAbovePos).isAir()) {
//                    // Получаем блок из предмета в руке
//                    Block block = Block.byItem(itemStack.getItem());
//                    BlockState blockState = block.defaultBlockState(); // Получаем состояние блока из предмета
//
////                    System.out.println("blockState : " + blockState);
//                    // Кладем предмет из руки на блок выше текущего
////                    if (!level.isClientSide) {
//                        level.setBlockAndUpdate(blockAbovePos, blockState); // Например, заменяем блок на доски из дуба
////                        level.setBlockAndUpdate(blockAbovePos, Blocks.OAK_PLANKS.defaultBlockState()); // Например, заменяем блок на доски из дуба
//                        return InteractionResult.SUCCESS; // Взаимодействие успешно выполнено
////                    }
//                }
//            }
//        } else {
//            System.out.println("Я внутри else ");
//            putInPlayersHandAndRemove(state,level,blockPos,player,interactionHand, itemStack);
//            return InteractionResult.SUCCESS;
//        }
//
//        return InteractionResult.PASS; // Взаимодействие не выполнено
//    }


//    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
//        if (level.isClientSide) {
//            ItemStack itemstack = player.getItemInHand(interactionHand);
//            return itemstack.is(Items.LEAD) ? InteractionResult.SUCCESS : InteractionResult.PASS;
//        } else {
//            return LeadItem.bindPlayerMobs(player, level, blockPos);
//        }
//    }

    public void putInPlayersHandAndRemove(TileEntity tileEntity, Level world, Player player, ItemStack itemStack) {
//        System.out.println("pos : " + pos);

        if (itemStack.isEmpty()) {
            // Помещаем предмет в инвентарь игрока или выкидываем его на землю
            if (!player.addItem(itemStack)) {
                world.addFreshEntity(new ItemEntity(world, player.getX(), player.getY(), player.getZ(), itemStack));
            }
            tileEntity.removeItem();
            // Убираем предмет из руки игрока
//            world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3); // Заменяем блок на пустоту (воздух)
        }
//        BlockEntity blockEntity = world.getBlockEntity(pos);
//        if (blockEntity instanceof TileEntity) {
//            TileEntity tileEntity = new TileEntity(pos, state);
//            System.out.println("tileEntity : " + tileEntity);
////            System.out.println("А теперь я внутри метода добавления в руку))))");
//            ItemStack itemStack = tileEntity.getItem(); // Получение предмета из блока
//
//            if (!itemStack.isEmpty()) {
//                // Помещаем предмет в руку игрока
//                if (!player.addItem(itemStack)) {
//                    // Если предмет не поместился в инвентарь, выкидываем его на землю
//                    world.addFreshEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), itemStack));
//                }
//
//                // Убираем предмет из блока
//                tileEntity.removeItem();
//                world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3); // Заменяем блок на пустоту (воздух)
//            }
//        }
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState state) {
        return new TileEntity(blockPos, state);
    }


//
//    //Right Click Action
//    @Override
//    public InteractionResult use(BlockState p_60503_, Level p_60504_, BlockPos p_60505_, Player p_60506_, InteractionHand p_60507_, BlockHitResult p_60508_) {
//
//        if(p_60504_.isClientSide()) {
//            ItemStack itemInHand = p_60506_.getMainHandItem();
//        } else {
//            BlockEntity blockEntity = p_60504_.getBlockEntity(p_60505_);
//            if(blockEntity instanceof TileEntity pedestal) {
//                ItemStack itemInHand = p_60506_.getMainHandItem();
//                ItemStack itemInOffHand = p_60506_.getOffhandItem();
//
//                int getColor;
//                int currentColor;
//                BlockState newState;
//                List<Item> DYES = ForgeRegistries.ITEMS.tags().getTag(ItemTags.create(new ResourceLocation("forge", "dyes"))).stream().toList();
//
//                if(itemInHand.getItem() instanceof IPedestalTool) {
//
//                    if(itemInHand.getItem().equals(DeferredRegisterItems.TOOL_LINKINGTOOL.get()) || itemInHand.getItem().equals(DeferredRegisterItems.TOOL_LINKINGTOOLBACKWARDS.get())){
//                        boolean getCurrentRender = pedestal.getRenderRange();
//                        pedestal.setRenderRange(!getCurrentRender);
//
//                        MutableComponent render_on = Component.translatable(ExampleMod.MODID + ".linkingtool.pedestal_render_on");
//                        MutableComponent render_off = Component.translatable(ExampleMod.MODID + ".linkingtool.pedestal_render_off");
//                        MutableComponent render = (!getCurrentRender)?(render_on):(render_off);
//                        ChatFormatting color = (!getCurrentRender)?(ChatFormatting.RED):(ChatFormatting.DARK_RED);
//                        render.withStyle(color);
//
//                        p_60506_.displayClientMessage(render, true);
//                    }
//                    if(itemInHand.getItem().equals(DeferredRegisterItems.TOOL_UPGRADETOOL.get())){
//                        if(pedestal.hasCoin()) {
//                            if(pedestal.getCoinOnPedestal().getItem() instanceof ItemUpgradeBase upgrade) {
//                                int value = upgrade.getUpgradeWorkRange(pedestal.getCoinOnPedestal());
//                                if(value>0) {
//                                    boolean getCurrentRenderUpgrade = pedestal.getRenderRangeUpgrade();
//                                    pedestal.setRenderRangeUpgrade(!getCurrentRenderUpgrade);
//
//                                    MutableComponent render_on = Component.translatable(MODID + ".upgradetool.pedestal_render_on");
//                                    MutableComponent render_off = Component.translatable(MODID + ".upgradetool.pedestal_render_off");
//                                    MutableComponent render = (!getCurrentRenderUpgrade)?(render_on):(render_off);
//                                    ChatFormatting color = (!getCurrentRenderUpgrade)?(ChatFormatting.BLUE):(ChatFormatting.DARK_BLUE);
//                                    render.withStyle(color);
//
//                                    p_60506_.displayClientMessage(render, true);
//                                } else {
//                                    MutableComponent render_none = Component.translatable(MODID + ".upgradetool.pedestal_render_none");
//                                    render_none.withStyle(ChatFormatting.WHITE);
//                                    p_60506_.displayClientMessage(render_none, true);
//                                }
//                            }
//                        }
//                    } else if(itemInHand.getItem().equals(DeferredRegisterItems.TOOL_MANIFEST.get())){
//
//                        MutableComponent manifest = Component.translatable(MODID + ".manifest.color");
//                        MutableComponent color = Component.translatable(MowLibReferences.MODID + "." +MowLibColorReference.getColorName(MowLibColorReference.getColorFromStateInt(p_60503_)));
//                        manifest.withStyle(ChatFormatting.GOLD);
//                        color.withStyle(ChatFormatting.WHITE);
//                        manifest.append(color);
//
//                        p_60506_.displayClientMessage(manifest, true);
//                    }
//                    return InteractionResult.FAIL;
//                } else {
//                    if(pedestal.hasFluid() && itemInHand.getItem().equals(Items.BUCKET)) {
//                        if(p_60506_ instanceof FakePlayer){ return InteractionResult.FAIL; }
//
//                        Item item = pedestal.getStoredFluid().copy().getFluid().getBucket();
//                        if(item instanceof BucketItem bucketItem) {
//                            String fluid = pedestal.getStoredFluid().getDisplayName().getString();
//                            if(!pedestal.removeFluid(1000, IFluidHandler.FluidAction.EXECUTE).isEmpty()) {
//                                if(!p_60506_.isCreative())itemInHand.shrink(1);
//                                if(!p_60506_.isCreative())ItemHandlerHelper.giveItemToPlayer(p_60506_,new ItemStack(bucketItem));
//
//                                String fluidRemoved = fluid +": " +pedestal.getStoredFluid().getAmount() +"/"+pedestal.getFluidCapacity();
//                                MutableComponent pedestalFluid = Component.translatable(fluidRemoved);
//                                pedestalFluid.withStyle(ChatFormatting.WHITE);
//                                p_60506_.displayClientMessage(pedestalFluid,true);
//
//                                return InteractionResult.SUCCESS;
//                            }
//                        }
//                    }
//
//                    if(!itemInHand.isEmpty()) {
//                        ItemStack stackNotInsert = pedestal.addItemStack(itemInHand,true);
//                        if(itemInHand.getCount() > stackNotInsert.getCount()) {
//                            int shrinkAmount = itemInHand.getCount() - pedestal.addItemStack(itemInHand,false).getCount();
//                            itemInHand.shrink(shrinkAmount);
//                            return InteractionResult.SUCCESS;
//                        } else return InteractionResult.FAIL;
//                    } else return InteractionResult.FAIL;
//                }
//            }
//        }
//
//        return InteractionResult.SUCCESS;
//    }
}

