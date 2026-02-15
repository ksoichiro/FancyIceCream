package com.ksoichiro.mcmod.fancyicecream.forge;

import com.ksoichiro.mcmod.fancyicecream.FancyIceCream;
import com.ksoichiro.mcmod.fancyicecream.item.ModelRegistrar;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(FancyIceCream.MOD_ID)
public class FancyIceCreamForge {
    public FancyIceCreamForge(FMLJavaModLoadingContext context) {
        var modBusGroup = context.getModBusGroup();
        FancyIceCreamForgeItems.register(modBusGroup);
        FancyIceCreamForgeEntityType.register(modBusGroup);
        FancyIceCreamForgeTab.register(modBusGroup);
        ModelRegistrar.registerModels();
    }
}
