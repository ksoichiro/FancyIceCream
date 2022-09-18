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

    private static final DataParameter<Integer> DATA_ROTATION = EntityDataManager.createKey(IceCreamStand.class, DataSerializers.VARINT);
    protected static final DataParameter<ItemStack> DATA_ITEM1 = EntityDataManager.createKey(IceCreamStand.class, DataSerializers.ITEMSTACK);

    public IceCreamStand(EntityType<? extends HangingEntity> entityType, World level) {
        super(entityType, level);
    }

    public IceCreamStand(World level, BlockPos pos, Direction direction, Direction placedDirection) {
        this(FancyIceCreamModEntityType.ICE_CREAM_STAND.get(), level, pos, direction, placedDirection);
    }

    public IceCreamStand(EntityType<? extends HangingEntity> entityType, World level, BlockPos pos, Direction direction, Direction placedDirection) {
        super(entityType, level, pos);
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
    protected void registerData() {
        this.getDataManager().register(DATA_ROTATION, 0);
        for (int i = 0; i < getMaxHoldableItems(); i++) {
            this.getDataManager().register(getItemEntityDataAccessor(i), ItemStack.EMPTY);
        }
    }

    protected void updateFacingWithBoundingBox(Direction facingDirectionIn) {
        Validate.notNull(facingDirectionIn);
        this.facingDirection = facingDirectionIn;
        if (facingDirectionIn.getAxis().isHorizontal()) {
            this.rotationPitch = 0.0F;
            this.rotationYaw = (float)(this.facingDirection.getHorizontalIndex() * 90);
        } else {
            this.rotationPitch = (float)(-90 * facingDirectionIn.getAxisDirection().getOffset());
            this.rotationYaw = 0.0F;
        }

        this.prevRotationPitch = this.rotationPitch;
        this.prevRotationYaw = this.rotationYaw;
        this.updateBoundingBox();
    }

    protected void updateBoundingBox() {
        if (this.facingDirection != null) {
            double d0 = 0.46875D;
            double d1 = (double)this.hangingPosition.getX() + 0.5D - (double)this.facingDirection.getXOffset() * 0.46875D;
            double d2 = (double)this.hangingPosition.getY() + 0.5D - (double)this.facingDirection.getYOffset() * 0.46875D;
            double d3 = (double)this.hangingPosition.getZ() + 0.5D - (double)this.facingDirection.getZOffset() * 0.46875D;
            this.setRawPosition(d1, d2, d3);
            double d4 = (double)this.getWidthPixels();
            double d5 = (double)this.getHeightPixels();
            double d6 = (double)this.getWidthPixels();
            Direction.Axis direction$axis = this.facingDirection.getAxis();
            switch(direction$axis) {
                case X:
                    d4 = 1.0D;
                    break;
                case Y:
                    d5 = 1.0D;
                    break;
                case Z:
                    d6 = 1.0D;
            }

            d4 = d4 / 32.0D;
            d5 = d5 / 32.0D;
            d6 = d6 / 32.0D;
            this.setBoundingBox(new AxisAlignedBB(d1 - d4, d2 - d5, d3 - d6, d1 + d4, d2 + d5, d3 + d6));
        }
    }

    public boolean onValidSurface() {
        if (!this.world.hasNoCollisions(this)) {
            return false;
        } else {
            BlockState blockstate = this.world.getBlockState(this.hangingPosition.offset(this.facingDirection.getOpposite()));
            return blockstate.getMaterial().isSolid() || this.facingDirection.getAxis().isHorizontal() && RedstoneDiodeBlock.isDiode(blockstate) ? this.world.getEntitiesInAABBexcluding(this, this.getBoundingBox(), IS_HANGING_ENTITY).isEmpty() : false;
        }
    }

    public int getRotation() {
        return this.getDataManager().get(DATA_ROTATION);
    }

    public void setRotation(int rotation) {
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
        compoundTag.putByte("Facing", (byte)this.facingDirection.getIndex());
        // Always use
        compoundTag.putByte("ItemRotation", (byte)this.getRotation());
        for (int i = 0; i < this.getMaxHoldableItems(); i++) {
            if (!this.getItem(i).isEmpty()) {
                compoundTag.put("Item" + (i + 1), this.getItem(i).write(new CompoundNBT()));
            }
        }
    }

    @Override
    public void readAdditional(CompoundNBT compoundTag) {
        super.readAdditional(compoundTag);
        for (int i = 0; i < this.getMaxHoldableItems(); i++) {
            CompoundNBT compoundtag = compoundTag.getCompound("Item" + (i + 1));
            if (compoundtag != null && !compoundtag.isEmpty()) {
                ItemStack itemstack = ItemStack.read(compoundtag);
                if (itemstack.isEmpty()) {
                    LOGGER.warn("Unable to load item from: {}", compoundtag);
                }
                this.setItem(itemstack, i, false);
            }
        }
        this.updateFacingWithBoundingBox(Direction.byIndex(compoundTag.getByte("Facing")));
        // Always use
        // Without 2nd parameter, this is going to be stuck.
        this.setRotation(compoundTag.getByte("ItemRotation"), false);
    }


    public void onBroken(@Nullable Entity entity) {
        this.playSound(this.getBreakSound(), 1.0F, 1.0F);
        this.dropItem(entity, true, true);
    }

    public SoundEvent getBreakSound() {
        return SoundEvents.ENTITY_ITEM_FRAME_BREAK;
    }

    @Override
    public void playPlaceSound() {
        this.playSound(this.getPlaceSound(), 1.0F, 1.0F);
    }

    public SoundEvent getPlaceSound() {
        return SoundEvents.ENTITY_ITEM_FRAME_PLACE;
    }

    protected void dropItem(@Nullable Entity entity, boolean p_31804_, boolean shouldDropAll) {
        boolean isInstabuild = false;
        if (entity instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity)entity;
            if (playerentity.abilities.isCreativeMode) {
                isInstabuild = true;
            }
        }
        if (!isInstabuild && p_31804_) {
            this.entityDropItem(this.getFrameItemStack());
        }

        for (int i = this.getMaxHoldableItems() - 1; i >= 0; i--) {
            ItemStack itemstack = this.getItem(i);
            if (!itemstack.isEmpty()) {
                this.setItem(ItemStack.EMPTY, i);
                itemstack = itemstack.copy();
                if (!isInstabuild) {
                    this.entityDropItem(itemstack);
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
            items.add(this.getDataManager().get(this.getItemEntityDataAccessor(i)));
        }
        return items;
    }

    public ItemStack getItem(int place) {
        if (place < 0 || this.getMaxHoldableItems() <= place) {
            return null;
        }
        return this.getDataManager().get(this.getItemEntityDataAccessor(place));
    }

    public boolean hasItems() {
        for (int i = 0; i < this.getMaxHoldableItems(); i++) {
            if (!this.getDataManager().get(this.getItemEntityDataAccessor(i)).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public int getEmptyStandSlot() {
        for (int i = 0; i < this.getMaxHoldableItems(); i++) {
            if (this.getDataManager().get(this.getItemEntityDataAccessor(i)).isEmpty()) {
                return i;
            }
        }
        return -1;
    }

    public ItemStack getLastStandSlotItem() {
        for (int i = this.getMaxHoldableItems() -1; i >= 0; i--) {
            ItemStack itemStack = this.getDataManager().get(this.getItemEntityDataAccessor(i));
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
            itemStack.func_234695_a_(this);
        }

        if (place < 0 || 2 < place) {
            return;
        }

        this.getDataManager().set(this.getItemEntityDataAccessor(place), itemStack);
        if (!itemStack.isEmpty()) {
            this.playSound(this.getAddItemSound(), 1.0F, 1.0F);
        }

        if (update && this.hangingPosition != null) {
            this.world.updateComparatorOutputLevel(this.hangingPosition, Blocks.AIR);
        }
    }

    public SoundEvent getAddItemSound() {
        return SoundEvents.ENTITY_ITEM_FRAME_ADD_ITEM;
    }

    public void notifyDataManagerChange(DataParameter<?> entityDataAccessor) {
        for (int i = 0; i < this.getMaxHoldableItems(); i++) {
            if (entityDataAccessor.equals(this.getItemEntityDataAccessor(i)) && !this.getItem(i).isEmpty()) {
                this.getItem(i).func_234695_a_(this);
            }
        }
    }

    @Override
    public ActionResultType processInitialInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        int emptyStandSlot = this.getEmptyStandSlot();
        boolean isStandEmpty = 0 <= emptyStandSlot;
        boolean hasItemInHand = !itemstack.isEmpty();
        if (!this.world.isRemote) {
            boolean isIceCream = ICE_CREAM_TAG.contains(itemstack.getItem()); // TODO ???
            if (isStandEmpty && hasItemInHand && this.isAlive() && isIceCream) {
                this.setItem(itemstack, emptyStandSlot);
                if (!player.abilities.isCreativeMode) {
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

    public boolean attackEntityFrom(DamageSource damageSource, float amount) {
        if (this.isInvulnerableTo(damageSource)) {
            return false;
        } else if (!damageSource.isExplosion() && this.hasItems()) {
            if (!this.world.isRemote) {
                this.dropItem(damageSource.getTrueSource(), false, false);
                this.playSound(this.getRemoveItemSound(), 1.0F, 1.0F);
            }

            return true;
        } else {
            return super.attackEntityFrom(damageSource, amount);
        }
    }

    public SoundEvent getRotateItemSound() {
        return SoundEvents.ENTITY_ITEM_FRAME_ROTATE_ITEM;
    }

    public boolean replaceItemInInventory(int slot, ItemStack itemStack) {
        if (0 <= slot && slot <= 2) {
            this.setItem(itemStack, slot);
            return true;
        } else {
            return false;
        }
    }

    public SoundEvent getRemoveItemSound() {
        return SoundEvents.ENTITY_ITEM_FRAME_REMOVE_ITEM;
    }

    @Override
    public int getWidthPixels() {
        return 6;
    }

    @Override
    public int getHeightPixels() {
        return 6;
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
        // TODO should Items be written?
        buffer.writeInt(this.facingDirection.getIndex());
    }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        this.updateFacingWithBoundingBox(Direction.byIndex(additionalData.readInt()));
    }

    public ItemStack getFrameItemStack() {
        return new ItemStack(FancyIceCreamModItems.ICE_CREAM_STAND.get());
    }
}
