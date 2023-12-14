package com.example.examplemod.entity;

import com.example.examplemod.init.MyBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = "examplemod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TileEntity extends BlockEntity {

    public TileEntity(BlockPos blockPos, BlockState state) {
        super(MyBlockEntities.MY_TILE_ENTITY.get(), blockPos, state);

    }

//    public TileEntity(EntityType<?> tileEntityEntityType, Level level) {
//        super(tileEntityEntityType, level);
//    }
}
