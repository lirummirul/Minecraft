package com.example.examplemod.item.custom;

import com.example.examplemod.gui.MyGuiMenu;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.Nullable;

public class MyGui extends Item implements MenuProvider {

    protected final ContainerData data;

    public MyGui(Properties tab) {
        super(new Item.Properties()
                .tab(CreativeModeTab.TAB_MISC)
                .stacksTo(128));

        this.data = new ContainerData() {
            @Override
            public int get(int p_39284_) {
                return 0;
            }

            @Override
            public void set(int p_39285_, int p_39286_) {

            }

            @Override
            public int getCount() {
                return 5;
            }
        };

    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player != null) {
            player.openMenu(getMenuProvider(context));
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    private MenuProvider getMenuProvider(UseOnContext context) {
        return new SimpleMenuProvider((id, inventory, player) -> new MyGuiMenu(id, inventory, this.data), getDisplayName());
    }


    @Override
    public Component getDisplayName() {
        return Component.literal("My GUI").setStyle(Style.EMPTY.withBold(true).withColor(ChatFormatting.WHITE));
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new MyGuiMenu(id, inventory, this.data);
    }
}
