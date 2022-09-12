package com.ksoichiro.mcmod.fancyicecream.entity.decoration;

import com.ksoichiro.mcmod.fancyicecream.common.Tag;
import com.ksoichiro.mcmod.fancyicecream.entity.FancyIceCreamModEntityType;
import com.ksoichiro.mcmod.fancyicecream.registry.FancyIceCreamModItems;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class IceCreamStand extends ItemFrameEntity implements IEntityAdditionalSpawnData {
    public static final Tag<Item> ICE_CREAM_TAG = Tag.createItemTag("ice_cream");
    private static final DataParameter<Integer> DATA_ROTATION = EntityDataManager.createKey(IceCreamStand.class, DataSerializers.VARINT);

    public IceCreamStand(EntityType<IceCreamStand> standEntityType, World level) {
        super(FancyIceCreamModEntityType.ICE_CREAM_STAND.get(), level);
    }

    public IceCreamStand(World level, BlockPos pos, Direction direction, Direction placedDirection) {
        super(FancyIceCreamModEntityType.ICE_CREAM_STAND.get(), level);
        this.hangingPosition = pos;
        this.updateFacingWithBoundingBox(direction);

        // Set base rotation from direction
        int rotation;
        switch (placedDirection) {
            case SOUTH:
                rotation = 4;
                break;
            case WEST:
                rotation = 2;
                break;
            case EAST:
                rotation = 6;
                break;
            default:
                rotation = 0;
                break;
        };
        this.setItemRotation(rotation);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.getDataManager().register(DATA_ROTATION, 0);
    }

    @Override
    public int getRotation() {
        return this.getDataManager().get(DATA_ROTATION);
    }

    @Override
    public void setItemRotation(int rotation) {
        this.setRotation(rotation, true);
    }

    private void setRotation(int rotation, boolean p_174865_2_) {
        this.getDataManager().set(DATA_ROTATION, rotation % 8);
        if (p_174865_2_ && this.hangingPosition != null) {
            this.world.updateComparatorOutputLevel(this.hangingPosition, Blocks.AIR);
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compoundTag) {
        super.writeAdditional(compoundTag);
        // Do not support
        compoundTag.remove("ItemDropChance");
        compoundTag.remove("Invisible");
        compoundTag.remove("Fixed");
        // Always use
        compoundTag.putByte("ItemRotation", (byte)this.getRotation());
    }

    @Override
    public void readAdditional(CompoundNBT compoundTag) {
        super.readAdditional(compoundTag);
        // Always use
        // Without 2nd parameter, this is going to be stuck.
        this.setRotation(compoundTag.getByte("ItemRotation"), false);
    }

    @Override
    public ActionResultType processInitialInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        boolean isStandEmpty = this.getDisplayedItem().isEmpty();
        boolean hasItemInHand = !itemstack.isEmpty();
        if (!this.world.isRemote) {
            boolean isIceCream = ICE_CREAM_TAG.contains(itemstack.getItem()); // TODO ???
            if (isStandEmpty && hasItemInHand && this.isAlive() && isIceCream) {
                this.setDisplayedItem(itemstack);
                if (!player.abilities.isCreativeMode) {
                    itemstack.shrink(1);
                }
            } else {
                this.playSound(SoundEvents.ENTITY_ITEM_FRAME_ROTATE_ITEM, 1.0F, 1.0F);
                this.setItemRotation(this.getRotation() + 1);
            }

            return ActionResultType.CONSUME;
        } else {
            return isStandEmpty && !hasItemInHand ? ActionResultType.PASS : ActionResultType.SUCCESS;
        }
    }

    public boolean attackEntityFrom(DamageSource damageSource, float amount) {
        if (this.isInvulnerableTo(damageSource)) {
            return false;
        } else if (!damageSource.isExplosion() && !this.getDisplayedItem().isEmpty()) {
            if (!this.world.isRemote) {
                this.dropItemOrSelf(damageSource.getTrueSource(), false);
                this.playSound(SoundEvents.ENTITY_ITEM_FRAME_REMOVE_ITEM, 1.0F, 1.0F);
            }

            return true;
        } else {
            return super.attackEntityFrom(damageSource, amount);
        }
    }

    @Override
    public int getWidthPixels() {
        return 6;
    }

    @Override
    public int getHeightPixels() {
        return 6;
    }

    public void onBroken(@Nullable Entity entity) {
        this.playSound(SoundEvents.ENTITY_ITEM_FRAME_BREAK, 1.0F, 1.0F);
        this.dropItemOrSelf(entity, true);
    }

    private void dropItemOrSelf(@Nullable Entity entity, boolean p_146065_2_) {
        ItemStack itemstack = this.getDisplayedItem();
        this.setDisplayedItem(ItemStack.EMPTY);
        if (!this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
            if (entity == null) {
                this.removeFramedMap(itemstack);
            }
        } else {
            if (entity instanceof PlayerEntity) {
                PlayerEntity playerentity = (PlayerEntity)entity;
                if (playerentity.abilities.isCreativeMode) {
                    this.removeFramedMap(itemstack);
                    return;
                }
            }

            if (p_146065_2_) {
                this.entityDropItem(FancyIceCreamModItems.ICE_CREAM_STAND.get());
            }

            if (!itemstack.isEmpty()) {
                itemstack = itemstack.copy();
                this.removeFramedMap(itemstack);
                this.entityDropItem(itemstack);
            }

        }
    }

    private void removeFramedMap(ItemStack itemStack) {
        itemStack.func_234695_a_((Entity)null);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        buffer.writeInt(this.facingDirection.getIndex());
    }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        this.updateFacingWithBoundingBox(Direction.byIndex(additionalData.readInt()));
    }
}
