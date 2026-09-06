package com.ksoichiro.mcmod.fancyicecream.item;

import com.ksoichiro.mcmod.fancyicecream.FancyIceCream;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

public class ItemProperties extends Item.Properties {
    public ItemProperties(String itemName) {
        this.setId(ResourceKey.create(Registries.ITEM,
            Identifier.fromNamespaceAndPath(FancyIceCream.MOD_ID, itemName)));
    }

    public Item.Properties foodBuilder(FoodBuilder foodBuilder) {
        this.food(foodBuilder.build());

        // Add consumable component if effects are present
        if (foodBuilder.hasEffects()) {
            this.component(DataComponents.CONSUMABLE, foodBuilder.buildConsumableComponent());
        }

        return this;
    }
}
