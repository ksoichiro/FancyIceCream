package com.ksoichiro.mcmod.fancyicecream.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;

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
        this.builder.saturationMod(saturationMod);
        return this;
    }

    public FoodBuilder effect(Effects effect, int duration, int amplifier, float probability) {
        this.builder.effect(
                new MobEffectInstance(effect.getMobEffect(), duration, amplifier),
                probability);
        return this;
    }

    public FoodBuilder alwaysEat() {
        this.builder.alwaysEat();
        return this;
    }

    public FoodProperties build() {
        return this.builder.build();
    }
}
