package com.ksoichiro.mcmod.fancyicecream.neoforge;

import com.ksoichiro.mcmod.fancyicecream.FancyIceCream;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FancyIceCreamNeoForgeTab {
    public static final DeferredRegister<CreativeModeTab> REGISTRAR =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FancyIceCream.MOD_ID);

    public static final Supplier<CreativeModeTab> FANCY_ICE_CREAM_MOD_TAB =
        REGISTRAR.register("fancyicecream_tab",
            () -> CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.fancyicecream_tab"))
                .icon(FancyIceCreamNeoForgeItems.VANILLA_ICE_CREAM.get()::getDefaultInstance)
                .displayItems((params, output) -> {
                    output.accept(FancyIceCreamNeoForgeItems.VANILLA_ICE_CREAM.get());
                    output.accept(FancyIceCreamNeoForgeItems.APPLE_ICE_CREAM.get());
                    output.accept(FancyIceCreamNeoForgeItems.CHOCO_CHIP_ICE_CREAM.get());
                    output.accept(FancyIceCreamNeoForgeItems.CHOCOLATE_ICE_CREAM.get());
                    output.accept(FancyIceCreamNeoForgeItems.GLOW_BERRY_ICE_CREAM.get());
                    output.accept(FancyIceCreamNeoForgeItems.GOLDEN_APPLE_ICE_CREAM.get());
                    output.accept(FancyIceCreamNeoForgeItems.HONEY_ICE_CREAM.get());
                    output.accept(FancyIceCreamNeoForgeItems.SWEET_BERRY_ICE_CREAM.get());
                    output.accept(FancyIceCreamNeoForgeItems.ICE_CREAM_STAND.get());
                    output.accept(FancyIceCreamNeoForgeItems.TRIPLE_ICE_CREAM_STAND.get());
                })
                .build());

    public static void register(IEventBus modEventBus) {
        REGISTRAR.register(modEventBus);
    }
}
