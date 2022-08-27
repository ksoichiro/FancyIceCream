package com.ksoichiro.mcmod.fancyicecream.entity;

import com.ksoichiro.mcmod.fancyicecream.entity.decoration.Stand;
import com.ksoichiro.mcmod.fancyicecream.entity.decoration.StandRenderer;
import com.ksoichiro.mcmod.fancyicecream.main.FancyIceCreamMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;


@ObjectHolder(FancyIceCreamMod.MOD_ID)
public class FancyIceCreamModEntityType {
    public static final EntityType<Stand> STAND = EntityType.Builder
        .<Stand>of(Stand::new, MobCategory.MISC)
        .sized(0.5F, 0.5F)
        .clientTrackingRange(10)
        .updateInterval(Integer.MAX_VALUE)
        .build("stand");

    @Mod.EventBusSubscriber(modid = FancyIceCreamMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Registerer {
        @SubscribeEvent
        public static void registerEntityType(final RegistryEvent.Register<EntityType<?>> event) {
            STAND.setRegistryName("stand");

            event.getRegistry().registerAll(STAND);
        }
        @SubscribeEvent
        public static void registerRenderer(final EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(STAND, StandRenderer::new);
        }
    }
}