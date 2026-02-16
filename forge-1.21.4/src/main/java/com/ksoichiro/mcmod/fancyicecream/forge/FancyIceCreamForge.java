package com.ksoichiro.mcmod.fancyicecream.forge;

import com.ksoichiro.mcmod.fancyicecream.FancyIceCream;
import com.ksoichiro.mcmod.fancyicecream.item.ModelRegistrar;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(FancyIceCream.MOD_ID)
public class FancyIceCreamForge {
    public FancyIceCreamForge(FMLJavaModLoadingContext context) {
        IEventBus eventBus = context.getModEventBus();
        FancyIceCreamForgeItems.register(eventBus);
        FancyIceCreamForgeEntityType.register(eventBus);
        FancyIceCreamForgeTab.register(eventBus);
        ModelRegistrar.registerModels();
    }
}
