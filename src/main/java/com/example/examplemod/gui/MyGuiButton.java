package com.example.examplemod.gui;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

public class MyGuiButton extends Button {
    private final Consumer<Integer> clickHandler;
    private final int buttonId;

    public MyGuiButton(int buttonId, int x, int y, Component buttonText, Consumer<Integer> clickHandler) {
        super(x, y, 200, 20, buttonText, (button) -> clickHandler.accept(buttonId));
        this.clickHandler = clickHandler;
        this.buttonId = buttonId;
    }

    // Дополнительные методы, если необходимо
    public int getButtonId() {
        return buttonId;
    }

    public void onClick() {
        clickHandler.accept(buttonId);
    }
}