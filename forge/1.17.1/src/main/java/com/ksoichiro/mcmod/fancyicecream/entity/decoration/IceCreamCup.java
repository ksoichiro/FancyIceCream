package com.ksoichiro.mcmod.fancyicecream.entity.decoration;

import com.ksoichiro.mcmod.fancyicecream.entity.FancyIceCreamModEntityType;
import com.ksoichiro.mcmod.fancyicecream.registry.FancyIceCreamModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class IceCreamCup extends IceCreamStand {
    public IceCreamCup(EntityType<? extends HangingEntity> entityType, Level level) {
        super(entityType, level);
    }

    public IceCreamCup(Level level, BlockPos pos, Direction direction, Direction placedDirection) {
        super(FancyIceCreamModEntityType.ICE_CREAM_CUP.get(), level, pos, direction, placedDirection);
    }

    @Override
    public ItemStack getFrameItemStack() {
        return new ItemStack(FancyIceCreamModItems.ICE_CREAM_CUP.get());
    }
}
