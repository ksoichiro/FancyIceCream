package com.ksoichiro.mcmod.fancyicecream.entity.decoration;

import com.ksoichiro.mcmod.fancyicecream.main.FancyIceCreamMod;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

public class IceCreamCupRenderer<T extends IceCreamStand> extends IceCreamStandRenderer<T> {
    public static final ResourceLocation STAND_LOCATION = ResourceLocation.fromNamespaceAndPath(FancyIceCreamMod.MOD_ID, "block/ice_cream_cup");

    public IceCreamCupRenderer(EntityRendererProvider.Context context) {
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
    protected BakedModel getBlockModel(ModelManager modelmanager, ItemStack itemStack) {
        Item item = itemStack.getItem();
        ResourceLocation itemResource = ForgeRegistries.ITEMS.getKey(item);
        if (itemResource != null) {
            ResourceLocation modelResource = ResourceLocation.fromNamespaceAndPath(itemResource.getNamespace(), "block/" + itemResource.getPath() + "_scoop");
            BakedModel model = modelmanager.getModel(modelResource);
            if (!model.equals(modelmanager.getMissingModel())) {
                return model;
            }
        }
        return modelmanager.getBlockModelShaper().getBlockModel(Blocks.STONE.defaultBlockState());
    }
}
