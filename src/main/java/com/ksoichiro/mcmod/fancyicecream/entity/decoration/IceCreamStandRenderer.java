package com.ksoichiro.mcmod.fancyicecream.entity.decoration;

import com.ksoichiro.mcmod.fancyicecream.main.FancyIceCreamMod;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

public class IceCreamStandRenderer extends EntityRenderer<IceCreamStand> {
    public static final ResourceLocation STAND_LOCATION = new ResourceLocation(FancyIceCreamMod.MOD_ID, "block/ice_cream_stand");
    private final Minecraft minecraft = Minecraft.getInstance();
    private final net.minecraft.client.renderer.ItemRenderer itemRenderer;

    public IceCreamStandRenderer(EntityRendererManager context) {
        super(context);
        this.itemRenderer = minecraft.getItemRenderer();
    }

    @Override
    public ResourceLocation getTextureLocation(IceCreamStand iceCreamStand) {
        return STAND_LOCATION;
    }

    @Override
    public void render(IceCreamStand iceCreamStand, float entityYaw, float partialTicks, MatrixStack poseStack, IRenderTypeBuffer bufferIn, int packedLightIn) {
        net.minecraftforge.client.event.RenderNameplateEvent renderNameplateEvent = new net.minecraftforge.client.event.RenderNameplateEvent(iceCreamStand, iceCreamStand.getDisplayName(), this, poseStack, bufferIn, packedLightIn, partialTicks);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(renderNameplateEvent);
        if (renderNameplateEvent.getResult() != net.minecraftforge.eventbus.api.Event.Result.DENY && (renderNameplateEvent.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW || this.shouldShowName(iceCreamStand))) {
            this.renderNameTag(iceCreamStand, renderNameplateEvent.getContent(), poseStack, bufferIn, packedLightIn);
        }

        poseStack.pushPose();
        Direction direction = iceCreamStand.getDirection();
        Vector3d vec3 = this.getRenderOffset(iceCreamStand, partialTicks);
        poseStack.translate(-vec3.x(), -vec3.y(), -vec3.z());
        poseStack.translate((double)direction.getStepX() * 0.46875D, (double)direction.getStepY() * 0.46875D, (double)direction.getStepZ() * 0.46875D);

        poseStack.mulPose(Vector3f.YP.rotationDegrees((float) iceCreamStand.getRotation() * 360.0F / 8.0F));

        ItemStack itemstack = iceCreamStand.getItem();

        BlockRendererDispatcher blockrenderdispatcher = this.minecraft.getBlockRenderer();
        ModelManager modelmanager = blockrenderdispatcher.getBlockModelShaper().getModelManager();
        poseStack.pushPose();
        poseStack.translate(-0.5D, -0.5D, -0.5D);
        blockrenderdispatcher.getModelRenderer().renderModel(poseStack.last(), bufferIn.getBuffer(Atlases.solidBlockSheet()), (BlockState)null, modelmanager.getModel(STAND_LOCATION), 1.0F, 1.0F, 1.0F, packedLightIn, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();

        if (!itemstack.isEmpty()) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
            poseStack.translate(0.0D, 0.1D, -0.0D);
            poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
            poseStack.mulPose(Vector3f.XP.rotationDegrees(20.0F));
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(-45.0F));
            this.itemRenderer.renderStatic(itemstack, ItemCameraTransforms.TransformType.FIXED, packedLightIn, OverlayTexture.NO_OVERLAY, poseStack, bufferIn);
        }

        poseStack.popPose();
    }
}
