package com.ksoichiro.mcmod.fancyicecream.item;

import com.ksoichiro.mcmod.fancyicecream.entity.FancyIceCreamModEntityType;
import com.ksoichiro.mcmod.fancyicecream.entity.decoration.Stand;
import com.ksoichiro.mcmod.fancyicecream.main.FancyIceCreamMod;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HangingEntityItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.slf4j.Logger;

public class StandItem extends HangingEntityItem {
    private static final Logger LOGGER = LogUtils.getLogger();

    public StandItem() {
        super(FancyIceCreamModEntityType.STAND, new Properties().tab(FancyIceCreamMod.FANCY_ICE_CREAM_MOD_TAB));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos blockpos = context.getClickedPos();
        Direction direction = context.getClickedFace();
        BlockPos blockpos1 = blockpos.relative(direction);
        Player player = context.getPlayer();
        ItemStack itemstack = context.getItemInHand();
        if (player != null && !this.mayPlace(player, direction, itemstack, blockpos1)) {
            return InteractionResult.FAIL;
        }
        Level level = context.getLevel();
        LOGGER.info("StandItem: player.getDirection: {}", player.getDirection());
        HangingEntity hangingentity = new Stand(level, blockpos1, direction, player.getDirection());
        String rot;
        if (0 < context.getRotation() && context.getRotation() <= 90) {
            rot = "A";
        }
        else if (90 < context.getRotation() && context.getRotation() <= 180) {
            rot = "B";
        }
        else if (180 < context.getRotation() && context.getRotation() <= 270) {
            rot = "C";
        }
        else {
            rot = "D";
        }
//        LOGGER.info("StandItem: itemInHand: {}, rotation: {} {} {} {}", itemstack.getItem().getRegistryName(), context.getRotation(), rot, player.getYRot(), player.getDirection());

        CompoundTag compoundtag = itemstack.getTag();
        if (compoundtag != null) {
            EntityType.updateCustomEntityTag(level, player, hangingentity, compoundtag);
        }

        if (hangingentity.survives()) {
            if (!level.isClientSide) {
                hangingentity.playPlacementSound();
                level.gameEvent(player, GameEvent.ENTITY_PLACE, blockpos);
                level.addFreshEntity(hangingentity);
            }
            itemstack.shrink(1);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.CONSUME;
    }

    protected boolean mayPlace(Player player, Direction clickedFaceDirection, ItemStack itemStack, BlockPos blockPos) {
        return clickedFaceDirection == Direction.UP
                && !player.level.isOutsideBuildHeight(blockPos)
                && player.mayUseItemAt(blockPos, clickedFaceDirection, itemStack);
    }
}
