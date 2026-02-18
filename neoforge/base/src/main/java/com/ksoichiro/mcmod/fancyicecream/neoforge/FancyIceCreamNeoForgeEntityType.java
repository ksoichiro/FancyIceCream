package com.ksoichiro.mcmod.fancyicecream.neoforge;

import com.ksoichiro.mcmod.fancyicecream.FancyIceCream;
import com.ksoichiro.mcmod.fancyicecream.entity.decoration.IceCreamStand;
import com.ksoichiro.mcmod.fancyicecream.entity.decoration.IceCreamStandRenderer;
import com.ksoichiro.mcmod.fancyicecream.entity.decoration.TripleIceCreamStand;
import com.ksoichiro.mcmod.fancyicecream.entity.decoration.TripleIceCreamStandRenderer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class FancyIceCreamNeoForgeEntityType {
    private static final DeferredRegister<EntityType<?>> ENTITY_TYPE = DeferredRegister.create(Registries.ENTITY_TYPE, FancyIceCream.MOD_ID);

    public static final Supplier<EntityType<IceCreamStand>> ICE_CREAM_STAND = ENTITY_TYPE.register("ice_cream_stand", () ->
        EntityType.Builder
            .<IceCreamStand>of(IceCreamStand::new, MobCategory.MISC)
            .sized(0.5F, 0.5F)
            .clientTrackingRange(10)
            .updateInterval(Integer.MAX_VALUE)
            .build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(FancyIceCream.MOD_ID, "ice_cream_stand"))));

    public static final Supplier<EntityType<TripleIceCreamStand>> TRIPLE_ICE_CREAM_STAND = ENTITY_TYPE.register("triple_ice_cream_stand", () ->
        EntityType.Builder
            .<TripleIceCreamStand>of(TripleIceCreamStand::new, MobCategory.MISC)
            .sized(0.5F, 0.5F)
            .clientTrackingRange(10)
            .updateInterval(Integer.MAX_VALUE)
            .build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(FancyIceCream.MOD_ID, "triple_ice_cream_stand"))));

    public static void register(IEventBus modEventBus) {
        ENTITY_TYPE.register(modEventBus);
        modEventBus.addListener(FancyIceCreamNeoForgeEntityType::registerRenderer);
    }

    private static void registerRenderer(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ICE_CREAM_STAND.get(), IceCreamStandRenderer::new);
        event.registerEntityRenderer(TRIPLE_ICE_CREAM_STAND.get(), TripleIceCreamStandRenderer::new);
    }
}
