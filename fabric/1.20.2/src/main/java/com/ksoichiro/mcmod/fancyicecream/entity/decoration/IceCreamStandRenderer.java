package com.ksoichiro.mcmod.fancyicecream.entity.decoration;

import com.ksoichiro.mcmod.fancyicecream.FancyIceCream;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class IceCreamStandRenderer<T extends IceCreamStand> extends EntityRenderer<T> {
    public static final ResourceLocation STAND_LOCATION = new ResourceLocation(FancyIceCream.MOD_ID, "block/ice_cream_stand");
    protected final Minecraft minecraft = Minecraft.getInstance();
    protected final ItemRenderer itemRenderer;

    public IceCreamStandRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public ResourceLocation getTextureLocation(T iceCreamStand) {
        return STAND_LOCATION;
    }

    protected ModelResourceLocation getModelResourceLoc() {
        return new ModelResourceLocation(new ResourceLocation(FancyIceCream.MOD_ID, "ice_cream_stand"), "");
    }

    @Override
    public void render(T iceCreamStand, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        super.render(iceCreamStand, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);

        poseStack.pushPose();
        Direction direction = iceCreamStand.getDirection();
        Vec3 vec3 = this.getRenderOffset(iceCreamStand, partialTicks);
        poseStack.translate(-vec3.x(), -vec3.y(), -vec3.z());
        poseStack.translate((double)direction.getStepX() * 0.46875D, (double)direction.getStepY() * 0.46875D, (double)direction.getStepZ() * 0.46875D);

        poseStack.mulPose(Axis.YP.rotationDegrees((float) iceCreamStand.getRotation() * 360.0F / 8.0F));

        BlockRenderDispatcher blockrenderdispatcher = this.minecraft.getBlockRenderer();
        ModelManager modelmanager = blockrenderdispatcher.getBlockModelShaper().getModelManager();
        poseStack.pushPose();
        poseStack.translate(-0.5D, -0.5D, -0.5D);
        blockrenderdispatcher.getModelRenderer().renderModel(poseStack.last(), bufferIn.getBuffer(Sheets.solidBlockSheet()),
            null, modelmanager.getModel(this.getModelResourceLoc()), 1.0F, 1.0F, 1.0F, packedLightIn,
            OverlayTexture.NO_OVERLAY);
        poseStack.popPose();

        if (iceCreamStand.hasItems()) {
            renderStandItems(iceCreamStand, poseStack, bufferIn, packedLightIn);
        }

        poseStack.popPose();
    }

    protected void renderStandItems(T iceCreamStand, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        List<ItemStack> itemstacks = iceCreamStand.getItems();
        double[][] translations = getTranslations();
        for (int i = 0; i < itemstacks.size(); i++) {
            ItemStack itemstack = itemstacks.get(i);
            if (!itemstack.isEmpty()) {
                poseStack.pushPose();
                BlockRenderDispatcher blockrenderdispatcher = this.minecraft.getBlockRenderer();
                ModelManager modelmanager = blockrenderdispatcher.getBlockModelShaper().getModelManager();
                BakedModel model = getBlockModel(modelmanager, itemstack);
                if (model.equals(modelmanager.getMissingModel())) {
                    poseStack.translate(translations[i][0] / 16.0D, translations[i][1] / 16.0D, translations[i][2] / 16.0D);
                    poseStack.scale(0.5F, 0.5F, 0.5F);
                    poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
                    poseStack.mulPose(Axis.XP.rotationDegrees(20.0F));
                    poseStack.mulPose(Axis.ZP.rotationDegrees(-45.0F));
                    this.itemRenderer.renderStatic(itemstack, ItemDisplayContext.FIXED, packedLightIn, OverlayTexture.NO_OVERLAY, poseStack, bufferIn, iceCreamStand.level(), iceCreamStand.getId());
                } else {
                    poseStack.translate(translations[i][0] / 16.0D, translations[i][1] / 16.0D, translations[i][2] / 16.0D);
                    poseStack.scale(0.8F, 0.8F, 0.8F);
                    poseStack.translate(-0.5D, -0.5D, -0.5D);
                    blockrenderdispatcher.getModelRenderer().renderModel(poseStack.last(), bufferIn.getBuffer(Sheets.solidBlockSheet()), null, model, 1.0F, 1.0F, 1.0F, packedLightIn, OverlayTexture.NO_OVERLAY);
                }
                poseStack.popPose();
            }
        }
    }

    protected BakedModel getBlockModel(ModelManager modelmanager, ItemStack itemStack) {
        Item item = itemStack.getItem();
        ResourceLocation itemResource = BuiltInRegistries.ITEM.getKey(item);
        if (itemResource != null) {
            ResourceLocation modelResource = new ResourceLocation(itemResource.getNamespace(), itemResource.getPath());
            ModelResourceLocation modelLocation = new ModelResourceLocation(modelResource, "");
            return modelmanager.getModel(modelLocation);
        }
        ResourceLocation modelResource = new ResourceLocation("minecraft", "stone");
        ModelResourceLocation modelLocation = new ModelResourceLocation(modelResource, "");
        return modelmanager.getModel(modelLocation);
    }

    protected double[][] getTranslations() {
        return new double[][]{
                {0.0D, 0.1D, 0.0D},
        };
    }
}
