package com.ksoichiro.mcmod.fancyicecream.main;

import com.ksoichiro.mcmod.fancyicecream.FancyIceCreamModInfo;
import com.ksoichiro.mcmod.fancyicecream.ForgeCompat41_0_1;
import com.ksoichiro.mcmod.fancyicecream.ForgeCompat41_0_94;
import com.ksoichiro.mcmod.fancyicecream.IForgeCompat;
import com.ksoichiro.mcmod.fancyicecream.entity.FancyIceCreamModEntityType;
import com.ksoichiro.mcmod.fancyicecream.registry.FancyIceCreamModItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(FancyIceCreamModInfo.MOD_ID)
public class FancyIceCreamMod {
    public static final String MOD_ID = "fancyicecream";
    public static final FancyIceCreamModTab FANCY_ICE_CREAM_MOD_TAB = new FancyIceCreamModTab();

    public FancyIceCreamMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        IForgeCompat compat;
        try {
            Class.forName("net.minecraftforge.client.event.ModelEvent");
            compat = ForgeCompat41_0_94.getInstance();
        } catch (ClassNotFoundException e) {
            // Forge 41.0.1 ~ 41.0.93
            compat = ForgeCompat41_0_1.getInstance();
        }
        compat.register(bus);
        FancyIceCreamModItems.register(bus, compat);
        FancyIceCreamModEntityType.register(bus, compat);
    }
}
