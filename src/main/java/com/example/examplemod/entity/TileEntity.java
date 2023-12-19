package com.example.examplemod.entity;

import com.example.examplemod.init.MyBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemStackHandler;

@Mod.EventBusSubscriber(modid = "examplemod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TileEntity extends BlockEntity {
    private int currentPage = 0;
    private ItemStack storedItem = ItemStack.EMPTY;
    private ItemStackHandler itemHandler = new ItemStackHandler(1);
    public TileEntity(BlockPos blockPos, BlockState state) {
        super(MyBlockEntities.MY_TILE_ENTITY.get(), blockPos, state);

    }
    public ItemStack getItem() {
        return storedItem.copy(); // Возвращаем копию хранимого предмета
    }

    // Метод для установки предмета в блок
    public void setItem(ItemStack itemStack) {
        storedItem = itemStack.copy(); // Устанавливаем переданный предмет в хранимый предмет блока
        setChanged(); // Отмечаем блок, как изменившийся
    }

    // Метод для удаления предмета из блока
    public void removeItem() {
        storedItem = ItemStack.EMPTY; // Устанавливаем хранимый предмет в пустую ItemStack
        setChanged(); // Отмечаем блок, как изменившийся
    }
}
