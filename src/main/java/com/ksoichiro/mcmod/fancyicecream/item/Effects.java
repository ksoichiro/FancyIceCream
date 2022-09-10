package com.ksoichiro.mcmod.fancyicecream.item;

import net.minecraft.potion.Effect;

public enum Effects {
    REGENERATION(net.minecraft.potion.Effects.REGENERATION),
    ABSORPTION(net.minecraft.potion.Effects.ABSORPTION);

    private Effect effect;

    Effects(Effect effect) {
        this.effect = effect;
    }

    public Effect getMobEffect() {
        return this.effect;
    }
}
