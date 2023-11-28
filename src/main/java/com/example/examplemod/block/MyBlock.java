package com.example.examplemod.block;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.item.MyItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class MyBlock {

    // Создаёт отложенный регистратор для моего блока
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ExampleMod.MODID);

    // Регистрирую свой блок
//    public static final RegistryObject<Block> MY_BLOCK = registerBlock("my_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(1, 1).sound(SoundType.BONE_BLOCK).noCollission()), CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Block> MY_BLOCK = registerBlock("my_block", () -> new BridgeBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.0F, 1.0F)));

    // Вспомогательный метод для регистриции, вызываю метод registerBlockItem,
    // который регистрирует мой блок как предмет, чтобы была возмонжость держать его в инвенторе
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    // Создаёт и регистрирует блок с указанными свойствами
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return MyItem.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
    private static <T extends Block> RegistryObject<T> registerBlockWithoutBlockItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }
    private static boolean always(BlockState p_50775_, BlockGetter p_50776_, BlockPos p_50777_) {
        return true;
    }

    // И снова регистрицая, уже основная для главного класса мода
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}