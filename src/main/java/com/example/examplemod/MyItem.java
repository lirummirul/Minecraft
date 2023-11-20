package com.example.examplemod;

import net.minecraft.world.item.Item;

import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MyItem {
    // Создаёт отложенный регистратор для моего предмета
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ExampleMod.MODID);
    // Регистрирую свой предмет как еду, в креативе его в этой вкладке можно будет найти
    public static final RegistryObject<Item> MY_ITEM = ITEMS.register("my_item", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD)));

    // Основной регистратор
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}