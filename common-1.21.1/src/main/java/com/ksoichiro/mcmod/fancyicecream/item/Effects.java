package com.ksoichiro.mcmod.fancyicecream.item;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;

public enum Effects {
    REGENERATION(MobEffects.REGENERATION),
    ABSORPTION(MobEffects.ABSORPTION);

    private final Holder<MobEffect> mobEffect;

    Effects(Holder<MobEffect> mobEffect) {
        this.mobEffect = mobEffect;
    }

    public Holder<MobEffect> getMobEffect() {
        return mobEffect;
    }
}
