package com.ksoichiro.mcmod.fancyicecream;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ForgeCompat41_0_64 implements IForgeCompat {
    private static DeferredRegister<EntityType<?>> ENTITY_TYPE;
    private static ForgeCompat41_0_64 singleton;
    private List<ResourceLocation> models;

    private ForgeCompat41_0_64() {
        ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.ENTITIES, FancyIceCreamModInfo.MOD_ID);
        this.models = new ArrayList<>();
    }

    public static ForgeCompat41_0_64 getInstance() {
        if (singleton == null) {
            singleton = new ForgeCompat41_0_64();
        }
        return singleton;
    }

    @Override
    public void register(IEventBus bus) {
        bus.addListener(ForgeCompat41_0_64::registerModels);
    }

    public static void registerModels(final ModelEvent.RegisterAdditional event) {
        for (ResourceLocation model : getInstance().models) {
            event.register(model);
        }
    }

    @Override
    public void addModel(ResourceLocation resourceLocation) {
        this.models.add(resourceLocation);
    }

    public DeferredRegister<EntityType<?>> getEntityTypeDeferredRegister() {
        return ENTITY_TYPE;
    }
}
