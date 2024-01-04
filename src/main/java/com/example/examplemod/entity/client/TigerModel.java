package com.example.examplemod.entity.client;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.entity.custom.TigerEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TigerModel extends AnimatedGeoModel<TigerEntity> {

    @Override
    public ResourceLocation getModelResource(TigerEntity object) {
        return new ResourceLocation(ExampleMod.MODID, "geo/tiger.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TigerEntity object) {
        return new ResourceLocation(ExampleMod.MODID, "textures/entity/tiger.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TigerEntity animatable) {
        return new ResourceLocation(ExampleMod.MODID, "animations/tiger.animation.json");
    }
}