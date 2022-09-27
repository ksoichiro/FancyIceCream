package com.ksoichiro.mcmod.fancyicecream;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.tags.ITagManager;

public class ItemTagCompat41_0_94 implements IItemTagCompat {
    protected TagKey<Item> tag;
    protected IForgeRegistry<Item> registry;

    @Override
    public boolean contains(Object item) {
        if (!(item instanceof Item i)) {
            return false;
        }
        ITagManager<Item> tagManager = registry.tags();
        return tagManager != null && tagManager.getTag(this.tag).contains(i);
    }
}
