package com.ksoichiro.mcmod.fancyicecream.registry;

import com.ksoichiro.mcmod.fancyicecream.item.*;
import com.ksoichiro.mcmod.fancyicecream.main.FancyIceCreamMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.RegisterEvent;

import static com.ksoichiro.mcmod.fancyicecream.entity.decoration.IceCreamStandRenderer.STAND_LOCATION;
import static com.ksoichiro.mcmod.fancyicecream.main.FancyIceCreamMod.MOD_ID;

public class FancyIceCreamModItems {
    public static final Item VANILLA_ICE_CREAM = registerItem("vanilla_ice_cream", new VanillaIceCream());

    private static Item registerItem(String name, Item item) {
        return item;
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Registerer {
        @SubscribeEvent
        public static void registerItem(final RegisterEvent event) {
            event.register(ForgeRegistries.Keys.ITEMS,
                    helper -> {
                        helper.register("vanilla_ice_cream", new VanillaIceCream());
                        helper.register("apple_ice_cream", new AppleIceCream());
                        helper.register("choco_chip_ice_cream", new ChocoChipIceCream());
                        helper.register("chocolate_ice_cream", new ChocolateIceCream());
                        helper.register("glow_berry_ice_cream", new GlowBerryIceCream());
                        helper.register("golden_apple_ice_cream", new GoldenAppleIceCream());
                        helper.register("honey_ice_cream", new HoneyIceCream());
                        helper.register("ice_cream_stand", new IceCreamStandItem());
                    });
        }

        @SubscribeEvent
        public static void registerModels(final ModelEvent.RegisterAdditional event) {
            event.register(STAND_LOCATION);
        }
    }
}
