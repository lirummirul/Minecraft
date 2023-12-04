package com.example.examplemod.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Event;
public class MyEventHandler extends Event {
    private Player player;
    private BlockPos blockPos;

    public MyEventHandler(Player player, BlockPos blockPos) {
        this.player = player;
        this.blockPos = blockPos;
    }

    public MyEventHandler() {}

    public Player getPlayer() {
        return player;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }
}