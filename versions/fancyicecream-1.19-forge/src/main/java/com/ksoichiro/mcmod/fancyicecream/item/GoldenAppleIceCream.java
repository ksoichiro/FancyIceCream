package com.ksoichiro.mcmod.fancyicecream.item;

import com.ksoichiro.mcmod.fancyicecream.main.FancyIceCreamMod;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class GoldenAppleIceCream extends Item {
    public GoldenAppleIceCream() {
        super(new Properties().tab(FancyIceCreamMod.FANCY_ICE_CREAM_MOD_TAB)
                .food(new FoodProperties.Builder()
                        .nutrition(4)
                        .saturationMod(1.2F)
                        .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100, 1), 1.0F)
                        .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 2400, 0), 1.0F)
                        .alwaysEat()
                        .build()));
    }
}
