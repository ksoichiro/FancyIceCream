package com.ksoichiro.mcmod.fancyicecream;

import com.ksoichiro.mcmod.fancyicecream.registry.ModItems;
import net.neoforged.fml.common.Mod;

/**
 * NeoForge entry point for FancyIceCream mod.
 * Uses NeoForge-specific initialization.
 */
@Mod("fancyicecream")
public class FancyIceCreamNeoForge {

    public FancyIceCreamNeoForge() {
        // Initialize common registries
        ModItems.init();

        // Initialize NeoForge-specific components
        FancyIceCreamNeoForgeClient.init();
    }
}