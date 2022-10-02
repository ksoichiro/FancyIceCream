package com.ksoichiro.mcmod.fancyicecream;

import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fmllegacy.network.NetworkHooks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ForgeCompat37_1_1 implements IForgeCompat {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FancyIceCreamModInfo.MOD_ID);
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
        ITEMS.register(bus);
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

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public IRegistryObjectCompat<Item> registerItem(String name, Supplier item) {
        return new RegistryObjectCompat37_1_1(ITEMS.register(name, item));
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public IRegistryObjectCompat<EntityType<?>> registerEntityType(String name, Supplier entityType) {
        return new RegistryObjectCompat37_1_1(getEntityTypeDeferredRegister().register(name, entityType));
    }

    @Override
    public Packet<?> getEntitySpawningPacket(Object entity) {
        return NetworkHooks.getEntitySpawningPacket((Entity) entity);
    }
}
