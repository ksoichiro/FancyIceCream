package com.ksoichiro.mcmod.fancyicecream.entity.decoration;

import com.ksoichiro.mcmod.fancyicecream.entity.FancyIceCreamModEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class IceCreamCup extends IceCreamStand {
    public static final RegistryObject<Item> ICE_CREAM_CUP = RegistryObject.create(ResourceLocation.parse("fancyicecream:ice_cream_cup"), ForgeRegistries.ITEMS);

    public IceCreamCup(EntityType<? extends HangingEntity> entityType, Level level) {
        super(entityType, level);
    }

    public IceCreamCup(Level level, BlockPos pos, Direction direction, Direction placedDirection) {
        super(FancyIceCreamModEntityType.ICE_CREAM_CUP.get(), level, pos, direction, placedDirection);
    }

    @Override
    public ItemStack getFrameItemStack() {
        return new ItemStack(ICE_CREAM_CUP.get());
    }
}
