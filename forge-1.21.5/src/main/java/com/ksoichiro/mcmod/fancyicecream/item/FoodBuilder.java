package com.ksoichiro.mcmod.fancyicecream.item;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

import java.util.ArrayList;
import java.util.List;

public class FoodBuilder {
    private final FoodProperties.Builder builder;
    private final List<ApplyStatusEffectsConsumeEffect> effects;
    private boolean alwaysEdible = false;

    public FoodBuilder() {
        this.builder = new FoodProperties.Builder();
        this.effects = new ArrayList<>();
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
        ApplyStatusEffectsConsumeEffect consumeEffect = new ApplyStatusEffectsConsumeEffect(effectInstance, probability);
        this.effects.add(consumeEffect);
        return this;
    }

    public FoodBuilder alwaysEat() {
        this.builder.alwaysEdible();
        this.alwaysEdible = true;
        return this;
    }

    public FoodProperties build() {
        return this.builder.build();
    }

    public Consumable buildConsumableComponent() {
        Consumable.Builder consumableBuilder = Consumable.builder();
        
        // Add all effects to the consumable component
        for (ApplyStatusEffectsConsumeEffect effect : effects) {
            consumableBuilder.onConsume(effect);
        }
        
        // Set consumable properties
        consumableBuilder.hasConsumeParticles(true);
        consumableBuilder.consumeSeconds(1.6f); // Default eating time
        
        return consumableBuilder.build();
    }

    public boolean hasEffects() {
        return !effects.isEmpty();
    }
}
