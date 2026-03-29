package com.ksoichiro.mcmod.fancyicecream.fabric;

import com.ksoichiro.mcmod.fancyicecream.item.ModelRegistrar;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class FancyIceCreamFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        FancyIceCreamFabricItems.register();
        FancyIceCreamFabricEntityType.register();
        FancyIceCreamFabricTab.register();
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            FancyIceCreamFabricEntityType.registerRenderers();
            ModelRegistrar.registerModels();
        }
    }
}
