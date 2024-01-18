package com.example.examplemod.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class MyGuiScreen extends AbstractContainerScreen<MyGuiMenu> {
    private Inventory inv;
    public MyGuiScreen(MyGuiMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        inv = inventory;
    }

    @Override
    protected void init() {
        super.init();

        this.addRenderableWidget(new MyGuiButton(1, 110, 50, Component.literal("+1 предметов в инвентаре"), this::onButtonClick));
        this.addRenderableWidget(new MyGuiButton(2, 110, 80, Component.literal("-1 предметов в инвентаре"), this::onButtonClick));
        this.addRenderableWidget(new MyGuiButton(3, 110, 110, Component.literal("Перемешать все предметы"), this::onButtonClick));
        this.addRenderableWidget(new MyGuiButton(4, 110, 140, Component.literal("Удалить все предметы"), this::onButtonClick));
        this.addRenderableWidget(new MyGuiButton(5, 110, 170, Component.literal("Заполнить случайными предметами"), this::onButtonClick));

        this.titleLabelX = 67;
        this.titleLabelY = -10;
        this.inventoryLabelX = -100;
        this.inventoryLabelY = -100;
    }

    public void onButtonClick(int buttonId) {
        switch (buttonId) {
            case 1 -> this.menu.inventoryPlusOne();
            case 2 -> this.menu.inventoryMinusOne();
            case 3 -> this.menu.swapItemsInInventory();
            case 4 -> this.menu.inventoryCleansUp();
            case 5 -> this.menu.randomItems();
        }
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float p_97788_, int p_97789_, int p_97790_) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
