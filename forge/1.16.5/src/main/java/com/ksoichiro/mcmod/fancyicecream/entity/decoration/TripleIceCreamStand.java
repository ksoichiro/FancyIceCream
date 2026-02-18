package com.ksoichiro.mcmod.fancyicecream.entity.decoration;

import com.ksoichiro.mcmod.fancyicecream.entity.FancyIceCreamModEntityType;
import com.ksoichiro.mcmod.fancyicecream.registry.FancyIceCreamModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.HangingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TripleIceCreamStand extends IceCreamStand {
    protected static final DataParameter<ItemStack> DATA_ITEM2 = EntityDataManager.defineId(TripleIceCreamStand.class, DataSerializers.ITEM_STACK);
    protected static final DataParameter<ItemStack> DATA_ITEM3 = EntityDataManager.defineId(TripleIceCreamStand.class, DataSerializers.ITEM_STACK);

    public TripleIceCreamStand(EntityType<? extends HangingEntity> entityType, World level) {
        super(entityType, level);
    }

    public TripleIceCreamStand(World level, BlockPos pos, Direction direction, Direction placedDirection) {
        super(FancyIceCreamModEntityType.TRIPLE_ICE_CREAM_STAND.get(), level, pos, direction, placedDirection);
    }

    protected int getMaxHoldableItems() {
        return 3;
    }

    protected DataParameter<ItemStack> getItemEntityDataAccessor(int slot) {
        switch (slot) {
            case 0:
                return DATA_ITEM1;
            case 1:
                return DATA_ITEM2;
            case 2:
                return DATA_ITEM3;
            default:
                return null;
        }
    }

    @Override
    public ItemStack getFrameItemStack() {
        return new ItemStack(FancyIceCreamModItems.TRIPLE_ICE_CREAM_STAND.get());
    }
}
