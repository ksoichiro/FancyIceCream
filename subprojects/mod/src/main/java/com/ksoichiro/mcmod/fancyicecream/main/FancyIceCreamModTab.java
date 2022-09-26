package com.ksoichiro.mcmod.fancyicecream.main;

import com.ksoichiro.mcmod.fancyicecream.registry.FancyIceCreamModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class FancyIceCreamModTab extends CreativeModeTab {
    public FancyIceCreamModTab() {
        super("fancyicecream_tab");
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(FancyIceCreamModItems.VANILLA_ICE_CREAM.get());
    }
}
