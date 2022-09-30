package com.ksoichiro.mcmod.fancyicecream;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class ItemTagCompat37_1_1 implements IItemTagCompat {
    protected net.minecraftforge.common.Tags.IOptionalNamedTag<Item> tag;

    @Override
    public boolean contains(Object item) {
        if (!(item instanceof Item i)) {
            return false;
        }
        return this.tag.contains(i);
    }
}
