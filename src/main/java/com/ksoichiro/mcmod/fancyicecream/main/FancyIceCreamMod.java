package com.ksoichiro.mcmod.fancyicecream.main;

import com.ksoichiro.mcmod.fancyicecream.entity.FancyIceCreamModEntityType;
import com.ksoichiro.mcmod.fancyicecream.registry.FancyIceCreamModItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(FancyIceCreamMod.MOD_ID)
public class FancyIceCreamMod {
    public static final String MOD_ID = "fancyicecreammod";
    public static final FancyIceCreamModTab FANCY_ICE_CREAM_MOD_TAB = new FancyIceCreamModTab();

    public FancyIceCreamMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        FancyIceCreamModItems.register(bus);
        FancyIceCreamModEntityType.register(bus);
    }
}
