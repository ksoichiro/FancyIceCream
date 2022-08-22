package com.ksoichiro.mcmod.fancyicecream.item;

import com.ksoichiro.mcmod.fancyicecream.main.FancyIceCreamMod;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class ChocolateIceCream extends Item {
    public ChocolateIceCream() {
        super(new Properties().tab(FancyIceCreamMod.FANCY_ICE_CREAM_MOD_TAB)
                .food(new FoodProperties.Builder()
                        .nutrition(4)
                        .saturationMod(0.3F)
                        .build()));
        this.setRegistryName("chocolateicecream");
    }
}
