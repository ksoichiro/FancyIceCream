package com.ksoichiro.mcmod.fancyicecream.item;

import net.minecraft.item.Item;

public class ChocolateIceCream extends Item {
    public ChocolateIceCream() {
        super(new ItemProperties()
                .food(new FoodBuilder()
                        .nutrition(4)
                        .saturationMod(0.3F)
                        .build()));
    }
}
