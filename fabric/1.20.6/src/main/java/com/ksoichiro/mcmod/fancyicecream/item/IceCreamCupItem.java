package com.ksoichiro.mcmod.fancyicecream.item;

import com.ksoichiro.mcmod.fancyicecream.entity.decoration.IceCreamCup;

public class IceCreamCupItem extends IceCreamStandItem {
    protected IceCreamStandFactory<?> getEntityFactory() {
        return IceCreamCup::new;
    }
}
