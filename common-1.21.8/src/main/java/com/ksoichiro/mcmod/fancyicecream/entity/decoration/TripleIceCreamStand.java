package com.ksoichiro.mcmod.fancyicecream.entity.decoration;

import com.ksoichiro.mcmod.fancyicecream.FancyIceCream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TripleIceCreamStand extends IceCreamStand {
    protected static final EntityDataAccessor<ItemStack> DATA_ITEM2 = SynchedEntityData.defineId(TripleIceCreamStand.class, EntityDataSerializers.ITEM_STACK);
    protected static final EntityDataAccessor<ItemStack> DATA_ITEM3 = SynchedEntityData.defineId(TripleIceCreamStand.class, EntityDataSerializers.ITEM_STACK);

    public TripleIceCreamStand(EntityType<? extends HangingEntity> entityType, Level level) {
        super(entityType, level);
    }

    public TripleIceCreamStand(Level level, BlockPos pos, Direction direction, Direction placedDirection) {
        super(getTripleStandEntityType(), level, pos, direction, placedDirection);
    }

    private static EntityType<? extends HangingEntity> getTripleStandEntityType() {
        return (EntityType<? extends HangingEntity>) BuiltInRegistries.ENTITY_TYPE
                .getValue(ResourceLocation.fromNamespaceAndPath(FancyIceCream.MOD_ID, "triple_ice_cream_stand"));
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
        Item item = BuiltInRegistries.ITEM.getValue(
                ResourceLocation.fromNamespaceAndPath(FancyIceCream.MOD_ID, "triple_ice_cream_stand"));
        return new ItemStack(item);
    }
}
