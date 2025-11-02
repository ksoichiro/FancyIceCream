package com.ksoichiro.mcmod.fancyicecream;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

/**
 * NeoForge client-side initialization for FancyIceCream mod.
 */
@Mod.EventBusSubscriber(modid = "fancyicecream", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class FancyIceCreamNeoForgeClient {

    public static void init() {
        // Client-side initialization
        // Entity renderers, item models, etc. will be registered here
    }
}