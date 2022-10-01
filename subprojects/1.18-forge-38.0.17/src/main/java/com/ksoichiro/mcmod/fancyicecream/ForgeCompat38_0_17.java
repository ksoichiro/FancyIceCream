package com.ksoichiro.mcmod.fancyicecream;

import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ForgeCompat38_0_17 implements IForgeCompat<Item, EntityType<?>> {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FancyIceCreamModInfo.MOD_ID);
    private static DeferredRegister<EntityType<?>> ENTITY_TYPE;
    private static ForgeCompat38_0_17 singleton;
    private List<ResourceLocation> models;

    private ForgeCompat38_0_17() {
        ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.ENTITIES, FancyIceCreamModInfo.MOD_ID);
        this.models = new ArrayList<>();
    }

    public static ForgeCompat38_0_17 getInstance() {
        if (singleton == null) {
            singleton = new ForgeCompat38_0_17();
        }
        return singleton;
    }

    @Override
    public void register(IEventBus bus) {
        bus.addListener(ForgeCompat38_0_17::registerModels);
        ITEMS.register(bus);
    }

    public static void registerModels(final ModelRegistryEvent event) {
        for (ResourceLocation model : getInstance().models) {
            ForgeModelBakery.addSpecialModel(model);
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
        ItemTagCompat38_0_17 t = new ItemTagCompat38_0_17();
        t.tag = ItemTags.createOptional(new ResourceLocation(FancyIceCreamModInfo.MOD_ID, name));
        return t;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public IRegistryObjectCompat<Item> registerItem(String name, Supplier<Item> item) {
        return new RegistryObjectCompat38_0_17(ITEMS.register(name, item));
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public IRegistryObjectCompat<EntityType<?>> registerEntityType(String name, Supplier<EntityType<?>> entityType) {
        return new RegistryObjectCompat38_0_17(getEntityTypeDeferredRegister().register(name, entityType));
    }

    @Override
    public Packet<?> getEntitySpawningPacket(Object entity) {
        return NetworkHooks.getEntitySpawningPacket((Entity) entity);
    }
}
