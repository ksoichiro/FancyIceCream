package com.ksoichiro.mcmod.fancyicecream.common;

import com.ksoichiro.mcmod.fancyicecream.main.FancyIceCreamMod;
import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class Tag<T> {
    private net.minecraftforge.common.Tags.IOptionalNamedTag<T> tag;

    public static Tag<Item> createItemTag(String name) {
        Tag<Item> t = new Tag<>();
        t.tag = ItemTags.createOptional(new ResourceLocation(FancyIceCreamMod.MOD_ID, name));
        return t;
    }

    public boolean contains(T element) {
        return this.tag.contains(element);
    }
}
