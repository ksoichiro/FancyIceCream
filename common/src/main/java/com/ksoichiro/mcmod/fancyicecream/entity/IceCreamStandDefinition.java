package com.ksoichiro.mcmod.fancyicecream.entity;

import com.ksoichiro.mcmod.fancyicecream.core.FancyIceCreamConstants;

/**
 * Version and loader independent ice cream stand entity definitions.
 * Contains basic properties and constants for ice cream stand entities.
 */
public final class IceCreamStandDefinition {

    // Entity dimensions
    public static final float ICE_CREAM_STAND_WIDTH = 0.5f;
    public static final float ICE_CREAM_STAND_HEIGHT = 0.5f;

    public static final float TRIPLE_ICE_CREAM_STAND_WIDTH = 0.75f;
    public static final float TRIPLE_ICE_CREAM_STAND_HEIGHT = 0.75f;

    // Entity properties
    public static final boolean FIRE_IMMUNE = false;
    public static final int MAX_ICE_CREAMS_SINGLE = 1;
    public static final int MAX_ICE_CREAMS_TRIPLE = 3;

    // Rotation angles (in degrees)
    public static final float ROTATION_STEP = 45.0f;
    public static final int TOTAL_ROTATIONS = 8; // 360 / 45

    // Item placement offsets for triple stand
    public static final double TRIPLE_STAND_ITEM_OFFSET = 0.2;

    // Rendering properties
    public static final float ITEM_SCALE = 0.8f;
    public static final float ITEM_Y_OFFSET = 0.3f;

    private IceCreamStandDefinition() {
        // Utility class
    }

    /**
     * Gets the entity ID for ice cream stand.
     */
    public static String getIceCreamStandId() {
        return FancyIceCreamConstants.ICE_CREAM_STAND;
    }

    /**
     * Gets the entity ID for triple ice cream stand.
     */
    public static String getTripleIceCreamStandId() {
        return FancyIceCreamConstants.TRIPLE_ICE_CREAM_STAND;
    }

    /**
     * Gets the item ID for ice cream stand item.
     */
    public static String getIceCreamStandItemId() {
        return FancyIceCreamConstants.ICE_CREAM_STAND_ITEM;
    }

    /**
     * Gets the item ID for triple ice cream stand item.
     */
    public static String getTripleIceCreamStandItemId() {
        return FancyIceCreamConstants.TRIPLE_ICE_CREAM_STAND_ITEM;
    }
}