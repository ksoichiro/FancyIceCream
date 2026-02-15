package com.ksoichiro.mcmod.fancyicecream.item;

import com.ksoichiro.mcmod.fancyicecream.entity.decoration.IceCreamStand;
import com.ksoichiro.mcmod.fancyicecream.main.FancyIceCreamMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.HangingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HangingEntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class IceCreamStandItem extends HangingEntityItem {
    public IceCreamStandItem() {
        super(null, new Properties().tab(FancyIceCreamMod.FANCY_ICE_CREAM_MOD_TAB));
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        BlockPos blockpos = context.getClickedPos();
        Direction direction = context.getClickedFace();
        BlockPos blockpos1 = blockpos.relative(direction);
        PlayerEntity player = context.getPlayer();
        ItemStack itemstack = context.getItemInHand();
        if (player != null && !this.mayPlace(player, direction, itemstack, blockpos1)) {
            return ActionResultType.FAIL;
        }
        World level = context.getLevel();
        HangingEntity hangingentity = getEntityFactory().create(level, blockpos1, direction, player.getDirection());

        CompoundNBT compoundtag = itemstack.getTag();
        if (compoundtag != null) {
            EntityType.updateCustomEntityTag(level, player, hangingentity, compoundtag);
        }

        if (hangingentity.survives()) {
            if (!level.isClientSide) {
                hangingentity.playPlacementSound();
                level.addFreshEntity(hangingentity);
            }
            itemstack.shrink(1);
            return ActionResultType.sidedSuccess(level.isClientSide);
        }
        return ActionResultType.CONSUME;
    }

    protected boolean mayPlace(PlayerEntity player, Direction clickedFaceDirection, ItemStack itemStack, BlockPos blockPos) {
        return clickedFaceDirection == Direction.UP
                && !World.isOutsideBuildHeight(blockPos)
                && player.mayUseItemAt(blockPos, clickedFaceDirection, itemStack);
    }

    protected interface IceCreamStandFactory<T extends HangingEntity> {
        T create(World level, BlockPos blockpos1, Direction direction, Direction placedDirection);
    }

    protected IceCreamStandFactory<?> getEntityFactory() {
        return IceCreamStand::new;
    }
}
