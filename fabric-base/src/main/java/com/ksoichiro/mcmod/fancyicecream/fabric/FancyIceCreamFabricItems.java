package com.ksoichiro.mcmod.fancyicecream.fabric;

import com.ksoichiro.mcmod.fancyicecream.FancyIceCream;
import com.ksoichiro.mcmod.fancyicecream.item.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public class FancyIceCreamFabricItems {
    public static Item VANILLA_ICE_CREAM;
    public static Item APPLE_ICE_CREAM;
    public static Item CHOCO_CHIP_ICE_CREAM;
    public static Item CHOCOLATE_ICE_CREAM;
    public static Item GLOW_BERRY_ICE_CREAM;
    public static Item GOLDEN_APPLE_ICE_CREAM;
    public static Item HONEY_ICE_CREAM;
    public static Item SWEET_BERRY_ICE_CREAM;
    public static Item ICE_CREAM_STAND;
    public static Item TRIPLE_ICE_CREAM_STAND;

    public static void register() {
        VANILLA_ICE_CREAM = registerItem("vanilla_ice_cream", VanillaIceCream::new);
        APPLE_ICE_CREAM = registerItem("apple_ice_cream", AppleIceCream::new);
        CHOCO_CHIP_ICE_CREAM = registerItem("choco_chip_ice_cream", ChocoChipIceCream::new);
        CHOCOLATE_ICE_CREAM = registerItem("chocolate_ice_cream", ChocolateIceCream::new);
        GLOW_BERRY_ICE_CREAM = registerItem("glow_berry_ice_cream", GlowBerryIceCream::new);
        GOLDEN_APPLE_ICE_CREAM = registerItem("golden_apple_ice_cream", GoldenAppleIceCream::new);
        HONEY_ICE_CREAM = registerItem("honey_ice_cream", HoneyIceCream::new);
        SWEET_BERRY_ICE_CREAM = registerItem("sweet_berry_ice_cream", SweetBerryIceCream::new);
        ICE_CREAM_STAND = registerItem("ice_cream_stand", IceCreamStandItem::new);
        TRIPLE_ICE_CREAM_STAND = registerItem("triple_ice_cream_stand", TripleIceCreamStandItem::new);
    }

    private static Item registerItem(String name, Function<String, Item> factory) {
        return Registry.register(BuiltInRegistries.ITEM,
                ResourceLocation.fromNamespaceAndPath(FancyIceCream.MOD_ID, name),
                factory.apply(name));
    }
}
