package com.ksoichiro.mcmod.fancyicecream.entity.decoration;

import com.ksoichiro.mcmod.fancyicecream.entity.FancyIceCreamModEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TripleIceCreamStand extends IceCreamStand {
    public static final RegistryObject<Item> TRIPLE_ICE_CREAM_STAND = RegistryObject.create(new ResourceLocation("fancyicecreammod:triple_ice_cream_stand"), ForgeRegistries.ITEMS);


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
        return new ItemStack(TRIPLE_ICE_CREAM_STAND.get());
    }
}
