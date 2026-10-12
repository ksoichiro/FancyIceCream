package com.ksoichiro.mcmod.fancyicecream.entity.decoration;

import com.ksoichiro.mcmod.fancyicecream.FancyIceCream;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

public class TripleIceCreamStandRenderer extends IceCreamStandRenderer<TripleIceCreamStand> {
    public TripleIceCreamStandRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected Identifier getModelResourceLoc() {
        return Identifier.fromNamespaceAndPath(FancyIceCream.MOD_ID, "triple_ice_cream_stand");
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
