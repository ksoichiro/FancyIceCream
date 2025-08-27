package com.ksoichiro.mcmod.fancyicecream.item;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;

public enum Effects {
    REGENERATION(MobEffects.REGENERATION),
    ABSORPTION(MobEffects.ABSORPTION);

    private Holder<MobEffect> effect;

    Effects(Holder<MobEffect> effect) {
        this.effect = effect;
    }

    public Holder<MobEffect> getMobEffect() {
        return this.effect;
    }
}
