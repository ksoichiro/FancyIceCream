package com.ksoichiro.mcmod.fancyicecream;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

/**
 * Forge client-side initialization for FancyIceCream mod.
 */
@Mod.EventBusSubscriber(modid = "fancyicecream", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class FancyIceCreamForgeClient {

    public static void init() {
        // Client-side initialization
        // Entity renderers, item models, etc. will be registered here
    }
}