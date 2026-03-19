package com.ksoichiro.mcmod.fancyicecream.entity.decoration;

import com.ksoichiro.mcmod.fancyicecream.FancyIceCream;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

public class IceCreamCupRenderer extends IceCreamStandRenderer<IceCreamCup> {
    public IceCreamCupRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected ModelResourceLocation getModelResourceLoc() {
        return new ModelResourceLocation(ResourceLocation.fromNamespaceAndPath(FancyIceCream.MOD_ID, "ice_cream_cup"), "");
    }

    @Override
    protected double[][] getTranslations() {
        return new double[][]{
                {0.0D, -5.0D, 0.0D},
        };
    }

    @Override
    protected BakedModel getBlockModel(ModelManager modelmanager, ItemStack itemStack) {
        Item item = itemStack.getItem();
        ResourceLocation itemResource = BuiltInRegistries.ITEM.getKey(item);
        if (itemResource != null) {
            ResourceLocation scoopResource = ResourceLocation.fromNamespaceAndPath(
                    itemResource.getNamespace(), itemResource.getPath() + "_scoop");
            ModelResourceLocation modelLocation = new ModelResourceLocation(scoopResource, "");
            BakedModel model = modelmanager.getModel(modelLocation);
            if (!model.equals(modelmanager.getMissingModel())) {
                return model;
            }
        }
        return modelmanager.getBlockModelShaper().getBlockModel(Blocks.STONE.defaultBlockState());
    }
}
