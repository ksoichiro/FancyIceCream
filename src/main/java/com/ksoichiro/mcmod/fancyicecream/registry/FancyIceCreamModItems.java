package com.ksoichiro.mcmod.fancyicecream.registry;

import com.ksoichiro.mcmod.fancyicecream.item.*;
import com.ksoichiro.mcmod.fancyicecream.main.FancyIceCreamMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class FancyIceCreamModItems {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FancyIceCreamMod.MOD_ID);

    public static final RegistryObject<Item> VANILLA_ICE_CREAM = registerItem("vanilla_ice_cream", VanillaIceCream::new);
    public static final RegistryObject<Item> APPLE_ICE_CREAM = registerItem("apple_ice_cream", AppleIceCream::new);
    public static final RegistryObject<Item> CHOCO_CHIP_ICE_CREAM = registerItem("choco_chip_ice_cream", ChocoChipIceCream::new);
    public static final RegistryObject<Item> CHOCOLATE_ICE_CREAM = registerItem("chocolate_ice_cream", ChocolateIceCream::new);
    public static final RegistryObject<Item> GLOW_BERRY_ICE_CREAM = registerItem("glow_berry_ice_cream", GlowBerryIceCream::new);
    public static final RegistryObject<Item> GOLDEN_APPLE_ICE_CREAM = registerItem("golden_apple_ice_cream", GoldenAppleIceCream::new);
    public static final RegistryObject<Item> HONEY_ICE_CREAM = registerItem("honey_ice_cream", HoneyIceCream::new);
    public static final RegistryObject<Item> ICE_CREAM_STAND = registerItem("ice_cream_stand", IceCreamStandItem::new);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    private static RegistryObject<Item> registerItem(String name, Supplier<Item> item) {
        return ITEMS.register(name, item);
    }
}
