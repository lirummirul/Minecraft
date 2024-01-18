package com.example.examplemod.gui;

import com.google.common.collect.Lists;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Random;

public class MyGuiMenu extends AbstractContainerMenu {
    private final ContainerData data;
    private Inventory inv;

    public MyGuiMenu(int id, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(id, playerInventory, new SimpleContainerData(2));
    }

    public MyGuiMenu(int id, Inventory playerInventory, ContainerData data) {
        super(ModMenuType.MY_GUI_MENU.get(), id);
        this.data = data;
        inv = playerInventory;
    }

    public void inventoryPlusOne() {
        for (int i = 0; i < this.inv.getContainerSize(); ++i) {
            ItemStack itemStack = this.inv.getItem(i);
            if (itemStack.getCount() != 0) {
                itemStack.setCount(itemStack.getCount() + 1);
            }
        }
        this.inv.setChanged();
    }

    public void inventoryMinusOne() {
        for (int i = 0; i < this.inv.getContainerSize(); ++i) {
            ItemStack itemStack = this.inv.getItem(i);
            itemStack.setCount(itemStack.getCount() - 1);
        }
        this.inv.setChanged();
    }

    public void swapItemsInInventory() {
        int size = this.inv.getContainerSize();

        for (int i = 0; i < size; i++) {
            int randomSlotIndex = new Random().nextInt(size);

            ItemStack currentItem = this.inv.getItem(i);
            ItemStack randomItem = this.inv.getItem(randomSlotIndex);

            this.inv.setItem(i, randomItem);
            this.inv.setItem(randomSlotIndex, currentItem);
        }
    }

    public void inventoryCleansUp() {
        for (int i = 0; i < this.inv.getContainerSize(); ++i) {
            ItemStack itemStack = this.inv.getItem(i);
            itemStack.setCount(0);
        }
        this.inv.setChanged();
    }

    public void randomItems() {
        Iterable<Item> allItems = Registry.ITEM;
        for (int i = 0; i < this.inv.getContainerSize(); ++i) {
            int randomCount = new Random().nextInt(64);
            Item randomItem = getRandomItem(allItems);
            int trueI = new Random().nextInt(2);
            if (trueI == 1) {
                this.inv.setItem(i, new ItemStack(randomItem, randomCount));
            }
        }
        this.inv.setChanged();
    }

    private Item getRandomItem(Iterable<Item> items) {
        List<Item> itemList = Lists.newArrayList(items);
        return itemList.get(new Random().nextInt(itemList.size()));
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
