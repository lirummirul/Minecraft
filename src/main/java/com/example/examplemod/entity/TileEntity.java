package com.example.examplemod.entity;

import com.example.examplemod.init.MyBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mod.EventBusSubscriber(modid = "examplemod", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TileEntity extends BlockEntity {
    private int currentPage = 0;
    private ItemStack storedItem = ItemStack.EMPTY;
    private ItemStackHandler itemHandler = new ItemStackHandler(144);
    //    private ItemStackHandler itemHandler = createItemHandlerStorageSlab();
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


    public Optional<Integer> maybeLastNonEmptySlot() {
        for (int i = itemHandler.getSlots() - 1; i >= 0; i--) {
            if(!itemHandler.getStackInSlot(i).isEmpty()) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    public ItemStack removeItem(boolean simulate) {
        return maybeLastNonEmptySlot().map(slot -> itemHandler.extractItem(slot, itemHandler.getStackInSlot(slot).getCount(), simulate))
                .orElse(ItemStack.EMPTY);
    }

//    public List<ItemStack> getCurrentItemStacks() {
//        List<ItemStack> listed = new ArrayList<>();
//        int currentpage = getCurrentPage()+1;
//        int maxPage = currentpage*9;
//        int minPage = maxPage-9;
//        for (int i = minPage; i < maxPage; i++) {
//            listed.add(itemHandler.getStackInSlot(i));
//        }
//        return listed;
//    }
//
//    public int getCurrentPage() {
//        return (this.currentPage<=0)?(0):(this.currentPage);
//    }

//    public ItemStackHandler createItemHandlerStorageSlab() {
//        //9 slots per page, up to 16 pages 0-15
//        return new ItemStackHandler(144) {
//            @Nonnull
//            @Override
//            public ItemStack getStackInSlot(int slot) {
//                return super.getStackInSlot((slot > getSlots()) ? (0) : (slot));
//            }
//        };
//    }

//    public TileEntity(EntityType<?> tileEntityEntityType, Level level) {
//        super(tileEntityEntityType, level);
//    }
}
