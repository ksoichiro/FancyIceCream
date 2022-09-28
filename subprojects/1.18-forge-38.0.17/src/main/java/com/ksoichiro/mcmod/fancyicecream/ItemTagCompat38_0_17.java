package com.ksoichiro.mcmod.fancyicecream;

import net.minecraft.world.item.Item;

public class ItemTagCompat38_0_17 implements IItemTagCompat {
    protected net.minecraftforge.common.Tags.IOptionalNamedTag<Item> tag;

    @Override
    public boolean contains(Object item) {
        if (!(item instanceof Item i)) {
            return false;
        }
        return this.tag.contains(i);
    }
}
