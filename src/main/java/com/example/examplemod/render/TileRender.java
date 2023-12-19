package com.example.examplemod.render;

import com.example.examplemod.block.TileBlock;
import com.example.examplemod.entity.TileEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;

import java.util.List;

public class TileRender implements BlockEntityRenderer<TileEntity> {
    public TileRender(BlockEntityRendererProvider.Context context) { }

//    @Override
//    public void render(TileEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack,
//                       MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
//        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
//        ItemStack itemStack = pBlockEntity.getRenderStack();
//        pPoseStack.pushPose();
//        pPoseStack.translate(0.5f, 0.65f, 0.5f);
//        pPoseStack.scale(0.25f, 0.25f, 0.25f);
//        pPoseStack.mulPose(Vector3f.XP.rotationDegrees(90));
//
//        switch (pBlockEntity.getBlockState().getValue(TileBlock.FACING)) {
//            case NORTH -> pPoseStack.mulPose(Vector3f.ZP.rotationDegrees(0));
//            case EAST -> pPoseStack.mulPose(Vector3f.ZP.rotationDegrees(90));
//            case SOUTH -> pPoseStack.mulPose(Vector3f.ZP.rotationDegrees(180));
//            case WEST -> pPoseStack.mulPose(Vector3f.ZP.rotationDegrees(270));
//        }
//
//        itemRenderer.renderStatic(itemStack, ItemTransforms.TransformType.GUI, getLightLevel(pBlockEntity.getLevel(),
//                        pBlockEntity.getBlockPos()),
//                OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, 1);
//        pPoseStack.popPose();
//    }


    @Override
    public void render(TileEntity tileEntity, float num, PoseStack stack, MultiBufferSource source, int combinedLight, int combinedOverlay) {
        ItemStack itemStack = tileEntity.getItem();
        Level level = tileEntity.getLevel();
        if (!itemStack.isEmpty()) {
            renderItemFixed(tileEntity, level, stack, source, itemStack, 0.5D, 1.4D, 0.5D, combinedLight, combinedOverlay);
        } else {
            renderItemFixed(tileEntity, level, stack, source, itemStack, 0D, 0D, 0D, combinedLight, combinedOverlay);
        }
    }
    public static void renderItemFixed(TileEntity tileEntity, Level level, PoseStack poseStack, MultiBufferSource source, ItemStack itemStack, double transX, double transY, double transZ, int combinedLight, int combinedOverlay) {
        if (!itemStack.isEmpty()) {
            poseStack.pushPose(); // метод сохраняет текущее состояние трансформации (позиция, масштаб, поворот), это нужно для временного изменения трансформации
            poseStack.translate(transX, transY, transZ); // перемещает объект в 3 мерном пространстве
            poseStack.scale(0.5F, 0.5F, 0.5F); // масштабирует объект
            ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
//            BakedModel baked = renderer.getModel(itemStack, level, (LivingEntity)null, 0);
//            renderer.render(itemStack, ItemTransforms.TransformType.GROUND, true, poseStack, source, combinedLight, combinedOverlay, baked);
            renderer.renderStatic(itemStack, ItemTransforms.TransformType.GUI,
                    getLightLevel(level, tileEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, source, 1);
            poseStack.popPose();
        }
    }

    private static int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}
