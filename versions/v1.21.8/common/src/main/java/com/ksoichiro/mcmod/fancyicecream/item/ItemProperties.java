package com.ksoichiro.mcmod.fancyicecream.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

/**
 * Minecraft 1.21.8 specific item properties builder.
 * Temporarily simplified for dependency testing.
 */
public class ItemProperties extends Item.Properties {
    public ItemProperties(String itemName) {
        this.setId(ResourceKey.create(Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath("fancyicecream", itemName)));
    }
}