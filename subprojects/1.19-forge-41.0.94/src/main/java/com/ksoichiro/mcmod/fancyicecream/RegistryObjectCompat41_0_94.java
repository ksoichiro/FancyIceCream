package com.ksoichiro.mcmod.fancyicecream;

import net.minecraftforge.registries.RegistryObject;

public class RegistryObjectCompat41_0_94<T> implements IRegistryObjectCompat<T> {
    private RegistryObject<T> registryObject;

    public RegistryObjectCompat41_0_94(RegistryObject<T> registryObject) {
        this.registryObject = registryObject;
    }

    @Override
    public T get() {
        return registryObject.get();
    }
}
