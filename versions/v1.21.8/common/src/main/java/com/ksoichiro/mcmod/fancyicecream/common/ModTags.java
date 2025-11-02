package com.ksoichiro.mcmod.fancyicecream.common;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

/**
 * Minecraft 1.21.8 specific tag definitions.
 * Temporarily simplified for dependency testing.
 */
public class ModTags {
    public static class Items {
        public static final TagKey<Item> ICE_CREAM = createTag("ice_cream");

        private static TagKey<Item> createTag(String name) {
            return TagKey.create(net.minecraft.core.registries.Registries.ITEM,
                    ResourceLocation.fromNamespaceAndPath("fancyicecream", name));
        }
    }
}