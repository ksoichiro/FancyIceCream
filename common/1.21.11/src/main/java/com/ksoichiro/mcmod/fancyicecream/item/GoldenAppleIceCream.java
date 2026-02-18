package com.ksoichiro.mcmod.fancyicecream.item;

import net.minecraft.world.item.Item;

public class GoldenAppleIceCream extends Item {
    public GoldenAppleIceCream(String itemName) {
        super(new ItemProperties(itemName)
                .foodBuilder(new FoodBuilder()
                        .nutrition(4)
                        .saturationMod(1.2F)
                        .effect(Effects.REGENERATION.getMobEffect(), 100, 1, 1.0F)
                        .effect(Effects.ABSORPTION.getMobEffect(), 2400, 0, 1.0F)
                        .alwaysEat()));
    }
}
