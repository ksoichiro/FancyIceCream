package com.ksoichiro.mcmod.fancyicecream.item;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;

public class FoodBuilder {
    private final Food.Builder builder;

    public FoodBuilder() {
        this.builder = new Food.Builder();
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
                () -> new EffectInstance(effect.getMobEffect(), duration, amplifier),
                probability);
        return this;
    }

    public FoodBuilder alwaysEat() {
        this.builder.alwaysEat();
        return this;
    }

    public Food build() {
        return this.builder.build();
    }
}
