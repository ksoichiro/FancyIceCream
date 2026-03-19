package com.ksoichiro.mcmod.fancyicecream.entity.decoration;

import com.ksoichiro.mcmod.fancyicecream.main.FancyIceCreamMod;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class IceCreamCupRenderer<T extends IceCreamStand> extends IceCreamStandRenderer<T> {
    public static final ResourceLocation STAND_LOCATION = new ResourceLocation(FancyIceCreamMod.MOD_ID, "block/ice_cream_cup");

    public IceCreamCupRenderer(EntityRendererManager context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(T iceCreamStand) {
        return STAND_LOCATION;
    }

    @Override
    protected double[][] getTranslations() {
        return new double[][]{
                {0.0D, -5.0D, 0.0D},
        };
    }

    @Override
    protected IBakedModel getBlockModel(ModelManager modelmanager, ItemStack itemStack) {
        Item item = itemStack.getItem();
        ResourceLocation itemResource = ForgeRegistries.ITEMS.getKey(item);
        if (itemResource != null) {
            ResourceLocation modelResource = new ResourceLocation(itemResource.getNamespace(), "block/" + itemResource.getPath() + "_scoop");
            IBakedModel model = modelmanager.getModel(modelResource);
            if (!model.equals(modelmanager.getMissingModel())) {
                return model;
            }
        }
        return modelmanager.getBlockModelShaper().getBlockModel(net.minecraft.block.Blocks.STONE.defaultBlockState());
    }
}
