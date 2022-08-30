package com.ksoichiro.mcmod.fancyicecream.entity.decoration;

import com.ksoichiro.mcmod.fancyicecream.entity.FancyIceCreamModEntityType;
import com.ksoichiro.mcmod.fancyicecream.main.FancyIceCreamMod;
import com.ksoichiro.mcmod.fancyicecream.registry.FancyIceCreamModItems;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.Validate;

public class Stand extends ItemFrame {
    public static final TagKey<Item> ICE_CREAM_TAG = ItemTags.create(new ResourceLocation(FancyIceCreamMod.MOD_ID, "ice_cream"));

    // Entity data should also be saved and restored as CompoundTag.
    private static final String TAG_NAME_PLACED_DIRECTION = "PlacedDirection";

    // Synchronize entity data from server to client using data parameters.
    // https://docs.minecraftforge.net/en/latest/networking/entities/#data-parameters
    private static final EntityDataAccessor<Integer> DATA_PLACED_DIRECTION = SynchedEntityData.defineId(Stand.class, EntityDataSerializers.INT);

    public Stand(EntityType<Stand> standEntityType, Level level) {
        super(FancyIceCreamModEntityType.STAND, level);
    }

    public Stand(Level level, BlockPos pos, Direction direction, Direction placedDirection) {
        super(FancyIceCreamModEntityType.STAND, level, pos, direction);
        this.setPlacedDirection(placedDirection);
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
    }

    public void setPlacedDirection(Direction direction) {
        this.getEntityData().set(DATA_PLACED_DIRECTION, direction.get3DDataValue());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);

        compoundTag.putByte(TAG_NAME_PLACED_DIRECTION, (byte) this.getPlacedDirection().get3DDataValue());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);

        Validate.notNull(compoundTag);
        this.setPlacedDirection(Direction.from3DDataValue(compoundTag.getByte(TAG_NAME_PLACED_DIRECTION)));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_PLACED_DIRECTION, 0);
    }
}
