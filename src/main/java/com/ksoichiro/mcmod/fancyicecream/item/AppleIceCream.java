package com.ksoichiro.mcmod.fancyicecream.item;

import net.minecraft.world.item.Item;

public class AppleIceCream extends Item {
    public AppleIceCream(String itemName) {
        super(new ItemProperties(itemName)
                .food(new FoodBuilder()
                        .nutrition(4)
                        .saturationMod(0.3F)
                        .build()));
    }
}
