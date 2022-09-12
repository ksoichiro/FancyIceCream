package com.ksoichiro.mcmod.fancyicecream.common;

import com.ksoichiro.mcmod.fancyicecream.main.FancyIceCreamMod;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

public class Tag<T> {
    private ITag.INamedTag<T> tag;

    public static Tag<Item> createItemTag(String name) {
        Tag<Item> t = new Tag<>();
        t.tag = ItemTags.makeWrapperTag(FancyIceCreamMod.MOD_ID + ":" + name);
        return t;
    }

    public boolean contains(T element) {
        return this.tag.func_230235_a_(element);
    }
}
