package com.ksoichiro.mcmod.fancyicecream;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ForgeCompat41_0_94 implements IForgeCompat {
    private static DeferredRegister<EntityType<?>> ENTITY_TYPE;
    private static ForgeCompat41_0_94 singleton;
    private List<ResourceLocation> models;

    private ForgeCompat41_0_94() {
        ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, FancyIceCreamModInfo.MOD_ID);
        this.models = new ArrayList<>();
    }

    public static ForgeCompat41_0_94 getInstance() {
        if (singleton == null) {
            singleton = new ForgeCompat41_0_94();
        }
        return singleton;
    }

    @Override
    public void register(IEventBus bus) {
        bus.addListener(ForgeCompat41_0_94::registerModels);
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

    @Override
    public DeferredRegister<EntityType<?>> getEntityTypeDeferredRegister() {
        return ENTITY_TYPE;
    }

    @Override
    public IItemTagCompat createItemTag(String name) {
        ItemTagCompat41_0_94 t = new ItemTagCompat41_0_94();
        t.tag = ItemTags.create(new ResourceLocation(FancyIceCreamModInfo.MOD_ID, name));
        t.registry = ForgeRegistries.ITEMS;
        return t;
    }
}
