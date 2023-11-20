package com.example.examplemod.item.custom;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class MyFoodItem extends Item {
    public MyFoodItem(Properties tab) {
        super(new Item.Properties()
                .food(new FoodProperties.Builder()
                        .nutrition(6) // Очков сытости
                        .saturationMod(0.6F) // Модификатор насыщенности
                        .build()));
    }

    // Анимация еды
    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.EAT;
    }

    // Метод с логикой после поедания
    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level world, net.minecraft.world.entity.@NotNull LivingEntity entityLiving) {
        super.finishUsingItem(stack, world, entityLiving);
        // Проверка, что ест персонаж
        if (entityLiving instanceof net.minecraft.world.entity.player.Player player) {
            player.getFoodData().eat(stack.getItem(), stack); // Обновляю количество сытости и предметов в инвенотре
        }
        return stack;
    }
}
