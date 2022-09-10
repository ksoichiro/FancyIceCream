package com.ksoichiro.mcmod.fancyicecream.main;

import com.ksoichiro.mcmod.fancyicecream.registry.FancyIceCreamModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class FancyIceCreamModTab extends ItemGroup {
    public FancyIceCreamModTab() {
        super("fancyicecreammod_tab");
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(FancyIceCreamModItems.VANILLA_ICE_CREAM.get());
    }
}
