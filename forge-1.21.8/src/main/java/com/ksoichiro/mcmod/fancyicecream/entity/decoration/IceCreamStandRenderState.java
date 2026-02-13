package com.ksoichiro.mcmod.fancyicecream.entity.decoration;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class IceCreamStandRenderState extends EntityRenderState {
    public List<ItemStack> items;
    public int rotation;
    public Direction direction;
    public ItemStack itemStack = ItemStack.EMPTY;
    public boolean hasItems;
}
