package com.ksoichiro.mcmod.fancyicecream.item;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;

public enum Effects {
    REGENERATION(MobEffects.REGENERATION),
    ABSORPTION(MobEffects.ABSORPTION);

    private MobEffect effect;

    Effects(MobEffect effect) {
        this.effect = effect;
    }

    public MobEffect getMobEffect() {
        return this.effect;
    }
}
