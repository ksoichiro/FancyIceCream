package com.ksoichiro.mcmod.fancyicecream.common;

import com.ksoichiro.mcmod.fancyicecream.FancyIceCream;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {
    public static class Items {
        public static final TagKey<Item> ICE_CREAM = TagKey.create(Registries.ITEM,
                ResourceLocation.fromNamespaceAndPath(FancyIceCream.MOD_ID, "ice_cream"));
    }
}
