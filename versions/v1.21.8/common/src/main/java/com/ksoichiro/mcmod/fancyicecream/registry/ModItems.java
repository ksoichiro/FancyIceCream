package com.ksoichiro.mcmod.fancyicecream.registry;

import com.ksoichiro.mcmod.fancyicecream.item.*;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;

/**
 * Minecraft 1.21.8 item registry using Architectury API.
 * Temporarily simplified for dependency testing.
 */
public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create("fancyicecream", Registries.ITEM);

    // Ice cream items
    public static final RegistrySupplier<Item> VANILLA_ICE_CREAM =
            ITEMS.register("vanilla_ice_cream", VanillaIceCream::new);

    // Entity items will be added when entities are implemented

    public static void init() {
        ITEMS.register();
    }
}