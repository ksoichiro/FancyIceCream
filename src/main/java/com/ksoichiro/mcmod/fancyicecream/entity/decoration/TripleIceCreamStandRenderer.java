package com.ksoichiro.mcmod.fancyicecream.entity.decoration;

import com.ksoichiro.mcmod.fancyicecream.main.FancyIceCreamMod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class TripleIceCreamStandRenderer<T extends IceCreamStand> extends IceCreamStandRenderer<T> {
    public static final ResourceLocation STAND_LOCATION = new ResourceLocation(FancyIceCreamMod.MOD_ID, "block/triple_ice_cream_stand");

    public TripleIceCreamStandRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(T iceCreamStand) {
        return STAND_LOCATION;
    }

    @Override
    protected void renderStandItems(T iceCreamStand, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        double[][] positions = new double[][]{
                {-3.0D, 0.1D, -2.5D},
                {3.0D, 0.1D, -2.5D},
                {0.0D, 0.1D, 3.0D},
        };
        List<ItemStack> itemstacks = iceCreamStand.getItems();
        for (int i = 0; i < itemstacks.size(); i++) {
            ItemStack itemstack = itemstacks.get(i);
            if (!itemstack.isEmpty()) {
                poseStack.pushPose();
                poseStack.translate(positions[i][0] / 16.0D, positions[i][1] / 16.0D, positions[i][2] / 16.0D);
                poseStack.scale(0.5F, 0.5F, 0.5F);
                poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
                poseStack.mulPose(Vector3f.XP.rotationDegrees(20.0F));
                poseStack.mulPose(Vector3f.ZP.rotationDegrees(-45.0F));
                this.itemRenderer.renderStatic(itemstack, ItemTransforms.TransformType.FIXED, packedLightIn, OverlayTexture.NO_OVERLAY, poseStack, bufferIn, iceCreamStand.getId());
                poseStack.popPose();
            }
        }
    }
}
