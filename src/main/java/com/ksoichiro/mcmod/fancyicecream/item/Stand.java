package com.ksoichiro.mcmod.fancyicecream.item;

import com.ksoichiro.mcmod.fancyicecream.entity.FancyIceCreamModEntityType;
import com.ksoichiro.mcmod.fancyicecream.main.FancyIceCreamMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HangingEntityItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Stand extends HangingEntityItem {
    private static final Logger LOGGER = LogManager.getLogger();

    public Stand() {
        super(FancyIceCreamModEntityType.STAND, new Properties().tab(FancyIceCreamMod.FANCY_ICE_CREAM_MOD_TAB));
//        super(EntityType.ITEM_FRAME, new Properties().tab(FancyIceCreamMod.FANCY_ICE_CREAM_MOD_TAB));
        this.setRegistryName("stand");
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
        LOGGER.info("StandItem::useOn() -> getItemInHand(): {}", itemstack.getItem().getRegistryName());
        Level level = context.getLevel();
        HangingEntity hangingentity = new com.ksoichiro.mcmod.fancyicecream.entity.decoration.Stand(level, blockpos1, direction);
        if (hangingentity.survives()) {
            if (!level.isClientSide) {
                LOGGER.info("StandItem::useOn() -> addFreshEntity()");
                hangingentity.playPlacementSound();
                level.gameEvent(player, GameEvent.ENTITY_PLACE, blockpos);
                level.addFreshEntity(hangingentity);
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    protected boolean mayPlace(Player player, Direction clickedFaceDirection, ItemStack itemStack, BlockPos blockPos) {
        return clickedFaceDirection == Direction.UP
                && !player.level.isOutsideBuildHeight(blockPos)
                && player.mayUseItemAt(blockPos, clickedFaceDirection, itemStack);
    }
}
