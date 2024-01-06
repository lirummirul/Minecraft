package com.example.examplemod.init;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.block.BridgeBlock;
import com.example.examplemod.block.CopyBlock;
import com.example.examplemod.block.TileBlock;
import com.example.examplemod.entity.TileEntity;
import com.example.examplemod.item.MyItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = ExampleMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MyBlock {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ExampleMod.MODID);
    public static final RegistryObject<Block> MY_BLOCK =
            registerBlock("my_block", () -> new BridgeBlock(
                    BlockBehaviour.Properties.of(Material.STONE).strength(1.0F, 1.0F)));
    public static final RegistryObject<Block> MY_TILE_BLOCK =
            registerBlock("my_tile_block", () -> new TileBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(1.0F, 1.0F)
                    .lightLevel((state) -> 10)));

    public static final RegistryObject<Block> MY_COPY_BLOCK =
            registerBlock("my_copy_block", () -> new CopyBlock(
                    BlockBehaviour.Properties.of(Material.STONE).strength(1.0F, 1.0F)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return MyItem.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
