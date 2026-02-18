package com.ksoichiro.mcmod.fancyicecream.fabric;

import com.ksoichiro.mcmod.fancyicecream.item.ModelRegistrar;
import net.fabricmc.api.ModInitializer;

public class FancyIceCreamFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        FancyIceCreamFabricItems.register();
        FancyIceCreamFabricEntityType.register();
        FancyIceCreamFabricTab.register();
        ModelRegistrar.registerModels();
    }
}
