package com.ksoichiro.mcmod.fancyicecream.entity.decoration;

import com.ksoichiro.mcmod.fancyicecream.entity.FancyIceCreamModEntityType;
import com.ksoichiro.mcmod.fancyicecream.registry.FancyIceCreamModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TripleIceCreamStand extends IceCreamStand {
    protected static final EntityDataAccessor<ItemStack> DATA_ITEM2 = SynchedEntityData.defineId(TripleIceCreamStand.class, EntityDataSerializers.ITEM_STACK);
    protected static final EntityDataAccessor<ItemStack> DATA_ITEM3 = SynchedEntityData.defineId(TripleIceCreamStand.class, EntityDataSerializers.ITEM_STACK);

    public TripleIceCreamStand(EntityType<? extends HangingEntity> entityType, Level level) {
        super(entityType, level);
    }

    public TripleIceCreamStand(Level level, BlockPos pos, Direction direction, Direction placedDirection) {
        super(FancyIceCreamModEntityType.TRIPLE_ICE_CREAM_STAND.get(), level, pos, direction, placedDirection);
    }

    protected int getMaxHoldableItems() {
        return 3;
    }

    protected EntityDataAccessor<ItemStack> getItemEntityDataAccessor(int slot) {
        return switch (slot) {
            case 0 -> DATA_ITEM1;
            case 1 -> DATA_ITEM2;
            case 2 -> DATA_ITEM3;
            default -> null;
        };
    }

    @Override
    public ItemStack getFrameItemStack() {
        return new ItemStack(FancyIceCreamModItems.TRIPLE_ICE_CREAM_STAND.get());
    }
}
