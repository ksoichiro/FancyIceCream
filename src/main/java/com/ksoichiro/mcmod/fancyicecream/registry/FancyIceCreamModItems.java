package com.ksoichiro.mcmod.fancyicecream.registry;

import com.ksoichiro.mcmod.fancyicecream.item.VanillaIceCream;
import com.ksoichiro.mcmod.fancyicecream.main.FancyIceCreamMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(FancyIceCreamMod.MOD_ID)
public class FancyIceCreamModItems {
    public static final VanillaIceCream VANILLA_ICE_CREAM = new VanillaIceCream();

    @Mod.EventBusSubscriber(modid = FancyIceCreamMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Registerer {
        @SubscribeEvent
        public static void registerItem(final RegistryEvent.Register<Item> event) {
            event.getRegistry().registerAll(VANILLA_ICE_CREAM);
        }
    }
}
