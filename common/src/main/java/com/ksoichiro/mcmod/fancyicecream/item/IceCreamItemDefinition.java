package com.ksoichiro.mcmod.fancyicecream.item;

import com.ksoichiro.mcmod.fancyicecream.core.FancyIceCreamConstants;

/**
 * Version and loader independent ice cream item definitions.
 * Contains nutritional values and effect definitions for all ice cream items.
 */
public final class IceCreamItemDefinition {

    // Nutritional values for ice cream items
    public static final int DEFAULT_NUTRITION = 4;
    public static final float DEFAULT_SATURATION_MOD = 0.3f;

    public static final int ENHANCED_NUTRITION = 6;
    public static final float ENHANCED_SATURATION_MOD = 0.5f;

    // Effect durations (in ticks)
    public static final int SHORT_EFFECT_DURATION = 600; // 30 seconds
    public static final int MEDIUM_EFFECT_DURATION = 1200; // 1 minute
    public static final int LONG_EFFECT_DURATION = 2400; // 2 minutes

    // Effect probabilities
    public static final float LOW_PROBABILITY = 0.3f;
    public static final float MEDIUM_PROBABILITY = 0.6f;
    public static final float HIGH_PROBABILITY = 0.9f;

    /**
     * Gets the food builder for vanilla ice cream.
     */
    public static FoodBuilder getVanillaIceCreamFood() {
        return new FoodBuilder()
                .nutrition(DEFAULT_NUTRITION)
                .saturationMod(DEFAULT_SATURATION_MOD);
    }

    /**
     * Gets the food builder for chocolate ice cream.
     */
    public static FoodBuilder getChocolateIceCreamFood() {
        return new FoodBuilder()
                .nutrition(DEFAULT_NUTRITION)
                .saturationMod(DEFAULT_SATURATION_MOD);
    }

    /**
     * Gets the food builder for apple ice cream.
     */
    public static FoodBuilder getAppleIceCreamFood() {
        return new FoodBuilder()
                .nutrition(DEFAULT_NUTRITION)
                .saturationMod(DEFAULT_SATURATION_MOD);
    }

    /**
     * Gets the food builder for honey ice cream.
     */
    public static FoodBuilder getHoneyIceCreamFood() {
        return new FoodBuilder()
                .nutrition(ENHANCED_NUTRITION)
                .saturationMod(ENHANCED_SATURATION_MOD);
    }

    /**
     * Gets the food builder for glow berry ice cream.
     */
    public static FoodBuilder getGlowBerryIceCreamFood() {
        return new FoodBuilder()
                .nutrition(DEFAULT_NUTRITION)
                .saturationMod(DEFAULT_SATURATION_MOD);
    }

    /**
     * Gets the food builder for golden apple ice cream.
     */
    public static FoodBuilder getGoldenAppleIceCreamFood() {
        return new FoodBuilder()
                .nutrition(ENHANCED_NUTRITION)
                .saturationMod(ENHANCED_SATURATION_MOD)
                .effect(Effects.REGENERATION.getMobEffect(), MEDIUM_EFFECT_DURATION, 0, HIGH_PROBABILITY)
                .effect(Effects.ABSORPTION.getMobEffect(), LONG_EFFECT_DURATION, 0, MEDIUM_PROBABILITY)
                .alwaysEat();
    }

    /**
     * Gets the food builder for choco chip ice cream.
     */
    public static FoodBuilder getChocoChipIceCreamFood() {
        return new FoodBuilder()
                .nutrition(DEFAULT_NUTRITION)
                .saturationMod(DEFAULT_SATURATION_MOD);
    }

    private IceCreamItemDefinition() {
        // Utility class
    }
}