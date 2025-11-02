package com.ksoichiro.mcmod.fancyicecream.util;

import com.ksoichiro.mcmod.fancyicecream.core.FancyIceCreamConstants;

/**
 * Version and loader independent recipe helper utilities.
 * Contains common recipe patterns and ingredient definitions.
 */
public final class RecipeHelper {

    // Common vanilla ingredient names
    public static final String MILK_BUCKET = "minecraft:milk_bucket";
    public static final String SUGAR = "minecraft:sugar";
    public static final String EGG = "minecraft:egg";
    public static final String BOWL = "minecraft:bowl";

    // Vanilla ingredient names for specific ice creams
    public static final String APPLE = "minecraft:apple";
    public static final String COCOA_BEANS = "minecraft:cocoa_beans";
    public static final String HONEY_BOTTLE = "minecraft:honey_bottle";
    public static final String GLOW_BERRIES = "minecraft:glow_berries";
    public static final String GOLDEN_APPLE = "minecraft:golden_apple";
    public static final String CHOCOLATE = "minecraft:cocoa_beans"; // Using cocoa beans as chocolate

    // Common recipe patterns
    public static final String[] BASIC_ICE_CREAM_PATTERN = {
        " A ",
        "MBM",
        " S "
    };

    public static final String[] STAND_RECIPE_PATTERN = {
        "   ",
        " I ",
        " B "
    };

    public static final String[] TRIPLE_STAND_RECIPE_PATTERN = {
        "I I",
        " B ",
        "   "
    };

    /**
     * Gets the recipe name for an ice cream item.
     */
    public static String getIceCreamRecipeName(String iceCreamType) {
        return FancyIceCreamConstants.MOD_ID + ":" + iceCreamType;
    }

    /**
     * Gets the recipe name for an entity item.
     */
    public static String getEntityItemRecipeName(String entityType) {
        return FancyIceCreamConstants.MOD_ID + ":" + entityType;
    }

    private RecipeHelper() {
        // Utility class
    }
}