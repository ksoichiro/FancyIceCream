package com.ksoichiro.mcmod.fancyicecream.main;

import com.ksoichiro.mcmod.fancyicecream.FancyIceCreamModInfo;
import com.ksoichiro.mcmod.fancyicecream.ForgeCompat41_0_1;
import com.ksoichiro.mcmod.fancyicecream.ForgeCompat41_0_64;
import com.ksoichiro.mcmod.fancyicecream.ForgeCompat41_0_94;
import com.ksoichiro.mcmod.fancyicecream.IForgeCompat;
import com.ksoichiro.mcmod.fancyicecream.entity.FancyIceCreamModEntityType;
import com.ksoichiro.mcmod.fancyicecream.registry.FancyIceCreamModItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.versions.forge.ForgeVersion;

import java.util.ArrayList;
import java.util.List;

@Mod(FancyIceCreamModInfo.MOD_ID)
public class FancyIceCreamMod {
    public static final String MOD_ID = "fancyicecream";
    public static final FancyIceCreamModTab FANCY_ICE_CREAM_MOD_TAB = new FancyIceCreamModTab();

    public FancyIceCreamMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        IForgeCompat compat;
        String[] versionStrings = ForgeVersion.getVersion().split("\\.");
        List<Integer> versions = new ArrayList<>();
        for (String versionString : versionStrings) {
            if (versionString.length() == 0) {
                continue;
            }
            Integer version = Integer.parseInt(versionString);
            versions.add(version);
        }
        if (versions.get(0) == 41 && versions.get(1) == 0 && versions.get(2) < 64) {
            compat = ForgeCompat41_0_1.getInstance();
        } else if (versions.get(0) == 41 && versions.get(1) == 0 && versions.get(2) < 94) {
                compat = ForgeCompat41_0_64.getInstance();
        } else {
            compat = ForgeCompat41_0_94.getInstance();
        }
        compat.register(bus);
        FancyIceCreamModItems.register(bus, compat);
        FancyIceCreamModEntityType.register(bus, compat);
    }
}
