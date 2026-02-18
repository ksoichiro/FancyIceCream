package com.ksoichiro.mcmod.fancyicecream.neoforge;

import com.ksoichiro.mcmod.fancyicecream.FancyIceCream;
import com.ksoichiro.mcmod.fancyicecream.item.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FancyIceCreamNeoForgeItems {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, FancyIceCream.MOD_ID);

    public static final Supplier<Item> VANILLA_ICE_CREAM = ITEMS.register("vanilla_ice_cream", VanillaIceCream::new);
    public static final Supplier<Item> APPLE_ICE_CREAM = ITEMS.register("apple_ice_cream", AppleIceCream::new);
    public static final Supplier<Item> CHOCO_CHIP_ICE_CREAM = ITEMS.register("choco_chip_ice_cream", ChocoChipIceCream::new);
    public static final Supplier<Item> CHOCOLATE_ICE_CREAM = ITEMS.register("chocolate_ice_cream", ChocolateIceCream::new);
    public static final Supplier<Item> GLOW_BERRY_ICE_CREAM = ITEMS.register("glow_berry_ice_cream", GlowBerryIceCream::new);
    public static final Supplier<Item> GOLDEN_APPLE_ICE_CREAM = ITEMS.register("golden_apple_ice_cream", GoldenAppleIceCream::new);
    public static final Supplier<Item> HONEY_ICE_CREAM = ITEMS.register("honey_ice_cream", HoneyIceCream::new);
    public static final Supplier<Item> SWEET_BERRY_ICE_CREAM = ITEMS.register("sweet_berry_ice_cream", SweetBerryIceCream::new);
    public static final Supplier<Item> ICE_CREAM_STAND = ITEMS.register("ice_cream_stand", IceCreamStandItem::new);
    public static final Supplier<Item> TRIPLE_ICE_CREAM_STAND = ITEMS.register("triple_ice_cream_stand", TripleIceCreamStandItem::new);

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
