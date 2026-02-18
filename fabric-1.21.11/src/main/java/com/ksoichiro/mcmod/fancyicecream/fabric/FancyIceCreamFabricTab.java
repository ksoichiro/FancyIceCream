package com.ksoichiro.mcmod.fancyicecream.fabric;

import com.ksoichiro.mcmod.fancyicecream.FancyIceCream;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;

public class FancyIceCreamFabricTab {
    public static void register() {
        CreativeModeTab tab = FabricItemGroup.builder()
                .title(Component.translatable("itemGroup.fancyicecream_tab"))
                .icon(FancyIceCreamFabricItems.VANILLA_ICE_CREAM::getDefaultInstance)
                .displayItems((params, output) -> {
                    output.accept(FancyIceCreamFabricItems.VANILLA_ICE_CREAM);
                    output.accept(FancyIceCreamFabricItems.APPLE_ICE_CREAM);
                    output.accept(FancyIceCreamFabricItems.CHOCO_CHIP_ICE_CREAM);
                    output.accept(FancyIceCreamFabricItems.CHOCOLATE_ICE_CREAM);
                    output.accept(FancyIceCreamFabricItems.GLOW_BERRY_ICE_CREAM);
                    output.accept(FancyIceCreamFabricItems.GOLDEN_APPLE_ICE_CREAM);
                    output.accept(FancyIceCreamFabricItems.HONEY_ICE_CREAM);
                    output.accept(FancyIceCreamFabricItems.SWEET_BERRY_ICE_CREAM);
                    output.accept(FancyIceCreamFabricItems.ICE_CREAM_STAND);
                    output.accept(FancyIceCreamFabricItems.TRIPLE_ICE_CREAM_STAND);
                })
                .build();

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
                Identifier.fromNamespaceAndPath(FancyIceCream.MOD_ID, "fancyicecream_tab"),
                tab);
    }
}
