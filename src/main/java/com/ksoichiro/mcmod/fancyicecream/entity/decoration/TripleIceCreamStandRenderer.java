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

public class TripleIceCreamStandRenderer extends EntityRenderer<TripleIceCreamStand> {
    public static final ResourceLocation STAND_LOCATION = new ResourceLocation(FancyIceCreamMod.MOD_ID, "block/triple_ice_cream_stand");
    private final Minecraft minecraft = Minecraft.getInstance();
    private final ItemRenderer itemRenderer;

    public TripleIceCreamStandRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public ResourceLocation getTextureLocation(TripleIceCreamStand iceCreamStand) {
        return STAND_LOCATION;
    }

    @Override
    public void render(TripleIceCreamStand iceCreamStand, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        var renderNameTagEvent = new net.minecraftforge.client.event.RenderNameTagEvent(iceCreamStand, iceCreamStand.getDisplayName(), this, poseStack, bufferIn, packedLightIn, partialTicks);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(renderNameTagEvent);
        if (renderNameTagEvent.getResult() != net.minecraftforge.eventbus.api.Event.Result.DENY && (renderNameTagEvent.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW || this.shouldShowName(iceCreamStand))) {
            this.renderNameTag(iceCreamStand, renderNameTagEvent.getContent(), poseStack, bufferIn, packedLightIn);
        }

        poseStack.pushPose();
        Direction direction = iceCreamStand.getDirection();
        Vec3 vec3 = this.getRenderOffset(iceCreamStand, partialTicks);
        poseStack.translate(-vec3.x(), -vec3.y(), -vec3.z());
        poseStack.translate((double)direction.getStepX() * 0.46875D, (double)direction.getStepY() * 0.46875D, (double)direction.getStepZ() * 0.46875D);

        poseStack.mulPose(Vector3f.YP.rotationDegrees((float) iceCreamStand.getRotation() * 360.0F / 8.0F));

        ItemStack[] itemstacks = iceCreamStand.getItems();

        BlockRenderDispatcher blockrenderdispatcher = this.minecraft.getBlockRenderer();
        ModelManager modelmanager = blockrenderdispatcher.getBlockModelShaper().getModelManager();
        poseStack.pushPose();
        poseStack.translate(-0.5D, -0.5D, -0.5D);
        blockrenderdispatcher.getModelRenderer().renderModel(poseStack.last(), bufferIn.getBuffer(Sheets.solidBlockSheet()), (BlockState)null, modelmanager.getModel(STAND_LOCATION), 1.0F, 1.0F, 1.0F, packedLightIn, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();

        if (iceCreamStand.hasItems()) {
            double[][] positions = new double[][]{
                    {-3.0D, 0.1D, -2.5D},
                    {3.0D, 0.1D, -2.5D},
                    {0.0D, 0.1D, 3.0D},
            };
            for (int i = 0; i < itemstacks.length; i++) {
                ItemStack itemstack = itemstacks[i];
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

        poseStack.popPose();
    }
}
