package com.example.examplemod.gui;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;

public class MyGuiMenu extends AbstractContainerMenu {
    private final ContainerData data;

//    public MyGuiMenu(@Nullable MenuType<?> menuType, int i) {
//        super(menuType, i);
//    }

    public MyGuiMenu(int id, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(id, playerInventory, new SimpleContainerData(2));
        System.out.println("Я нахожусь в конструкторе на клиентской сторо");
    }
//    public MyGuiMenu(int id, Inventory playerInventory, FriendlyByteBuf extraData) {
//        super(ModMenuType.MY_GUI_MENU.get(), id);
//    }

    public MyGuiMenu(int id, Inventory playerInventory, ContainerData data) {
        super(ModMenuType.MY_GUI_MENU.get(), id);
        this.data = data;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
