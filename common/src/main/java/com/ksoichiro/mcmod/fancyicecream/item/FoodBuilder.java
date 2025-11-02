package com.ksoichiro.mcmod.fancyicecream.item;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;

/**
 * Version and loader independent food properties builder for ice cream items.
 * Compatible with Minecraft 1.21.1 API.
 */
public class FoodBuilder {
    private final FoodProperties.Builder builder;

    public FoodBuilder() {
        this.builder = new FoodProperties.Builder();
    }

    public FoodBuilder nutrition(int nutrition) {
        this.builder.nutrition(nutrition);
        return this;
    }

    public FoodBuilder saturationMod(float saturationMod) {
        this.builder.saturationModifier(saturationMod);
        return this;
    }

    public FoodBuilder effect(Holder<MobEffect> effect, int duration, int amplifier, float probability) {
        MobEffectInstance effectInstance = new MobEffectInstance(effect, duration, amplifier);
        this.builder.effect(effectInstance, probability);
        return this;
    }

    public FoodBuilder alwaysEat() {
        this.builder.alwaysEdible();
        return this;
    }

    public FoodProperties build() {
        return this.builder.build();
    }
}