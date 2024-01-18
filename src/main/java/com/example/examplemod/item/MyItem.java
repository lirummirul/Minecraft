package com.example.examplemod.item;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.init.ModEntities;
import com.example.examplemod.item.custom.MyEnderItem;
import com.example.examplemod.item.custom.MyGui;
import net.minecraft.world.item.Item;

import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MyItem {
    // Создаёт отложенный регистратор для моего предмета
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ExampleMod.MODID);
    // Регистрирую свой предмет как еду, в креативе его в этой вкладке можно будет найти
    public static final RegistryObject<Item> MY_ITEM = ITEMS.register("my_item",
            () -> new MyEnderItem(new Item.Properties().tab(CreativeModeTab.TAB_TRANSPORTATION).stacksTo(128)));

    public static final RegistryObject<Item> TIGER_SPAWN_EGG = ITEMS.register("tiger_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.TIGER, 0xD57E36, 0x1D0D00,
                    new Item.Properties().tab(CreativeModeTab.TAB_FOOD)));

    public static final RegistryObject<Item> MY_GUI = ITEMS.register("my_gui",
            () -> new MyGui(new Item.Properties().tab(CreativeModeTab.TAB_TRANSPORTATION).stacksTo(128)));

    // Основной регистратор
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}