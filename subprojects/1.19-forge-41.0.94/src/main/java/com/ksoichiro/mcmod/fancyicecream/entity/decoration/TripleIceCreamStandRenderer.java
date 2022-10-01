package com.ksoichiro.mcmod.fancyicecream.entity.decoration;

import com.ksoichiro.mcmod.fancyicecream.FancyIceCreamModInfo;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class TripleIceCreamStandRenderer<T extends IceCreamStand> extends IceCreamStandRenderer<T> {
    public static final ResourceLocation STAND_LOCATION = new ResourceLocation(FancyIceCreamModInfo.MOD_ID, "block/triple_ice_cream_stand");

    public TripleIceCreamStandRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(T iceCreamStand) {
        return STAND_LOCATION;
    }

    @Override
    protected double[][] getTranslations() {
        return new double[][]{
                {-3.0D, 0.1D, -2.5D},
                {3.0D, 0.1D, -2.5D},
                {0.0D, 0.1D, 3.0D},
        };
    }
}
