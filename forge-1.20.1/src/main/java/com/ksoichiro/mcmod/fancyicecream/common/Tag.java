package com.ksoichiro.mcmod.fancyicecream.common;

import com.ksoichiro.mcmod.fancyicecream.main.FancyIceCreamMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.tags.ITagManager;

public class Tag<T> {
    private TagKey<T> tag;
    private IForgeRegistry<T> registry;

    public static Tag<Item> createItemTag(String name) {
        Tag<Item> t = new Tag<>();
        t.tag = ItemTags.create(ResourceLocation.fromNamespaceAndPath(FancyIceCreamMod.MOD_ID, name));
        t.registry = ForgeRegistries.ITEMS;
        return t;
    }

    public boolean contains(T element) {
        ITagManager<T> tagManager = registry.tags();
        return tagManager != null&& tagManager.getTag(this.tag).contains(element);
    }
}
