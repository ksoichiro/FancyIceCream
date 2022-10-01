package com.ksoichiro.mcmod.fancyicecream;

import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RegistryObjectCompat37_1_1<T extends IForgeRegistryEntry<? super T>> implements IRegistryObjectCompat<T> {
    private RegistryObject<T> registryObject;

    public RegistryObjectCompat37_1_1(RegistryObject<T> registryObject) {
        this.registryObject = registryObject;
    }

    @Override
    public T get() {
        return registryObject.get();
    }
}
