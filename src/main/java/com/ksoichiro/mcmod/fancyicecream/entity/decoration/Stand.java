package com.ksoichiro.mcmod.fancyicecream.entity.decoration;

import com.ksoichiro.mcmod.fancyicecream.entity.FancyIceCreamModEntityType;
import com.ksoichiro.mcmod.fancyicecream.registry.FancyIceCreamModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Stand extends ItemFrame {
    public Stand(EntityType<Stand> standEntityType, Level level) {
        super(FancyIceCreamModEntityType.STAND, level);
    }

    public Stand(Level level, BlockPos pos, Direction direction) {
        super(FancyIceCreamModEntityType.STAND, level, pos, direction);
    }

    @Override
    public ItemStack getFrameItemStack() {
        return new ItemStack(FancyIceCreamModItems.STAND);
    }
}
