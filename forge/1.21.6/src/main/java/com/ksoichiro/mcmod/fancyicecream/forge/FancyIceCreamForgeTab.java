package com.ksoichiro.mcmod.fancyicecream.forge;

import com.ksoichiro.mcmod.fancyicecream.FancyIceCream;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class FancyIceCreamForgeTab {
    public static final DeferredRegister<CreativeModeTab> REGISTRAR =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FancyIceCream.MOD_ID);

    public static final RegistryObject<CreativeModeTab> FANCY_ICE_CREAM_MOD_TAB =
        REGISTRAR.register("fancyicecream_tab",
            () -> CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.fancyicecream_tab"))
                .icon(FancyIceCreamForgeItems.VANILLA_ICE_CREAM.get()::getDefaultInstance)
                .displayItems((params, output) -> {
                    output.accept(FancyIceCreamForgeItems.VANILLA_ICE_CREAM.get());
                    output.accept(FancyIceCreamForgeItems.APPLE_ICE_CREAM.get());
                    output.accept(FancyIceCreamForgeItems.CHOCO_CHIP_ICE_CREAM.get());
                    output.accept(FancyIceCreamForgeItems.CHOCOLATE_ICE_CREAM.get());
                    output.accept(FancyIceCreamForgeItems.GLOW_BERRY_ICE_CREAM.get());
                    output.accept(FancyIceCreamForgeItems.GOLDEN_APPLE_ICE_CREAM.get());
                    output.accept(FancyIceCreamForgeItems.HONEY_ICE_CREAM.get());
                    output.accept(FancyIceCreamForgeItems.SWEET_BERRY_ICE_CREAM.get());
                    output.accept(FancyIceCreamForgeItems.ICE_CREAM_STAND.get());
                    output.accept(FancyIceCreamForgeItems.TRIPLE_ICE_CREAM_STAND.get());
                })
                .build());

    public static void register(BusGroup busGroup) {
        REGISTRAR.register(busGroup);
    }
}
