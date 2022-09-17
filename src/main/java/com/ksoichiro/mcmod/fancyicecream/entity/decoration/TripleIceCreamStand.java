package com.ksoichiro.mcmod.fancyicecream.entity.decoration;

import com.ksoichiro.mcmod.fancyicecream.common.Tag;
import com.ksoichiro.mcmod.fancyicecream.entity.FancyIceCreamModEntityType;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import javax.annotation.Nullable;

public class TripleIceCreamStand extends ItemFrame {
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
        super(FancyIceCreamModEntityType.TRIPLE_ICE_CREAM_STAND.get(), level, pos, direction);

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
        for (int i = 0; i < 3; i++) {
            if (!this.getItem(i).isEmpty()) {
                compoundTag.put("Item" + (i + 1), this.getItem(i).save(new CompoundTag()));
            }
        }
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
        // Always use
        this.setRotation(compoundTag.getByte("ItemRotation"));
    }

    public void dropItem(@Nullable Entity p_31779_) {
        this.playSound(this.getBreakSound(), 1.0F, 1.0F);
        this.dropItem(p_31779_, true, true);
    }

    protected void dropItem(@Nullable Entity entity, boolean p_31804_, boolean shouldDropAll) {
        this.setItem(ItemStack.EMPTY);
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
        if (!itemStack.isEmpty() && itemStack.getFrame() != this) {
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

    // TODO Should override getSlot? because it calls this.getItem()

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

    @Override
    public int getWidth() {
        return 6;
    }

    @Override
    public int getHeight() {
        return 6;
    }

    @Override
    public ItemStack getPickResult() {
        int emptyStandSlot = this.getEmptyStandSlot();
        return emptyStandSlot < 0 ? this.getFrameItemStack() : this.getItem(emptyStandSlot - 1).copy();
    }

    @Override
    public ItemStack getFrameItemStack() {
        return new ItemStack(TRIPLE_ICE_CREAM_STAND.get());
    }
}
