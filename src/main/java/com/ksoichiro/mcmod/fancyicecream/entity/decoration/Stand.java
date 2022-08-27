package com.ksoichiro.mcmod.fancyicecream.entity.decoration;

import com.ksoichiro.mcmod.fancyicecream.entity.FancyIceCreamModEntityType;
import com.ksoichiro.mcmod.fancyicecream.registry.FancyIceCreamModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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

public class Stand extends ItemFrame {
    public static final TagKey<Item> ICE_CREAM_TAG = ItemTags.create(new ResourceLocation("fancyicecreammod", "ice_cream"));

    public Stand(EntityType<Stand> standEntityType, Level level) {
        super(FancyIceCreamModEntityType.STAND, level);
    }

    public Stand(Level level, BlockPos pos, Direction direction) {
        super(FancyIceCreamModEntityType.STAND, level, pos, direction);
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
}
