package com.ksoichiro.mcmod.fancyicecream.entity.decoration;

import com.ksoichiro.mcmod.fancyicecream.common.Tag;
import com.ksoichiro.mcmod.fancyicecream.entity.FancyIceCreamModEntityType;
import com.ksoichiro.mcmod.fancyicecream.registry.FancyIceCreamModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneDiodeBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.HangingEntity;
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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class IceCreamStand extends HangingEntity implements IEntityAdditionalSpawnData {
    private static final Logger LOGGER = LogManager.getLogger();
    protected static final Tag<Item> ICE_CREAM_TAG = Tag.createItemTag("ice_cream");

    private static final DataParameter<Integer> DATA_ROTATION = EntityDataManager.defineId(IceCreamStand.class, DataSerializers.INT);
    protected static final DataParameter<ItemStack> DATA_ITEM1 = EntityDataManager.defineId(IceCreamStand.class, DataSerializers.ITEM_STACK);

    public IceCreamStand(EntityType<? extends HangingEntity> entityType, World level) {
        super(entityType, level);
    }

    public IceCreamStand(World level, BlockPos pos, Direction direction, Direction placedDirection) {
        this(FancyIceCreamModEntityType.ICE_CREAM_STAND.get(), level, pos, direction, placedDirection);
    }

    public IceCreamStand(EntityType<? extends HangingEntity> entityType, World level, BlockPos pos, Direction direction, Direction placedDirection) {
        super(entityType, level, pos);
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

    protected int getMaxHoldableItems() {
        return 1;
    }

    protected DataParameter<ItemStack> getItemEntityDataAccessor(int slot) {
        if (slot == 0) {
            return DATA_ITEM1;
        }
        return null;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_ROTATION, 0);
        for (int i = 0; i < getMaxHoldableItems(); i++) {
            this.getEntityData().define(getItemEntityDataAccessor(i), ItemStack.EMPTY);
        }
    }

    protected void setDirection(Direction p_174859_1_) {
        Validate.notNull(p_174859_1_);
        this.direction = p_174859_1_;
        if (p_174859_1_.getAxis().isHorizontal()) {
            this.xRot = 0.0F;
            this.yRot = (float)(this.direction.get2DDataValue() * 90);
        } else {
            this.xRot = (float)(-90 * p_174859_1_.getAxisDirection().getStep());
            this.yRot = 0.0F;
        }

        this.xRotO = this.xRot;
        this.yRotO = this.yRot;
        this.recalculateBoundingBox();
    }

    protected void recalculateBoundingBox() {
        if (this.direction != null) {
            double d0 = 0.46875D;
            double d1 = (double)this.pos.getX() + 0.5D - (double)this.direction.getStepX() * 0.46875D;
            double d2 = (double)this.pos.getY() + 0.5D - (double)this.direction.getStepY() * 0.46875D;
            double d3 = (double)this.pos.getZ() + 0.5D - (double)this.direction.getStepZ() * 0.46875D;
            this.setPosRaw(d1, d2, d3);
            double d4 = (double)this.getWidth();
            double d5 = (double)this.getHeight();
            double d6 = (double)this.getWidth();
            Direction.Axis direction$axis = this.direction.getAxis();
            switch (direction$axis) {
                case X:
                    d4 = 1.0D;
                    break;
                case Y:
                    d5 = 1.0D;
                    break;
                case Z:
                    d6 = 1.0D;
            }

            d4 /= 32.0D;
            d5 /= 32.0D;
            d6 /= 32.0D;
            this.setBoundingBox(new AxisAlignedBB(d1 - d4, d2 - d5, d3 - d6, d1 + d4, d2 + d5, d3 + d6));
        }
    }

    public boolean survives() {
        if (!this.level.noCollision(this)) {
            return false;
        } else {
            BlockState blockstate = this.level.getBlockState(this.pos.relative(this.direction.getOpposite()));
            return blockstate.getMaterial().isSolid() || this.direction.getAxis().isHorizontal() && RedstoneDiodeBlock.isDiode(blockstate) ? this.level.getEntities(this, this.getBoundingBox(), HANGING_ENTITY).isEmpty() : false;
        }
    }

    public float getPickRadius() {
        return 0.0F;
    }

    public int getRotation() {
        return this.getEntityData().get(DATA_ROTATION);
    }

    public void setRotation(int rotation) {
        this.getEntityData().set(DATA_ROTATION, rotation % 8);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putByte("Facing", (byte)this.direction.get3DDataValue());
        // Always use
        compoundTag.putByte("ItemRotation", (byte)this.getRotation());
        for (int i = 0; i < this.getMaxHoldableItems(); i++) {
            if (!this.getItem(i).isEmpty()) {
                compoundTag.put("Item" + (i + 1), this.getItem(i).save(new CompoundNBT()));
            }
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        for (int i = 0; i < this.getMaxHoldableItems(); i++) {
            CompoundNBT compoundtag = compoundTag.getCompound("Item" + (i + 1));
            if (compoundtag != null && !compoundtag.isEmpty()) {
                ItemStack itemstack = ItemStack.of(compoundtag);
                if (itemstack.isEmpty()) {
                    LOGGER.warn("Unable to load item from: {}", compoundtag);
                }
                this.setItem(itemstack, i, false);
            }
        }
        this.setDirection(Direction.from3DDataValue(compoundTag.getByte("Facing")));
        // Always use
        this.setRotation(compoundTag.getByte("ItemRotation"));
    }

    public void dropItem(@Nullable Entity p_31779_) {
        this.playSound(this.getBreakSound(), 1.0F, 1.0F);
        this.dropItem(p_31779_, true, true);
    }

    public SoundEvent getBreakSound() {
        return SoundEvents.ITEM_FRAME_BREAK;
    }

    @Override
    public void playPlacementSound() {
        this.playSound(this.getPlaceSound(), 1.0F, 1.0F);
    }

    public SoundEvent getPlaceSound() {
        return SoundEvents.ITEM_FRAME_PLACE;
    }

    protected void dropItem(@Nullable Entity entity, boolean p_31804_, boolean shouldDropAll) {
        boolean isInstabuild = false;
        if (entity instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity)entity;
            if (playerentity.abilities.instabuild) {
                isInstabuild = true;
            }
        }
        if (!isInstabuild && p_31804_) {
            this.spawnAtLocation(this.getFrameItemStack());
        }

        for (int i = this.getMaxHoldableItems() - 1; i >= 0; i--) {
            ItemStack itemstack = this.getItem(i);
            if (!itemstack.isEmpty()) {
                this.setItem(ItemStack.EMPTY, i);
                itemstack = itemstack.copy();
                if (!isInstabuild) {
                    this.spawnAtLocation(itemstack);
                }
                if (!shouldDropAll) {
                    break;
                }
            }
        }
    }

    public List<ItemStack> getItems() {
        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < this.getMaxHoldableItems(); i++) {
            items.add(this.getEntityData().get(this.getItemEntityDataAccessor(i)));
        }
        return items;
    }

    public ItemStack getItem(int place) {
        if (place < 0 || this.getMaxHoldableItems() <= place) {
            return null;
        }
        return this.getEntityData().get(this.getItemEntityDataAccessor(place));
    }

    public boolean hasItems() {
        for (int i = 0; i < this.getMaxHoldableItems(); i++) {
            if (!this.getEntityData().get(this.getItemEntityDataAccessor(i)).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public int getEmptyStandSlot() {
        for (int i = 0; i < this.getMaxHoldableItems(); i++) {
            if (this.getEntityData().get(this.getItemEntityDataAccessor(i)).isEmpty()) {
                return i;
            }
        }
        return -1;
    }

    public ItemStack getLastStandSlotItem() {
        for (int i = this.getMaxHoldableItems() -1; i >= 0; i--) {
            ItemStack itemStack = this.getEntityData().get(this.getItemEntityDataAccessor(i));
            if (!itemStack.isEmpty()) {
                return itemStack;
            }
        }
        return null;
    }

    public void setItem(ItemStack itemStack, int place) {
        this.setItem(itemStack, place, true);
    }

    public void setItem(ItemStack itemStack, int place, boolean update) {
        if (!itemStack.isEmpty()) {
            itemStack = itemStack.copy();
            itemStack.setCount(1);
        }

        if (place < 0 || 2 < place) {
            return;
        }

        this.onItemChanged(itemStack);
        this.getEntityData().set(this.getItemEntityDataAccessor(place), itemStack);
        if (!itemStack.isEmpty()) {
            this.playSound(this.getAddItemSound(), 1.0F, 1.0F);
        }

        if (update && this.pos != null) {
            this.level.updateNeighbourForOutputSignal(this.pos, Blocks.AIR);
        }
    }

    public SoundEvent getAddItemSound() {
        return SoundEvents.ITEM_FRAME_ADD_ITEM;
    }

    public void onSyncedDataUpdated(DataParameter<?> entityDataAccessor) {
        super.onSyncedDataUpdated(entityDataAccessor);
        for (int i = 0; i < this.getMaxHoldableItems(); i++) {
            if (entityDataAccessor.equals(this.getItemEntityDataAccessor(i))) {
                this.onItemChanged(this.getItem(i));
            }
        }
    }

    protected void onItemChanged(ItemStack itemStack) {
        if (!itemStack.isEmpty()) { // TODO ???
            itemStack.setEntityRepresentation(this);
        }

        this.recalculateBoundingBox();
    }

    @Override
    public ActionResultType interact(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        int emptyStandSlot = this.getEmptyStandSlot();
        boolean isStandEmpty = 0 <= emptyStandSlot;
        boolean hasItemInHand = !itemstack.isEmpty();
        if (!this.level.isClientSide) {
            boolean isIceCream = ICE_CREAM_TAG.contains(itemstack.getItem());
            if (isStandEmpty && hasItemInHand && !this.removed && isIceCream) {
                this.setItem(itemstack, emptyStandSlot);
                if (!player.abilities.instabuild) {
                    itemstack.shrink(1);
                }
            } else {
                this.playSound(this.getRotateItemSound(), 1.0F, 1.0F);
                this.setRotation(this.getRotation() + 1);
            }

            return ActionResultType.CONSUME;
        } else {
            return isStandEmpty && !hasItemInHand ? ActionResultType.PASS : ActionResultType.SUCCESS;
        }
    }

    public SoundEvent getRotateItemSound() {
        return SoundEvents.ITEM_FRAME_ROTATE_ITEM;
    }

    public boolean setSlot(int slot, ItemStack itemStack) {
        if (0 <= slot && slot <= 2) {
            this.setItem(itemStack, slot);
            return true;
        } else {
            return false;
        }
    }

    public boolean hurt(DamageSource damageSource, float p_31777_) {
        if (this.isInvulnerableTo(damageSource)) {
            return false;
        } else if (!damageSource.isExplosion() && this.hasItems()) {
            if (!this.level.isClientSide) {
                this.dropItem(damageSource.getEntity(), false, false);
                this.playSound(this.getRemoveItemSound(), 1.0F, 1.0F);
            }

            return true;
        } else {
            return super.hurt(damageSource, p_31777_);
        }
    }

    public SoundEvent getRemoveItemSound() {
        return SoundEvents.ITEM_FRAME_REMOVE_ITEM;
    }

    @Override
    public int getWidth() {
        return 6;
    }

    @Override
    public int getHeight() {
        return 6;
    }

    public boolean shouldRenderAtSqrDistance(double p_31769_) {
        double d0 = 16.0D;
        d0 *= 64.0D * getViewScale();
        return p_31769_ < d0 * d0;
    }

    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        // TODO should Items be written?
        buffer.writeInt(this.direction.get3DDataValue());
    }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        this.setDirection(Direction.from3DDataValue(additionalData.readInt()));
    }

    public ItemStack getFrameItemStack() {
        return new ItemStack(FancyIceCreamModItems.ICE_CREAM_STAND.get());
    }
}
