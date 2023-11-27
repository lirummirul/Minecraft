package com.example.examplemod.item.custom;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;


public class MyEnderItem extends Item {


    // Вот тут выставляю я stacksTo = 128, взять максимально я могу 128 предметов,
    // а в инвентарь могу положить максимум 64, не понимаю как это изменить
    public MyEnderItem(Properties tab) {
        super(new Item.Properties()
                .tab(CreativeModeTab.TAB_MISC)
                .stacksTo(128));
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.SPEAR;
    }

//    @Override
//    public ItemStack getDefaultInstance() {
//        return super.getDefaultInstance().setCount(128);
//    }

    public @NotNull InteractionResultHolder<ItemStack> use(Level p_41190_, Player p_41191_, @NotNull InteractionHand p_41192_) {
        // Получаем предмет, который игрок держит в руке
        ItemStack itemstack = p_41191_.getItemInHand(p_41192_);
        // Воспроизводим звук при броске
        p_41190_.playSound((Player)null, p_41191_.getX(), p_41191_.getY(), p_41191_.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (p_41190_.getRandom().nextFloat() * 0.4F + 0.8F));
//      // Устанавливаем перезарядку (20 тиков = 1 секунда)
        p_41191_.getCooldowns().addCooldown(this, 20);
        // Проверка, что код выполняется на стороне сервера
        if (!p_41190_.isClientSide) {
            // Этот блок кода создаёт новый объект, который является сущностью метнутого эндер пёрла
            ThrownEnderpearl thrownenderpearl = new ThrownEnderpearl(p_41190_, p_41191_);
            // Устанавливает предмет, который будет использоваться для мётнутого эндер пёрла
            thrownenderpearl.setItem(itemstack);
            // Запускает предмет с определённой скоростью и углам в зависимости от угла, под которым смотрим ирок
            thrownenderpearl.shootFromRotation(p_41191_, p_41191_.getXRot(), p_41191_.getYRot(), 0.0F, 1.5F, 1.0F);
            // Добавляем предмет в мир
            p_41190_.addFreshEntity(thrownenderpearl);
        }

        // Засчитываем использование этого предмета в статистику
        p_41191_.awardStat(Stats.ITEM_USED.get(this));
        // Если у игрока не криатив, то вычитаем количество предмета в инвенторе
        if (!p_41191_.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        // Возвращает результат использования предмета
        return InteractionResultHolder.sidedSuccess(itemstack, p_41190_.isClientSide());
    }

}