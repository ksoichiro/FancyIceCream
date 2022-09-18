package com.ksoichiro.mcmod.fancyicecream.item;

import com.ksoichiro.mcmod.fancyicecream.entity.decoration.TripleIceCreamStand;

public class TripleIceCreamStandItem extends IceCreamStandItem {
    protected IceCreamStandFactory<?> getEntityFactory() {
        return TripleIceCreamStand::new;
    }
}
