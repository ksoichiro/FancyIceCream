package com.ksoichiro.mcmod.fancyicecream.entity;

import com.ksoichiro.mcmod.fancyicecream.entity.decoration.IceCreamStand;
import com.ksoichiro.mcmod.fancyicecream.entity.decoration.IceCreamStandRenderer;
import com.ksoichiro.mcmod.fancyicecream.main.FancyIceCreamMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FancyIceCreamModEntityType {
    private static final DeferredRegister<EntityType<?>> ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.ENTITIES, FancyIceCreamMod.MOD_ID);

    public static final RegistryObject<EntityType<IceCreamStand>> ICE_CREAM_STAND = ENTITY_TYPE.register("ice_cream_stand", () ->
        EntityType.Builder
            .<IceCreamStand>of(IceCreamStand::new, MobCategory.MISC)
            .sized(0.5F, 0.5F)
            .clientTrackingRange(10)
            .updateInterval(Integer.MAX_VALUE)
            .build("ice_cream_stand"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPE.register(eventBus);
    }

    @Mod.EventBusSubscriber(modid = FancyIceCreamMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Registerer {
        @SubscribeEvent
        public static void registerModel(final ModelRegistryEvent event) {
            ModelLoader.addSpecialModel(IceCreamStandRenderer.STAND_LOCATION);
        }
        @SubscribeEvent
        public static void registerRenderer(final EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(ICE_CREAM_STAND.get(), IceCreamStandRenderer::new);
        }
    }
}
