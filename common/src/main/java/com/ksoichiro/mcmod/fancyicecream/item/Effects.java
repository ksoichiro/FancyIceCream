package com.ksoichiro.mcmod.fancyicecream.item;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;

/**
 * Version and loader independent effect definitions for ice cream items.
 */
public enum Effects {
    REGENERATION(MobEffects.REGENERATION),
    ABSORPTION(MobEffects.ABSORPTION);

    private final Holder<MobEffect> effect;

    Effects(Holder<MobEffect> effect) {
        this.effect = effect;
    }

    public Holder<MobEffect> getMobEffect() {
        return this.effect;
    }
}