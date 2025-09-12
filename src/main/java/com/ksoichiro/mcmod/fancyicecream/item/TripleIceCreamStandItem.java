package com.ksoichiro.mcmod.fancyicecream.item;

import com.ksoichiro.mcmod.fancyicecream.entity.decoration.TripleIceCreamStand;

public class TripleIceCreamStandItem extends IceCreamStandItem {
    public TripleIceCreamStandItem(String itemName) {
        super(itemName);
    }

    protected IceCreamStandFactory<?> getEntityFactory() {
        return TripleIceCreamStand::new;
    }
}
