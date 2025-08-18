package com.ksoichiro.mcmod.fancyicecream.main;

import com.ksoichiro.mcmod.fancyicecream.registry.FancyIceCreamModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.ksoichiro.mcmod.fancyicecream.main.FancyIceCreamMod.MOD_ID;

@Mod.EventBusSubscriber(modid = FancyIceCreamMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FancyIceCreamModTab {
    @SubscribeEvent
    public static void buildContents(CreativeModeTabEvent.Register event) {
        event.registerCreativeModeTab(new ResourceLocation(MOD_ID, ""), builder ->
            builder.title(Component.translatable("itemGroup.fancyicecream_tab"))
                .icon(() -> new ItemStack(FancyIceCreamModItems.VANILLA_ICE_CREAM.get()))
                .displayItems((params, output, display) -> {
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
        );
    }
}
