package com.ksoichiro.mcmod.fancyicecream.entity.decoration;

import com.ksoichiro.mcmod.fancyicecream.entity.FancyIceCreamModEntityType;
import com.ksoichiro.mcmod.fancyicecream.registry.FancyIceCreamModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.HangingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class IceCreamCup extends IceCreamStand {
    public IceCreamCup(EntityType<? extends HangingEntity> entityType, World level) {
        super(entityType, level);
    }

    public IceCreamCup(World level, BlockPos pos, Direction direction, Direction placedDirection) {
        super(FancyIceCreamModEntityType.ICE_CREAM_CUP.get(), level, pos, direction, placedDirection);
    }

    @Override
    public ItemStack getFrameItemStack() {
        return new ItemStack(FancyIceCreamModItems.ICE_CREAM_CUP.get());
    }
}
