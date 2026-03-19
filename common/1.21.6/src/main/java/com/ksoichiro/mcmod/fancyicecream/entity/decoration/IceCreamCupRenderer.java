package com.ksoichiro.mcmod.fancyicecream.entity.decoration;

import com.ksoichiro.mcmod.fancyicecream.FancyIceCream;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.resources.model.BlockStateDefinitions;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class IceCreamCupRenderer extends IceCreamStandRenderer<IceCreamCup> {
    public IceCreamCupRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected ResourceLocation getModelResourceLoc() {
        return ResourceLocation.fromNamespaceAndPath(FancyIceCream.MOD_ID, "ice_cream_cup");
    }

    @Override
    protected double[][] getTranslations() {
        return new double[][]{
                {0.0D, -5.0D, 0.0D},
        };
    }

    @Override
    protected BlockStateModel getBlockModel(ModelManager modelmanager, ItemStack itemStack) {
        Item item = itemStack.getItem();
        ResourceLocation itemResource = BuiltInRegistries.ITEM.getKey(item);
        if (itemResource != null) {
            // Use scoop-only model (without cone) for cup display
            ResourceLocation scoopResource = ResourceLocation.fromNamespaceAndPath(
                    itemResource.getNamespace(), itemResource.getPath() + "_scoop");
            var definition = BlockStateDefinitions.STATIC_DEFINITIONS.get(scoopResource);
            if (definition != null) {
                BlockState blockstate = definition.any();
                return this.blockRenderer.getBlockModel(blockstate);
            }
        }
        return modelmanager.getBlockModelShaper().getBlockModel(Blocks.STONE.defaultBlockState());
    }
}
