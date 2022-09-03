package com.ksoichiro.mcmod.fancyicecream.entity;

import com.ksoichiro.mcmod.fancyicecream.entity.decoration.IceCreamStand;
import com.ksoichiro.mcmod.fancyicecream.entity.decoration.IceCreamStandRenderer;
import com.ksoichiro.mcmod.fancyicecream.main.FancyIceCreamMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class FancyIceCreamModEntityType {
    public static final EntityType<IceCreamStand> ICE_CREAM_STAND = EntityType.Builder
        .<IceCreamStand>of(IceCreamStand::new, MobCategory.MISC)
        .sized(0.5F, 0.5F)
        .clientTrackingRange(10)
        .updateInterval(Integer.MAX_VALUE)
        .build("ice_cream_stand");

    @Mod.EventBusSubscriber(modid = FancyIceCreamMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Registerer {
        @SubscribeEvent
        public static void registerEntityType(final RegistryEvent.Register<EntityType<?>> event) {
            ICE_CREAM_STAND.setRegistryName("ice_cream_stand");

            event.getRegistry().registerAll(ICE_CREAM_STAND);
        }
        @SubscribeEvent
        public static void registerRenderer(final EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(ICE_CREAM_STAND, IceCreamStandRenderer::new);
        }
    }
}
