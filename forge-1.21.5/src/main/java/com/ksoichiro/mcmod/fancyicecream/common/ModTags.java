package com.ksoichiro.mcmod.fancyicecream.common;

import com.ksoichiro.mcmod.fancyicecream.main.FancyIceCreamMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {
    public static class Items {
        public static final TagKey<Item> ICE_CREAM = createTag("ice_cream");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(FancyIceCreamMod.MOD_ID, name));
        }
    }
}
