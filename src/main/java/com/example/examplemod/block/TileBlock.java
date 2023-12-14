package com.example.examplemod.block;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.entity.TileEntity;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraftforge.fml.common.Mod;
import com.google.common.collect.Maps;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

//@Mod.EventBusSubscriber(modid = "examplemod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TileBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final BooleanProperty HAS_BOOK = BlockStateProperties.HAS_BOOK;
    public TileBlock(BlockBehaviour.Properties properties) {
        super(properties);
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
    public InteractionResult use(BlockState state, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult result) {
        // Получаем предмет в руке игрока
        ItemStack itemStack = player.getItemInHand(interactionHand);

        // Проверяем, что предмет в руке не пустой
        if (!itemStack.isEmpty()) {
            // Проверяем, что позиция блока не пустая
            if (level.getBlockState(blockPos).isSolidRender(level, blockPos)) {
                // Получаем позицию блока, на который хотим положить предмет (например, блок над нажатым блоком)
                BlockPos blockAbovePos = blockPos.above();

                // Проверяем, что блок выше существует и не занят
                if (level.getBlockState(blockAbovePos).isAir()) {
                    // Кладем предмет из руки на блок выше текущего
                    if (!level.isClientSide) {
                        level.setBlockAndUpdate(blockAbovePos, Blocks.OAK_PLANKS.defaultBlockState()); // Например, заменяем блок на доски из дуба
                        return InteractionResult.SUCCESS; // Взаимодействие успешно выполнено
                    }
                }
            }
        }

        return InteractionResult.PASS; // Взаимодействие не выполнено
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

