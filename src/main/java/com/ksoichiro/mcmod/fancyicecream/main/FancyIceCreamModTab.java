package com.ksoichiro.mcmod.fancyicecream.main;

import com.ksoichiro.mcmod.fancyicecream.registry.FancyIceCreamModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class FancyIceCreamModTab {
    public static final DeferredRegister<CreativeModeTab> REGISTRAR =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FancyIceCreamMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> FANCY_ICE_CREAM_MOD_TAB =
        REGISTRAR.register("fancyicecream_tab",
            () -> CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.fancyicecream_tab"))
                .icon(FancyIceCreamModItems.VANILLA_ICE_CREAM.get()::getDefaultInstance)
                .displayItems((params, output) -> {
                    output.accept(FancyIceCreamModItems.VANILLA_ICE_CREAM.get());
                    output.accept(FancyIceCreamModItems.APPLE_ICE_CREAM.get());
                    output.accept(FancyIceCreamModItems.CHOCO_CHIP_ICE_CREAM.get());
                    output.accept(FancyIceCreamModItems.CHOCOLATE_ICE_CREAM.get());
                    output.accept(FancyIceCreamModItems.GLOW_BERRY_ICE_CREAM.get());
                    output.accept(FancyIceCreamModItems.GOLDEN_APPLE_ICE_CREAM.get());
                    output.accept(FancyIceCreamModItems.HONEY_ICE_CREAM.get());
                    output.accept(FancyIceCreamModItems.ICE_CREAM_STAND.get());
                    output.accept(FancyIceCreamModItems.TRIPLE_ICE_CREAM_STAND.get());
                })
                .build());

    public static void register(IEventBus eventBus) {
        REGISTRAR.register(eventBus);
    }
}
