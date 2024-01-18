package com.example.examplemod.gui;

import com.example.examplemod.ExampleMod;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class MyGuiScreen extends AbstractContainerScreen<MyGuiMenu> {

//    private static final ResourceLocation TEXTURE =
//            new ResourceLocation(ExampleMod.MODID,"textures/gui/my_gui.png");

    public MyGuiScreen(MyGuiMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

//    protected MyGuiScreen(Component p_96550_) {
//        super(p_96550_);
//    }
//
    @Override
    protected void init() {
        super.init();
        // todo: координаты нужно сместить и найти Inventory
        this.addRenderableWidget(new MyGuiButton(1, 50, 0, Component.literal("Button 1"), this::onButtonClick));
        this.addRenderableWidget(new MyGuiButton(2, 50, 20, Component.literal("Button 2"), this::onButtonClick));
        this.addRenderableWidget(new MyGuiButton(3, 50, 40, Component.literal("Button 3"), this::onButtonClick));
        this.addRenderableWidget(new MyGuiButton(4, 50, 60, Component.literal("Button 4"), this::onButtonClick));
        this.addRenderableWidget(new MyGuiButton(5, 50, 80, Component.literal("Button 5"), this::onButtonClick));
        // Добавьте кнопки и другие элементы GUI
            // Обработка нажатия кнопки 1
        System.out.println("Я нахожусь в init MyGuiScreen");
    }

    public void onButtonClick(int buttonId) {
        switch (buttonId) {
            case 1 :
                break;
            case 2 :
                break;
            case 3 :
                break;
            case 4 :
                break;
            case 5 :
                break;
        }
    }

    @Override
    protected void renderBg(PoseStack p_97787_, float p_97788_, int p_97789_, int p_97790_) {

    }
}
