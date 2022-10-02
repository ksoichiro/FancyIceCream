package com.ksoichiro.mcmod.fancyicecream.main;

import com.ksoichiro.mcmod.fancyicecream.*;
import com.ksoichiro.mcmod.fancyicecream.entity.FancyIceCreamModEntityType;
import com.ksoichiro.mcmod.fancyicecream.registry.FancyIceCreamModItems;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.pool.TypePool;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.versions.forge.ForgeVersion;

import java.util.ArrayList;
import java.util.List;

@Mod(FancyIceCreamModInfo.MOD_ID)
public class FancyIceCreamMod {
    public static final String MOD_ID = "fancyicecream";
    public static FancyIceCreamModTab FANCY_ICE_CREAM_MOD_TAB;
    public static IForgeCompat FORGE_COMPAT;

    public FancyIceCreamMod() throws ClassNotFoundException {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        String[] versionStrings = ForgeVersion.getVersion().split("\\.");
        List<Integer> versions = new ArrayList<>();
        for (String versionString : versionStrings) {
            if (versionString.length() == 0) {
                continue;
            }
            Integer version = Integer.parseInt(versionString);
            versions.add(version);
        }
        Class<?> entityAdditionalSpawnDataClass = null;
        if (versions.get(0) == 37 && versions.get(1) == 1 && versions.get(2) >= 1) {
            FORGE_COMPAT = ForgeCompat37_1_1.getInstance();
            entityAdditionalSpawnDataClass = Class.forName("net.minecraftforge.fmllegacy.common.registry.IEntityAdditionalSpawnData");
        } else if (versions.get(0) == 38 && versions.get(1) == 0 && versions.get(2) >= 17) {
            FORGE_COMPAT = ForgeCompat38_0_17.getInstance();
        } else if (versions.get(0) == 40 && versions.get(1) == 1) {
            FORGE_COMPAT = ForgeCompat40_1_73.getInstance();
        } else if (versions.get(0) == 41 && versions.get(1) == 0 && versions.get(2) < 64) {
            FORGE_COMPAT = ForgeCompat41_0_1.getInstance();
        } else if (versions.get(0) == 41 && versions.get(1) == 0 && versions.get(2) < 94) {
            FORGE_COMPAT = ForgeCompat41_0_64.getInstance();
        } else {
            FORGE_COMPAT = ForgeCompat41_0_94.getInstance();
        }
        if (entityAdditionalSpawnDataClass == null) {
            entityAdditionalSpawnDataClass = Class.forName("net.minecraftforge.entity.IEntityAdditionalSpawnData");
        }
        TypePool typePool = TypePool.Default.ofSystemLoader();
        new ByteBuddy()
            .redefine(typePool.describe("com.ksoichiro.mcmod.fancyicecream.entity.decoration.IceCreamStand").resolve(), ClassFileLocator.ForClassLoader.ofSystemLoader())
            .implement(entityAdditionalSpawnDataClass)
            .make()
            .load(ClassLoader.getSystemClassLoader(), ClassLoadingStrategy.Default.INJECTION)
            .getLoaded();

        FORGE_COMPAT.register(bus);
        FancyIceCreamModItems.register(bus, FORGE_COMPAT);
        FancyIceCreamModEntityType.register(bus, FORGE_COMPAT);
        FANCY_ICE_CREAM_MOD_TAB = new FancyIceCreamModTab();
    }
}
