package com.ksoichiro.mcmod.fancyicecream.entity.decoration;

import com.ksoichiro.mcmod.fancyicecream.main.FancyIceCreamMod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class StandRenderer extends EntityRenderer<Stand> {
    public static final ResourceLocation STAND_LOCATION = new ResourceLocation(FancyIceCreamMod.MOD_ID, "block/stand");
    private final Minecraft minecraft = Minecraft.getInstance();
    private final ItemRenderer itemRenderer;

    public StandRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public ResourceLocation getTextureLocation(Stand stand) {
        return STAND_LOCATION;
    }

    @Override
    public void render(Stand stand, float p_115077_, float p_115078_, PoseStack poseStack, MultiBufferSource p_115080_, int p_115081_) {
        net.minecraftforge.client.event.RenderNameplateEvent renderNameplateEvent = new net.minecraftforge.client.event.RenderNameplateEvent(stand, stand.getDisplayName(), this, poseStack, p_115080_, p_115081_, p_115078_);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(renderNameplateEvent);
        if (renderNameplateEvent.getResult() != net.minecraftforge.eventbus.api.Event.Result.DENY && (renderNameplateEvent.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW || this.shouldShowName(stand))) {
            this.renderNameTag(stand, renderNameplateEvent.getContent(), poseStack, p_115080_, p_115081_);
        }

        poseStack.pushPose();
        Direction direction = stand.getDirection();
        Vec3 vec3 = this.getRenderOffset(stand, p_115078_);
        poseStack.translate(-vec3.x(), -vec3.y(), -vec3.z());
        poseStack.translate((double)direction.getStepX() * 0.46875D, (double)direction.getStepY() * 0.46875D, (double)direction.getStepZ() * 0.46875D);
        ItemStack itemstack = stand.getItem();

        BlockRenderDispatcher blockrenderdispatcher = this.minecraft.getBlockRenderer();
        ModelManager modelmanager = blockrenderdispatcher.getBlockModelShaper().getModelManager();
        poseStack.pushPose();
        poseStack.translate(-0.5D, -0.5D, -0.5D);
        blockrenderdispatcher.getModelRenderer().renderModel(poseStack.last(), p_115080_.getBuffer(Sheets.solidBlockSheet()), (BlockState)null, modelmanager.getModel(STAND_LOCATION), 1.0F, 1.0F, 1.0F, p_115081_, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();

        if (!itemstack.isEmpty()) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
            poseStack.translate(0.0D, 0.1D, -0.0D);
            poseStack.mulPose(Vector3f.XP.rotationDegrees(-20.0F));
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(-45.0F));
            this.itemRenderer.renderStatic(itemstack, ItemTransforms.TransformType.FIXED, p_115081_, OverlayTexture.NO_OVERLAY, poseStack, p_115080_, stand.getId());
        }

        poseStack.popPose();
    }
}
