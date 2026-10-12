package com.ksoichiro.mcmod.fancyicecream.item;

import net.minecraft.world.item.Item;

public class SweetBerryIceCream extends Item {
    public SweetBerryIceCream(String itemName) {
        super(new ItemProperties(itemName)
                .food(new FoodBuilder()
                        .nutrition(4)
                        .saturationMod(0.3F)
                        .build()));
    }
}
