package com.ksoichiro.mcmod.fancyicecream;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

public interface IForgeCompat {
    void register(IEventBus bus);
    void addModel(ResourceLocation resourceLocation);
    DeferredRegister<EntityType<?>> getEntityTypeDeferredRegister();
    IItemTagCompat createItemTag(String name);
}
