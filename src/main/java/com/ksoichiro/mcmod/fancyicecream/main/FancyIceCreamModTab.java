package com.ksoichiro.mcmod.fancyicecream.main;

import com.ksoichiro.mcmod.fancyicecream.registry.FancyIceCreamModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class FancyIceCreamModTab extends CreativeModeTab {
    public FancyIceCreamModTab() {
        super("fancyicecreammod_tab");
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(FancyIceCreamModItems.ICE_CREAM);
    }
}
