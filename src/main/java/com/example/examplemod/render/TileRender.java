package com.example.examplemod.render;

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
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

public class TileRender implements BlockEntityRenderer<TileEntity> {
    public TileRender(BlockEntityRendererProvider.Context context) { }

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
            float angle = (float) (System.currentTimeMillis() / 10 % 360);

            poseStack.pushPose();
            poseStack.translate(transX, transY, transZ);
            poseStack.mulPose(Vector3f.YP.rotationDegrees(angle)); // Вращение вокруг оси Y
            poseStack.scale(0.5F, 0.5F, 0.5F);
            ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
//            System.out.println("combinedLight : " + combinedLight + " combinedOverlay : " + combinedOverlay);
            renderer.renderStatic((LivingEntity)null, itemStack, ItemTransforms.TransformType.GUI,
                    true, poseStack, source, level, combinedLight, OverlayTexture.NO_OVERLAY, getLightLevel(level, tileEntity.getBlockPos()));
            poseStack.popPose();
        }
    }

    private static int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}
