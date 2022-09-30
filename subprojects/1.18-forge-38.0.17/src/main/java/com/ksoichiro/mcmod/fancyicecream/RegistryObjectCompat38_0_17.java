package com.ksoichiro.mcmod.fancyicecream;

import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryObject;

public class RegistryObjectCompat38_0_17<T extends IForgeRegistryEntry<? super T>> implements IRegistryObjectCompat<T> {
    private RegistryObject<T> registryObject;

    public RegistryObjectCompat38_0_17(RegistryObject<T> registryObject) {
        this.registryObject = registryObject;
    }

    @Override
    public T get() {
        return registryObject.get();
    }
}
