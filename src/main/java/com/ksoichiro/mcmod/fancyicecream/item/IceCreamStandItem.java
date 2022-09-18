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
        super(null, new Properties().group(FancyIceCreamMod.FANCY_ICE_CREAM_MOD_TAB));
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        BlockPos blockpos = context.getPos();
        Direction direction = context.getFace();
        BlockPos blockpos1 = blockpos.offset(direction);
        PlayerEntity player = context.getPlayer();
        ItemStack itemstack = context.getItem();
        if (player != null && !this.mayPlace(player, direction, itemstack, blockpos1)) {
            return ActionResultType.FAIL;
        }
        World level = context.getWorld();
        HangingEntity hangingentity = getEntityFactory().create(level, blockpos1, direction, player.getHorizontalFacing());

        CompoundNBT compoundtag = itemstack.getTag();
        if (compoundtag != null) {
            EntityType.applyItemNBT(level, player, hangingentity, compoundtag);
        }

        if (hangingentity.onValidSurface()) {
            if (!level.isRemote) {
                hangingentity.playPlaceSound();
                level.addEntity(hangingentity);
            }
            itemstack.shrink(1);
            return ActionResultType.func_233537_a_(level.isRemote);
        }
        return ActionResultType.CONSUME;
    }

    protected boolean mayPlace(PlayerEntity player, Direction clickedFaceDirection, ItemStack itemStack, BlockPos blockPos) {
        return clickedFaceDirection == Direction.UP
                && !World.isOutsideBuildHeight(blockPos)
                && player.canPlayerEdit(blockPos, clickedFaceDirection, itemStack);
    }

    protected interface IceCreamStandFactory<T extends HangingEntity> {
        T create(World level, BlockPos blockpos1, Direction direction, Direction placedDirection);
    }

    protected IceCreamStandFactory<?> getEntityFactory() {
        return IceCreamStand::new;
    }
}
