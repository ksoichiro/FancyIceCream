package com.ksoichiro.mcmod.fancyicecream.item;

import net.minecraft.item.Item;

public class GoldenAppleIceCream extends Item {
    public GoldenAppleIceCream() {
        super(new ItemProperties()
                .food(new FoodBuilder()
                        .nutrition(4)
                        .saturationMod(1.2F)
                        .effect(Effects.REGENERATION, 100, 1, 1.0F)
                        .effect(Effects.ABSORPTION, 2400, 0, 1.0F)
                        .alwaysEat()
                        .build()));
    }
}
