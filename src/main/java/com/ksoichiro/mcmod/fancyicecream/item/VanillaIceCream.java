package com.ksoichiro.mcmod.fancyicecream.item;

import net.minecraft.world.item.Item;

public class VanillaIceCream extends Item {
    public VanillaIceCream() {
        super(new ItemProperties()
                .food(new FoodBuilder()
                        .nutrition(4)
                        .saturationMod(0.3F)
                        .build()));
    }
}
