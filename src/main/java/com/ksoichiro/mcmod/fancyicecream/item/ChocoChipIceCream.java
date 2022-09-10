package com.ksoichiro.mcmod.fancyicecream.item;

import net.minecraft.item.Item;

public class ChocoChipIceCream extends Item {
    public ChocoChipIceCream() {
        super(new ItemProperties()
                .food(new FoodBuilder()
                        .nutrition(4)
                        .saturationMod(0.3F)
                        .build()));
    }
}
