package com.ksoichiro.mcmod.fancyicecream.entity.decoration;

import com.ksoichiro.mcmod.fancyicecream.common.Tag;
import com.ksoichiro.mcmod.fancyicecream.entity.FancyIceCreamModEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class IceCreamStand extends ItemFrame {
    public static final Tag<Item> ICE_CREAM_TAG = Tag.createItemTag("ice_cream");
    public static final RegistryObject<Item> ICE_CREAM_STAND = RegistryObject.create(new ResourceLocation("fancyicecreammod:ice_cream_stand"), ForgeRegistries.ITEMS);

    private static final EntityDataAccessor<Integer> DATA_ROTATION = SynchedEntityData.defineId(IceCreamStand.class, EntityDataSerializers.INT);

    public IceCreamStand(EntityType<IceCreamStand> standEntityType, Level level) {
        super(FancyIceCreamModEntityType.ICE_CREAM_STAND.get(), level);
    }

    public IceCreamStand(Level level, BlockPos pos, Direction direction, Direction placedDirection) {
        super(FancyIceCreamModEntityType.ICE_CREAM_STAND.get(), level, pos, direction);

        // Set base rotation from direction
        int rotation = switch (placedDirection) {
            case SOUTH -> 4;
            case WEST -> 2;
            case EAST -> 6;
            default -> 0;
        };
        this.setRotation(rotation);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_ROTATION, 0);
    }

    @Override
    public int getRotation() {
        return this.getEntityData().get(DATA_ROTATION);
    }

    @Override
    public void setRotation(int rotation) {
        this.getEntityData().set(DATA_ROTATION, rotation % 8);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        // Do not support
        compoundTag.remove("ItemDropChance");
        compoundTag.remove("Invisible");
        compoundTag.remove("Fixed");
        // Always use
        compoundTag.putByte("ItemRotation", (byte)this.getRotation());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        // Always use
        this.setRotation(compoundTag.getByte("ItemRotation"));
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        boolean isStandEmpty = this.getItem().isEmpty();
        boolean hasItemInHand = !itemstack.isEmpty();
        if (!this.level.isClientSide) {
            boolean isIceCream = ICE_CREAM_TAG.contains(itemstack.getItem());
            if (isStandEmpty && hasItemInHand && !this.isRemoved() && isIceCream) {
                this.setItem(itemstack);
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
            } else {
                this.playSound(this.getRotateItemSound(), 1.0F, 1.0F);
                this.setRotation(this.getRotation() + 1);
            }

            return InteractionResult.CONSUME;
        } else {
            return isStandEmpty && !hasItemInHand ? InteractionResult.PASS : InteractionResult.SUCCESS;
        }
    }

    @Override
    public int getWidth() {
        return 6;
    }

    @Override
    public int getHeight() {
        return 6;
    }

    @Override
    public ItemStack getFrameItemStack() {
        return new ItemStack(ICE_CREAM_STAND.get());
    }
}
