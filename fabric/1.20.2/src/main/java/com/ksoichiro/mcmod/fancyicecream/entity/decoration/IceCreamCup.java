package com.ksoichiro.mcmod.fancyicecream.entity.decoration;

import com.ksoichiro.mcmod.fancyicecream.FancyIceCream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class IceCreamCup extends IceCreamStand {
    public IceCreamCup(EntityType<? extends HangingEntity> entityType, Level level) {
        super(entityType, level);
    }

    public IceCreamCup(Level level, BlockPos pos, Direction direction, Direction placedDirection) {
        super((EntityType<? extends HangingEntity>) BuiltInRegistries.ENTITY_TYPE
                .get(new ResourceLocation(FancyIceCream.MOD_ID, "ice_cream_cup")),
                level, pos, direction, placedDirection);
    }

    @Override
    public ItemStack getFrameItemStack() {
        Item item = BuiltInRegistries.ITEM.get(
                new ResourceLocation(FancyIceCream.MOD_ID, "ice_cream_cup"));
        return new ItemStack(item);
    }
}
