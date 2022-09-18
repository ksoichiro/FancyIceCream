package com.ksoichiro.mcmod.fancyicecream.entity.decoration;

import com.ksoichiro.mcmod.fancyicecream.common.Tag;
import com.ksoichiro.mcmod.fancyicecream.entity.FancyIceCreamModEntityType;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;

import javax.annotation.Nullable;

public class TripleIceCreamStand extends HangingEntity implements IEntityAdditionalSpawnData {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final Tag<Item> ICE_CREAM_TAG = Tag.createItemTag("ice_cream");
    public static final RegistryObject<Item> TRIPLE_ICE_CREAM_STAND = RegistryObject.create(new ResourceLocation("fancyicecreammod:triple_ice_cream_stand"), ForgeRegistries.ITEMS);

    private static final EntityDataAccessor<Integer> DATA_ROTATION = SynchedEntityData.defineId(TripleIceCreamStand.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<ItemStack> DATA_ITEM1 = SynchedEntityData.defineId(TripleIceCreamStand.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> DATA_ITEM2 = SynchedEntityData.defineId(TripleIceCreamStand.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> DATA_ITEM3 = SynchedEntityData.defineId(TripleIceCreamStand.class, EntityDataSerializers.ITEM_STACK);

    public TripleIceCreamStand(EntityType<TripleIceCreamStand> standEntityType, Level level) {
        super(FancyIceCreamModEntityType.TRIPLE_ICE_CREAM_STAND.get(), level);
    }

    public TripleIceCreamStand(Level level, BlockPos pos, Direction direction, Direction placedDirection) {
        super(FancyIceCreamModEntityType.TRIPLE_ICE_CREAM_STAND.get(), level, pos);
        this.setDirection(direction);

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
        this.getEntityData().define(DATA_ITEM1, ItemStack.EMPTY);
        this.getEntityData().define(DATA_ITEM2, ItemStack.EMPTY);
        this.getEntityData().define(DATA_ITEM3, ItemStack.EMPTY);
    }

    protected void setDirection(Direction p_31793_) {
        Validate.notNull(p_31793_);
        this.direction = p_31793_;
        if (p_31793_.getAxis().isHorizontal()) {
            this.setXRot(0.0F);
            this.setYRot((float)(this.direction.get2DDataValue() * 90));
        } else {
            this.setXRot((float)(-90 * p_31793_.getAxisDirection().getStep()));
            this.setYRot(0.0F);
        }

        this.xRotO = this.getXRot();
        this.yRotO = this.getYRot();
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
            this.setBoundingBox(new AABB(d1 - d4, d2 - d5, d3 - d6, d1 + d4, d2 + d5, d3 + d6));
        }
    }

    public boolean survives() {
        if (!this.level.noCollision(this)) {
            return false;
        } else {
            BlockState blockstate = this.level.getBlockState(this.pos.relative(this.direction.getOpposite()));
            return blockstate.getMaterial().isSolid() || this.direction.getAxis().isHorizontal() && DiodeBlock.isDiode(blockstate) ? this.level.getEntities(this, this.getBoundingBox(), HANGING_ENTITY).isEmpty() : false;
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
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        for (int i = 0; i < 3; i++) {
            if (!this.getItem(i).isEmpty()) {
                compoundTag.put("Item" + (i + 1), this.getItem(i).save(new CompoundTag()));
            }
        }
        compoundTag.putByte("Facing", (byte)this.direction.get3DDataValue());
        // Always use
        compoundTag.putByte("ItemRotation", (byte)this.getRotation());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        for (int i = 0; i < 3; i++) {
            CompoundTag compoundtag = compoundTag.getCompound("Item" + (i + 1));
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
        if (entity instanceof Player player) {
            if (player.getAbilities().instabuild) {
                isInstabuild = true;
            }
        }
        if (!isInstabuild && p_31804_) {
            this.spawnAtLocation(this.getFrameItemStack());
        }

        for (int i = 2; i >= 0; i--) {
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

    public ItemStack[] getItems() {
        return new ItemStack[] {
                this.getEntityData().get(DATA_ITEM1),
                this.getEntityData().get(DATA_ITEM2),
                this.getEntityData().get(DATA_ITEM3),
        };
    }

    public ItemStack getItem(int place) {
        if (place < 0 || 2 < place) {
            return null;
        }
        EntityDataAccessor<ItemStack> target = null;
        switch (place) {
            case 0 -> target = DATA_ITEM1;
            case 1 -> target = DATA_ITEM2;
            case 2 -> target = DATA_ITEM3;
        }
        return this.getEntityData().get(target);
    }

    public boolean hasItems() {
        return !this.getEntityData().get(DATA_ITEM1).isEmpty()
                || !this.getEntityData().get(DATA_ITEM2).isEmpty()
                || !this.getEntityData().get(DATA_ITEM3).isEmpty();
    }

    public int getEmptyStandSlot() {
        if (this.getEntityData().get(DATA_ITEM1).isEmpty()) {
            return 0;
        }
        if (this.getEntityData().get(DATA_ITEM2).isEmpty()) {
            return 1;
        }
        if (this.getEntityData().get(DATA_ITEM3).isEmpty()) {
            return 2;
        }
        return -1;
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
        EntityDataAccessor<ItemStack> target = null;
        switch (place) {
            case 0 -> target = DATA_ITEM1;
            case 1 -> target = DATA_ITEM2;
            case 2 -> target = DATA_ITEM3;
        }
        this.getEntityData().set(target, itemStack);
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

    public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
        super.onSyncedDataUpdated(entityDataAccessor);
        if (entityDataAccessor.equals(DATA_ITEM1)) {
            this.onItemChanged(this.getItem(0));
        }
        if (entityDataAccessor.equals(DATA_ITEM2)) {
            this.onItemChanged(this.getItem(1));
        }
        if (entityDataAccessor.equals(DATA_ITEM3)) {
            this.onItemChanged(this.getItem(2));
        }
    }

    protected void onItemChanged(ItemStack itemStack) {
        if (!itemStack.isEmpty()) { // TODO ???
            itemStack.setEntityRepresentation(this);
        }

        this.recalculateBoundingBox();
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        int emptyStandSlot = this.getEmptyStandSlot();
        boolean isStandEmpty = 0 <= emptyStandSlot;
        boolean hasItemInHand = !itemstack.isEmpty();
        if (!this.level.isClientSide) {
            boolean isIceCream = ICE_CREAM_TAG.contains(itemstack.getItem());
            if (isStandEmpty && hasItemInHand && !this.isRemoved() && isIceCream) {
                this.setItem(itemstack, emptyStandSlot);
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

    public SoundEvent getRotateItemSound() {
        return SoundEvents.ITEM_FRAME_ROTATE_ITEM;
    }

    // From 1.17:
    //   e.g. /item replace entity UUID container.0 with fancyicecreammod:chocolate_ice_cream
    public SlotAccess getSlot(int slot) {
        return 0 <= slot && slot <= 2 ? new SlotAccess() {
            public ItemStack get() {
                return TripleIceCreamStand.this.getItem(slot);
            }

            public boolean set(ItemStack itemStack) {
                TripleIceCreamStand.this.setItem(itemStack, slot);
                return true;
            }
        } : super.getSlot(slot);
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

    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void recreateFromPacket(ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        this.setDirection(Direction.from3DDataValue(packet.getData()));
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        buffer.writeInt(this.direction.get3DDataValue());
    }

    @Override
    public void readSpawnData(FriendlyByteBuf additionalData) {
        this.setDirection(Direction.from3DDataValue(additionalData.readInt()));
    }

    @Override
    public ItemStack getPickResult() {
        int emptyStandSlot = this.getEmptyStandSlot();
        return emptyStandSlot < 0 ? this.getFrameItemStack() : this.getItem(emptyStandSlot - 1).copy();
    }

    public ItemStack getFrameItemStack() {
        return new ItemStack(TRIPLE_ICE_CREAM_STAND.get());
    }

    public float getVisualRotationYInDegrees() {
        Direction direction = this.getDirection();
        int i = direction.getAxis().isVertical() ? 90 * direction.getAxisDirection().getStep() : 0;
        return (float) Mth.wrapDegrees(180 + direction.get2DDataValue() * 90 + this.getRotation() * 45 + i);
    }
}
