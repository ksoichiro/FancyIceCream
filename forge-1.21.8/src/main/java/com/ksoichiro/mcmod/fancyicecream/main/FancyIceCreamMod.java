package com.ksoichiro.mcmod.fancyicecream.main;

import com.ksoichiro.mcmod.fancyicecream.entity.FancyIceCreamModEntityType;
import com.ksoichiro.mcmod.fancyicecream.registry.FancyIceCreamModItems;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(FancyIceCreamMod.MOD_ID)
public class FancyIceCreamMod {
    public static final String MOD_ID = "fancyicecream";

    public FancyIceCreamMod(FMLJavaModLoadingContext context) {
        var modBusGroup = context.getModBusGroup();
        FancyIceCreamModItems.register(modBusGroup);
        FancyIceCreamModEntityType.register(modBusGroup);
        FancyIceCreamModTab.register(modBusGroup);
        FancyIceCreamModItems.Registerer.registerModels();
    }
}
