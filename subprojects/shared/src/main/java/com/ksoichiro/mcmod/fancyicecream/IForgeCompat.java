package com.ksoichiro.mcmod.fancyicecream;

import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Supplier;

public interface IForgeCompat<T, E> {
    void register(IEventBus bus);
    void addModel(ResourceLocation resourceLocation);
    DeferredRegister<EntityType<?>> getEntityTypeDeferredRegister();
    IItemTagCompat createItemTag(String name);
    IRegistryObjectCompat<T> registerItem(String name, Supplier<T> item);
    IRegistryObjectCompat<E> registerEntityType(String name, Supplier<E> entityType);
    Packet<?> getEntitySpawningPacket(Object entity);
}
