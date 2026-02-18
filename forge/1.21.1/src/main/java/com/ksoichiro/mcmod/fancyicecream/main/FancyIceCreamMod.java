package com.ksoichiro.mcmod.fancyicecream.main;

import com.ksoichiro.mcmod.fancyicecream.entity.FancyIceCreamModEntityType;
import com.ksoichiro.mcmod.fancyicecream.registry.FancyIceCreamModItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(FancyIceCreamMod.MOD_ID)
public class FancyIceCreamMod {
    public static final String MOD_ID = "fancyicecream";

    public FancyIceCreamMod(FMLJavaModLoadingContext context) {
        IEventBus bus = context.getModEventBus();
        FancyIceCreamModItems.register(bus);
        FancyIceCreamModEntityType.register(bus);
        FancyIceCreamModTab.register(bus);
    }
}
