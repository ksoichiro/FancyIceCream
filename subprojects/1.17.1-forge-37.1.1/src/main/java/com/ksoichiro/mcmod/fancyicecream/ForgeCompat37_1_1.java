package com.ksoichiro.mcmod.fancyicecream;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ForgeCompat37_1_1 implements IForgeCompat {
    private static DeferredRegister<EntityType<?>> ENTITY_TYPE;
    private static ForgeCompat37_1_1 singleton;
    private List<ResourceLocation> models;

    private ForgeCompat37_1_1() {
        ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.ENTITIES, FancyIceCreamModInfo.MOD_ID);
        this.models = new ArrayList<>();
    }

    public static ForgeCompat37_1_1 getInstance() {
        if (singleton == null) {
            singleton = new ForgeCompat37_1_1();
        }
        return singleton;
    }

    @Override
    public void register(IEventBus bus) {
        bus.addListener(ForgeCompat37_1_1::registerModels);
    }

    public static void registerModels(final ModelRegistryEvent event) {
        for (ResourceLocation model : getInstance().models) {
            ModelLoader.addSpecialModel(model);
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
        ItemTagCompat37_1_1 t = new ItemTagCompat37_1_1();
        t.tag = ItemTags.createOptional(new ResourceLocation(FancyIceCreamModInfo.MOD_ID, name));
        return t;
    }
}
