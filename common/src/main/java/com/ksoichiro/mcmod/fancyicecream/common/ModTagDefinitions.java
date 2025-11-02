package com.ksoichiro.mcmod.fancyicecream.common;

import com.ksoichiro.mcmod.fancyicecream.core.FancyIceCreamConstants;

/**
 * Version and loader independent tag definitions for FancyIceCream mod.
 * Contains tag names and utilities for creating tags.
 */
public final class ModTagDefinitions {

    /**
     * Item tag definitions.
     */
    public static final class Items {
        public static final String ICE_CREAM = FancyIceCreamConstants.ICE_CREAM_TAG;

        private Items() {
            // Utility class
        }
    }

    /**
     * Gets the namespace for mod tags.
     */
    public static String getModNamespace() {
        return FancyIceCreamConstants.MOD_ID;
    }

    /**
     * Creates a fully qualified tag name with mod namespace.
     */
    public static String createTagName(String tagName) {
        return getModNamespace() + ":" + tagName;
    }

    private ModTagDefinitions() {
        // Utility class
    }
}