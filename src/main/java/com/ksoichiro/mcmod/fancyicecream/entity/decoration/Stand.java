package com.ksoichiro.mcmod.fancyicecream.entity.decoration;

import com.ksoichiro.mcmod.fancyicecream.entity.FancyIceCreamModEntityType;
import com.ksoichiro.mcmod.fancyicecream.registry.FancyIceCreamModItems;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;

public class Stand extends ItemFrame {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final TagKey<Item> ICE_CREAM_TAG = ItemTags.create(new ResourceLocation("fancyicecreammod", "ice_cream"));

    private static final EntityDataAccessor<Integer> DATA_PLACED_DIRECTION = SynchedEntityData.defineId(Stand.class, EntityDataSerializers.INT);

    private Direction placedDirection;

    public Stand(EntityType<Stand> standEntityType, Level level) {
        super(FancyIceCreamModEntityType.STAND, level);
        LOGGER.info("Stand[{}]: non direction", this.hashCode());
    }

    public Stand(Level level, BlockPos pos, Direction direction, Direction placedDirection) {
        super(FancyIceCreamModEntityType.STAND, level, pos, direction);
        this.setPlacedDirection(placedDirection);
        LOGGER.info("Stand[{}]: setPlacedDirection: {}", this.hashCode(), this.getPlacedDirection());
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        boolean isStandEmpty = this.getItem().isEmpty();
        boolean hasItemInHand = !itemstack.isEmpty();
        // TODO fixed in ItemFrame is ignored
        if (!this.level.isClientSide) {
            if (isStandEmpty) {
                if (hasItemInHand && !this.isRemoved()) {
                    boolean isIceCream = ForgeRegistries.ITEMS.tags().getTag(ICE_CREAM_TAG).contains(itemstack.getItem());
                    if (isIceCream) {
                        this.setItem(itemstack);
                        if (!player.getAbilities().instabuild) {
                            itemstack.shrink(1);
                        }
                    }
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
    public ItemStack getFrameItemStack() {
        return new ItemStack(FancyIceCreamModItems.STAND);
    }

    public Direction getPlacedDirection() {
        return Direction.from3DDataValue(this.getEntityData().get(DATA_PLACED_DIRECTION));
//        return this.placedDirection;
    }

    public void setPlacedDirection(Direction direction) {
        this.getEntityData().set(DATA_PLACED_DIRECTION, direction.get3DDataValue());
//        this.placedDirection = direction;

        this.setXRot(0.0F);
        this.setYRot((float)(this.direction.get2DDataValue() * 90));

        this.xRotO = this.getXRot();
        this.yRotO = this.getYRot();
        this.recalculateBoundingBox();
    }

    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);

        // もうある
        // compoundTag.putByte("Facing", (byte) this.direction.get3DDataValue());
        // 保存したいのはdirectionではなくてplacedDirection
        compoundTag.putByte("PlacedDirection", (byte) this.getPlacedDirection().get3DDataValue());
        LOGGER.info("Stand[{}]: addAdditionalSaveData: {}", this.hashCode(), placedDirection);
    }

    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);

        Validate.notNull(compoundTag);
        this.setPlacedDirection(Direction.from3DDataValue(compoundTag.getByte("PlacedDirection")));
        LOGGER.info("Stand[{}]: readAdditionalSaveData: {}", this.hashCode(), placedDirection);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_PLACED_DIRECTION, 0);
    }
}
