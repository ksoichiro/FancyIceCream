package com.ksoichiro.mcmod.fancyicecream.fabric;

import com.ksoichiro.mcmod.fancyicecream.FancyIceCream;
import com.ksoichiro.mcmod.fancyicecream.entity.decoration.IceCreamStand;
import com.ksoichiro.mcmod.fancyicecream.entity.decoration.IceCreamStandRenderer;
import com.ksoichiro.mcmod.fancyicecream.entity.decoration.TripleIceCreamStand;
import com.ksoichiro.mcmod.fancyicecream.entity.decoration.TripleIceCreamStandRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class FancyIceCreamFabricEntityType {
    public static EntityType<IceCreamStand> ICE_CREAM_STAND;
    public static EntityType<TripleIceCreamStand> TRIPLE_ICE_CREAM_STAND;

    public static void register() {
        ICE_CREAM_STAND = Registry.register(BuiltInRegistries.ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(FancyIceCream.MOD_ID, "ice_cream_stand"),
                EntityType.Builder
                        .<IceCreamStand>of(IceCreamStand::new, MobCategory.MISC)
                        .sized(0.5F, 0.5F)
                        .clientTrackingRange(10)
                        .updateInterval(Integer.MAX_VALUE)
                        .build("ice_cream_stand"));

        TRIPLE_ICE_CREAM_STAND = Registry.register(BuiltInRegistries.ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(FancyIceCream.MOD_ID, "triple_ice_cream_stand"),
                EntityType.Builder
                        .<TripleIceCreamStand>of(TripleIceCreamStand::new, MobCategory.MISC)
                        .sized(0.5F, 0.5F)
                        .clientTrackingRange(10)
                        .updateInterval(Integer.MAX_VALUE)
                        .build("triple_ice_cream_stand"));

        EntityRendererRegistry.register(ICE_CREAM_STAND, IceCreamStandRenderer::new);
        EntityRendererRegistry.register(TRIPLE_ICE_CREAM_STAND, TripleIceCreamStandRenderer::new);
    }
}
