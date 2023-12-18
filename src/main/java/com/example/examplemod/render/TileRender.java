package com.example.examplemod.render;

import com.example.examplemod.entity.TileEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class TileRender implements BlockEntityRenderer<TileEntity> {

    @Override
    public void render(TileEntity tileEntity, float num, PoseStack stack, MultiBufferSource source, int combinedLight, int combinedOverlay) {
        // Получение предмета или блока, хранящегося в блоке
//        ItemStack itemStack = tileEntity.getItem();
        List<ItemStack> listed = tileEntity.getCurrentItemStacks();
        Level level = tileEntity.getLevel();
        renderItemFixed(level,stack,source,listed.get(0),0.3125D,0.3D,0.3125D,combinedLight,combinedOverlay);
        renderItemFixed(level,stack,source,listed.get(1),0.5D,0.3D,0.3125D,combinedLight,combinedOverlay);
        renderItemFixed(level,stack,source,listed.get(2),0.6875D,0.3D,0.3125D,combinedLight,combinedOverlay);

        renderItemFixed(level,stack,source,listed.get(3),0.3125D,0.3D,0.5D,combinedLight,combinedOverlay);
        renderItemFixed(level,stack,source,listed.get(4),0.5D,0.3D,0.5D,combinedLight,combinedOverlay);
        renderItemFixed(level,stack,source,listed.get(5),0.6875D,0.3D,0.5D,combinedLight,combinedOverlay);

        renderItemFixed(level,stack,source,listed.get(6),0.3125D,0.3D,0.6875D,combinedLight,combinedOverlay);
        renderItemFixed(level,stack,source,listed.get(7),0.5D,0.3D,0.6875D,combinedLight,combinedOverlay);
        renderItemFixed(level,stack,source,listed.get(8),0.6875D,0.3D,0.6875D,combinedLight,combinedOverlay);
//
//        if (!itemStack.isEmpty()) {
//            stack.pushPose();
//            stack.translate(0.5D, 1.0D, 0.5D); // Смещение для отображения предмета на блоке
//            stack.scale(0.5F, 0.5F, 0.5F); // Масштабирование предмета
//            BakedModel baked = Minecraft.getInstance().getItemRenderer().getModel(itemStack, level, (LivingEntity)null, 0);
//            Minecraft.getInstance().getItemRenderer().render(itemStack, ItemTransforms.TransformType.GROUND, true, stack, source, combinedLight, combinedOverlay, baked);
//            stack.popPose();
//        }
    }
    public static void renderItemFixed(Level level, PoseStack poseStack, MultiBufferSource source, ItemStack itemStack, double transX, double transY, double transZ, int combinedLight, int combinedOverlay) {
        if (!itemStack.isEmpty()) {
            poseStack.pushPose();
            poseStack.translate(transX, transY, transZ);
            poseStack.scale(0.5F, 0.5F, 0.5F);
            ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
            BakedModel baked = renderer.getModel(itemStack, level, (LivingEntity)null, 0);
            renderer.render(itemStack, ItemTransforms.TransformType.GROUND, true, poseStack, source, combinedLight, combinedOverlay, baked);
            poseStack.popPose();
        }
    }
//    @Override
//    public void render(TileEntity tileEntity, float p_112308_, PoseStack p_112309_, MultiBufferSource p_112310_, int p_112311_, int p_112312_) {
//        if (!tileEntity.isRemoved()) {
//            List<ItemStack> listed = tileEntity.getCurrentItemStacks();
//            BlockPos pos = tileEntity.getPos();
//            Level level = tileEntity.getLevel();
//            Direction facing = tileEntity.getBlockState().getValue(PLACEMENTFACING);
//
//            if(facing== Direction.UP) {//when placed on ground
//                renderItemFixed(level,p_112309_,p_112310_,listed.get(0),0.3125D,0.3D,0.3125D,p_112311_,p_112312_);
//                renderItemFixed(level,p_112309_,p_112310_,listed.get(1),0.5D,0.3D,0.3125D,p_112311_,p_112312_);
//                renderItemFixed(level,p_112309_,p_112310_,listed.get(2),0.6875D,0.3D,0.3125D,p_112311_,p_112312_);
//
//                renderItemFixed(level,p_112309_,p_112310_,listed.get(3),0.3125D,0.3D,0.5D,p_112311_,p_112312_);
//                renderItemFixed(level,p_112309_,p_112310_,listed.get(4),0.5D,0.3D,0.5D,p_112311_,p_112312_);
//                renderItemFixed(level,p_112309_,p_112310_,listed.get(5),0.6875D,0.3D,0.5D,p_112311_,p_112312_);
//
//                renderItemFixed(level,p_112309_,p_112310_,listed.get(6),0.3125D,0.3D,0.6875D,p_112311_,p_112312_);
//                renderItemFixed(level,p_112309_,p_112310_,listed.get(7),0.5D,0.3D,0.6875D,p_112311_,p_112312_);
//                renderItemFixed(level,p_112309_,p_112310_,listed.get(8),0.6875D,0.3D,0.6875D,p_112311_,p_112312_);
//            }
//
//        }
//    }
//

    /*
    'render (ItemStack, ItemTransforms.TransformType, boolean, PoseStack, MultiBufferSource, int, int, ...)'
    in 'ItemRenderer' cannot be applied to
    '(ItemStack, ItemTransforms.TransformType, boolean, int, int, PoseStack, BakedModel)'
     */
//    public static void renderItemFixed(Level worldIn, PoseStack p_112309_, MultiBufferSource p_112310_, ItemStack itemStack, double transX, double transY, double transZ, int p_112311_, int p_112312_) {
//        if (!itemStack.isEmpty()) {
//            p_112309_.pushPose();
//            p_112309_.translate(transX, transY, transZ);
//            p_112309_.scale(0.5F, 0.5F, 0.5F);
//            ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
//            BakedModel baked = renderer.getModel(itemStack, worldIn, (LivingEntity)null, 0);
//            renderer.render(itemStack, ItemDisplayContext.FIXED, true, p_112309_, p_112310_, p_112311_, p_112312_, baked);
//            p_112309_.popPose();
//        }
//    }

}
