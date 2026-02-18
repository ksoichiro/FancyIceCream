package com.ksoichiro.mcmod.fancyicecream.neoforge;

import com.ksoichiro.mcmod.fancyicecream.FancyIceCream;
import com.ksoichiro.mcmod.fancyicecream.item.ModelRegistrar;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(FancyIceCream.MOD_ID)
public class FancyIceCreamNeoForge {
    public FancyIceCreamNeoForge(IEventBus modEventBus) {
        FancyIceCreamNeoForgeItems.register(modEventBus);
        FancyIceCreamNeoForgeEntityType.register(modEventBus);
        FancyIceCreamNeoForgeTab.register(modEventBus);
        ModelRegistrar.registerModels();
    }
}
