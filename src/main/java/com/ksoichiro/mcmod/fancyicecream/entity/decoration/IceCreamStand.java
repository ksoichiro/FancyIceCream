package com.ksoichiro.mcmod.fancyicecream.entity.decoration;

import com.ksoichiro.mcmod.fancyicecream.common.ModTags;
import com.ksoichiro.mcmod.fancyicecream.entity.FancyIceCreamModEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
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
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IceCreamStand extends HangingEntity implements IEntityAdditionalSpawnData {
    public static final RegistryObject<Item> ICE_CREAM_STAND = RegistryObject.create(ResourceLocation.parse("fancyicecream:ice_cream_stand"), ForgeRegistries.ITEMS);

    private static final EntityDataAccessor<Integer> DATA_ROTATION = SynchedEntityData.defineId(IceCreamStand.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<ItemStack> DATA_ITEM1 = SynchedEntityData.defineId(IceCreamStand.class, EntityDataSerializers.ITEM_STACK);

    public IceCreamStand(EntityType<? extends HangingEntity> entityType, Level level) {
        super(entityType, level);
    }

    public IceCreamStand(Level level, BlockPos pos, Direction direction, Direction placedDirection) {
        this(FancyIceCreamModEntityType.ICE_CREAM_STAND.get(), level, pos, direction, placedDirection);
    }

    public IceCreamStand(EntityType<? extends HangingEntity> entityType, Level level, BlockPos pos, Direction direction, Direction placedDirection) {
        super(entityType, level, pos);
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

    protected int getMaxHoldableItems() {
        return 1;
    }

    protected EntityDataAccessor<ItemStack> getItemEntityDataAccessor(int slot) {
        if (slot == 0) {
            return DATA_ITEM1;
        }
        return null;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ROTATION, 0);
        for (int i = 0; i < getMaxHoldableItems(); i++) {
            builder.define(getItemEntityDataAccessor(i), ItemStack.EMPTY);
        }
    }

    @Override
    protected void setDirection(Direction pFacingDirection) {
        Validate.notNull(pFacingDirection);
        super.setDirectionRaw(pFacingDirection);
        if (pFacingDirection.getAxis().isHorizontal()) {
            this.setXRot(0.0F);
            this.setYRot((float)(this.getDirection().get2DDataValue() * 90));
        } else {
            this.setXRot((float)(-90 * pFacingDirection.getAxisDirection().getStep()));
            this.setYRot(0.0F);
        }

        this.xRotO = this.getXRot();
        this.yRotO = this.getYRot();
        this.recalculateBoundingBox();
    }

    @Override
    protected AABB calculateBoundingBox(BlockPos blockPos, Direction direction) {
        double d0 = 0.46875D;
        double d1 = (double)blockPos.getX() + 0.5D - (double)direction.getStepX() * 0.46875D;
        double d2 = (double)blockPos.getY() + 0.5D - (double)direction.getStepY() * 0.46875D;
        double d3 = (double)blockPos.getZ() + 0.5D - (double)direction.getStepZ() * 0.46875D;
        double d4 = (double)this.getWidth();
        double d5 = (double)this.getHeight();
        double d6 = (double)this.getWidth();
        Direction.Axis axis = direction.getAxis();
        switch (axis) {
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
        return new AABB(d1 - d4, d2 - d5, d3 - d6, d1 + d4, d2 + d5, d3 + d6);
    }

    @Override
    public boolean survives() {
        if (!this.level().noCollision(this)) {
            return false;
        } else {
            BlockState blockstate = this.level().getBlockState(this.pos.relative(this.getDirection().getOpposite()));
            return blockstate.isSolid() || this.getDirection().getAxis().isHorizontal() && DiodeBlock.isDiode(blockstate) ? this.level().getEntities(this, this.getBoundingBox(), HANGING_ENTITY).isEmpty() : false;
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
    public void addAdditionalSaveData(ValueOutput compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putByte("Facing", (byte)this.getDirection().get3DDataValue());
        // Always use
        compoundTag.putByte("ItemRotation", (byte)this.getRotation());
        for (int i = 0; i < this.getMaxHoldableItems(); i++) {
            if (!this.getItem(i).isEmpty()) {
                compoundTag.store("Item" + (i + 1), ItemStack.CODEC, this.getItem(i));
            }
        }
    }

    @Override
    public void readAdditionalSaveData(ValueInput compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        for (int i = 0; i < this.getMaxHoldableItems(); i++) {
            ItemStack itemStack = compoundTag.read("Item" + (i + 1), ItemStack.CODEC).orElse(ItemStack.EMPTY);
            if (!itemStack.isEmpty()) {
                this.setItem(itemStack, i, false);
            }
        }
        this.setDirection(compoundTag.read("Facing", Direction.LEGACY_ID_CODEC).orElse(Direction.DOWN));
        // Always use
        this.setRotation(compoundTag.getByteOr("ItemRotation", (byte) 0));
    }

    @Override
    public void dropItem(ServerLevel level, @Nullable Entity pBrokenEntity) {
        this.playSound(this.getBreakSound(), 1.0F, 1.0F);
        this.dropItem(pBrokenEntity, true, true);
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

    protected void dropItem(@Nullable Entity entity, boolean pEntity, boolean shouldDropAll) {
        boolean isInstabuild = false;
        if (entity instanceof Player player) {
            if (player.getAbilities().instabuild) {
                isInstabuild = true;
            }
        }
        if (!isInstabuild && pEntity) {
            this.spawnAtLocation((ServerLevel) this.level(), this.getFrameItemStack());
        }

        for (int i = this.getMaxHoldableItems() - 1; i >= 0; i--) {
            ItemStack itemstack = this.getItem(i);
            if (!itemstack.isEmpty()) {
                this.setItem(ItemStack.EMPTY, i);
                itemstack = itemstack.copy();
                if (!isInstabuild) {
                    this.spawnAtLocation((ServerLevel) this.level(), itemstack);
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
            this.level().updateNeighbourForOutputSignal(this.pos, Blocks.AIR);
        }
    }

    public SoundEvent getAddItemSound() {
        return SoundEvents.ITEM_FRAME_ADD_ITEM;
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
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
    public InteractionResult interact(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        int emptyStandSlot = this.getEmptyStandSlot();
        boolean isStandEmpty = 0 <= emptyStandSlot;
        boolean hasItemInHand = !itemstack.isEmpty();
        if (!this.level().isClientSide) {
            boolean isIceCream = itemstack.is(ModTags.Items.ICE_CREAM);
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
    //   e.g. /item replace entity UUID container.0 with fancyicecream:chocolate_ice_cream
    public SlotAccess getSlot(int slot) {
        return 0 <= slot && slot <= 2 ? new SlotAccess() {
            public ItemStack get() {
                return IceCreamStand.this.getItem(slot);
            }

            public boolean set(ItemStack itemStack) {
                IceCreamStand.this.setItem(itemStack, slot);
                return true;
            }
        } : super.getSlot(slot);
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount) {
        if (this.isInvulnerableToBase(damageSource)) {
            return false;
        } else if (!damageSource.is(DamageTypeTags.IS_EXPLOSION) && this.hasItems()) {
            this.dropItem(damageSource.getEntity(), false, false);
            this.playSound(this.getRemoveItemSound(), 1.0F, 1.0F);
            return true;
        } else {
            return super.hurtServer(level, damageSource, amount);
        }
    }

    public SoundEvent getRemoveItemSound() {
        return SoundEvents.ITEM_FRAME_REMOVE_ITEM;
    }

    public int getWidth() {
        return 6;
    }

    public int getHeight() {
        return 6;
    }

    public boolean shouldRenderAtSqrDistance(double p_31769_) {
        double d0 = 16.0D;
        d0 *= 64.0D * getViewScale();
        return p_31769_ < d0 * d0;
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this, this.getDirection().get3DDataValue(), this.getPos());
    }

    public void recreateFromPacket(ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        this.setDirection(Direction.from3DDataValue(packet.getData()));
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        // TODO should Items be written?
        buffer.writeInt(this.getDirection().get3DDataValue());
    }

    @Override
    public void readSpawnData(FriendlyByteBuf additionalData) {
        this.setDirection(Direction.from3DDataValue(additionalData.readInt()));
    }

    @Override
    public ItemStack getPickResult() {
        ItemStack itemStack = this.getLastStandSlotItem();
        if (itemStack != null) {
            return itemStack.copy();
        }
        return this.getFrameItemStack();
    }

    public ItemStack getFrameItemStack() {
        return new ItemStack(ICE_CREAM_STAND.get());
    }

    public float getVisualRotationYInDegrees() {
        Direction direction = this.getDirection();
        int i = direction.getAxis().isVertical() ? 90 * direction.getAxisDirection().getStep() : 0;
        return (float) Mth.wrapDegrees(180 + direction.get2DDataValue() * 90 + this.getRotation() * 45 + i);
    }
}
