package com.ksoichiro.mcmod.fancyicecream;

import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryObject;

public class RegistryObjectCompat40_1_73<T extends IForgeRegistryEntry<? super T>> implements IRegistryObjectCompat<T> {
    private RegistryObject<T> registryObject;

    public RegistryObjectCompat40_1_73(RegistryObject<T> registryObject) {
        this.registryObject = registryObject;
    }

    @Override
    public T get() {
        return registryObject.get();
    }
}
