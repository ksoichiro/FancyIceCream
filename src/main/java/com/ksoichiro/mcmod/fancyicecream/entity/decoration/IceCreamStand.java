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
import net.minecraft.tags.ItemTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class IceCreamStand extends ItemFrameEntity implements IEntityAdditionalSpawnData {
    public static final Tag<Item> ICE_CREAM_TAG = Tag.createItemTag("ice_cream");
    private static final DataParameter<Integer> DATA_ROTATION = EntityDataManager.defineId(IceCreamStand.class, DataSerializers.INT);

    public IceCreamStand(EntityType<IceCreamStand> standEntityType, World level) {
        super(FancyIceCreamModEntityType.ICE_CREAM_STAND.get(), level);
    }

    public IceCreamStand(World level, BlockPos pos, Direction direction, Direction placedDirection) {
        super(FancyIceCreamModEntityType.ICE_CREAM_STAND.get(), level);
        this.pos = pos;
        this.setDirection(direction);

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
        this.setRotation(rotation, true);
    }

    private void setRotation(int rotation, boolean p_174865_2_) {
        this.getEntityData().set(DATA_ROTATION, rotation % 8);
        if (p_174865_2_ && this.pos != null) {
            this.level.updateNeighbourForOutputSignal(this.pos, Blocks.AIR);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        // Do not support
        compoundTag.remove("ItemDropChance");
        compoundTag.remove("Invisible");
        compoundTag.remove("Fixed");
        // Always use
        compoundTag.putByte("ItemRotation", (byte)this.getRotation());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        // Always use
        // Without 2nd parameter, this is going to be stuck.
        this.setRotation(compoundTag.getByte("ItemRotation"), false);
    }

    @Override
    public ActionResultType interact(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        boolean isStandEmpty = this.getItem().isEmpty();
        boolean hasItemInHand = !itemstack.isEmpty();
        if (!this.level.isClientSide) {
            boolean isIceCream = ICE_CREAM_TAG.contains(itemstack.getItem());
            if (isStandEmpty && hasItemInHand && this.isAlive() && isIceCream) {
                this.setItem(itemstack);
                if (!player.abilities.instabuild) {
                    itemstack.shrink(1);
                }
            } else {
                this.playSound(SoundEvents.ITEM_FRAME_ROTATE_ITEM, 1.0F, 1.0F);
                this.setRotation(this.getRotation() + 1);
            }

            return ActionResultType.CONSUME;
        } else {
            return isStandEmpty && !hasItemInHand ? ActionResultType.PASS : ActionResultType.SUCCESS;
        }
    }

    public boolean hurt(DamageSource damageSource, float p_70097_2_) {
        if (this.isInvulnerableTo(damageSource)) {
            return false;
        } else if (!damageSource.isExplosion() && !this.getItem().isEmpty()) {
            if (!this.level.isClientSide) {
                this.dropItem(damageSource.getEntity(), false);
                this.playSound(SoundEvents.ITEM_FRAME_REMOVE_ITEM, 1.0F, 1.0F);
            }

            return true;
        } else {
            return super.hurt(damageSource, p_70097_2_);
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

    public void dropItem(@Nullable Entity entity) {
        this.playSound(SoundEvents.ITEM_FRAME_BREAK, 1.0F, 1.0F);
        this.dropItem(entity, true);
    }

    private void dropItem(@Nullable Entity entity, boolean p_146065_2_) {
        ItemStack itemstack = this.getItem();
        this.setItem(ItemStack.EMPTY);
        if (!this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
            if (entity == null) {
                this.removeFramedMap(itemstack);
            }
        } else {
            if (entity instanceof PlayerEntity) {
                PlayerEntity playerentity = (PlayerEntity)entity;
                if (playerentity.abilities.instabuild) {
                    this.removeFramedMap(itemstack);
                    return;
                }
            }

            if (p_146065_2_) {
                this.spawnAtLocation(new ItemStack(FancyIceCreamModItems.ICE_CREAM_STAND.get()));
            }

            if (!itemstack.isEmpty()) {
                itemstack = itemstack.copy();
                this.removeFramedMap(itemstack);
                this.spawnAtLocation(itemstack);
            }

        }
    }

    private void removeFramedMap(ItemStack itemStack) {
        itemStack.setEntityRepresentation((Entity)null);
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        buffer.writeInt(this.direction.get3DDataValue());
    }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        this.setDirection(Direction.from3DDataValue(additionalData.readInt()));
    }
}
