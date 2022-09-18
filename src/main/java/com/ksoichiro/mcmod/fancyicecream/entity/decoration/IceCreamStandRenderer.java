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
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class IceCreamStandRenderer<T extends IceCreamStand> extends EntityRenderer<T> {
    public static final ResourceLocation STAND_LOCATION = new ResourceLocation(FancyIceCreamMod.MOD_ID, "block/ice_cream_stand");
    protected final Minecraft minecraft = Minecraft.getInstance();
    protected final net.minecraft.client.renderer.ItemRenderer itemRenderer;

    public IceCreamStandRenderer(EntityRendererManager context) {
        super(context);
        this.itemRenderer = minecraft.getItemRenderer();
    }

    @Override
    public ResourceLocation getEntityTexture(T iceCreamStand) {
        return STAND_LOCATION;
    }

    @Override
    public void render(T iceCreamStand, float entityYaw, float partialTicks, MatrixStack poseStack, IRenderTypeBuffer bufferIn, int packedLightIn) {
        net.minecraftforge.client.event.RenderNameplateEvent renderNameplateEvent = new net.minecraftforge.client.event.RenderNameplateEvent(iceCreamStand, iceCreamStand.getDisplayName(), this, poseStack, bufferIn, packedLightIn);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(renderNameplateEvent);
        if (renderNameplateEvent.getResult() != net.minecraftforge.eventbus.api.Event.Result.DENY && (renderNameplateEvent.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW || this.canRenderName(iceCreamStand))) {
            this.renderName(iceCreamStand, renderNameplateEvent.getContent(), poseStack, bufferIn, packedLightIn);
        }

        poseStack.push();
        Direction direction = iceCreamStand.getHorizontalFacing();
        Vector3d vec3 = this.getRenderOffset(iceCreamStand, partialTicks);
        poseStack.translate(-vec3.getX(), -vec3.getY(), -vec3.getZ());
        poseStack.translate((double)direction.getXOffset() * 0.46875D, (double)direction.getYOffset() * 0.46875D, (double)direction.getZOffset() * 0.46875D);

        poseStack.rotate(Vector3f.YP.rotationDegrees((float) iceCreamStand.getRotation() * 360.0F / 8.0F));

        BlockRendererDispatcher blockrenderdispatcher = this.minecraft.getBlockRendererDispatcher();
        ModelManager modelmanager = blockrenderdispatcher.getBlockModelShapes().getModelManager();
        poseStack.push();
        poseStack.translate(-0.5D, -0.5D, -0.5D);
        blockrenderdispatcher.getBlockModelRenderer().renderModelBrightnessColor(poseStack.getLast(), bufferIn.getBuffer(Atlases.getSolidBlockType()), (BlockState)null, modelmanager.getModel(getEntityTexture(iceCreamStand)), 1.0F, 1.0F, 1.0F, packedLightIn, OverlayTexture.NO_OVERLAY);
        poseStack.pop();

        if (iceCreamStand.hasItems()) {
            renderStandItems(iceCreamStand, poseStack, bufferIn, packedLightIn);
        }

        poseStack.pop();
    }

    protected void renderStandItems(T iceCreamStand, MatrixStack poseStack, IRenderTypeBuffer bufferIn, int packedLightIn) {
        List<ItemStack> itemstacks = iceCreamStand.getItems();
        double[][] translations = getTranslations();
        for (int i = 0; i < itemstacks.size(); i++) {
            ItemStack itemstack = itemstacks.get(i);
            if (!itemstack.isEmpty()) {
                poseStack.push();
                BlockRendererDispatcher blockrenderdispatcher = this.minecraft.getBlockRendererDispatcher();
                ModelManager modelmanager = blockrenderdispatcher.getBlockModelShapes().getModelManager();
                IBakedModel model = getBlockModel(modelmanager, itemstack);
                if (model.equals(modelmanager.getMissingModel())) {
                    poseStack.translate(translations[i][0] / 16.0D, translations[i][1] / 16.0D, translations[i][2] / 16.0D);
                    poseStack.scale(0.5F, 0.5F, 0.5F);
                    poseStack.rotate(Vector3f.YP.rotationDegrees(180.0F));
                    poseStack.rotate(Vector3f.XP.rotationDegrees(20.0F));
                    poseStack.rotate(Vector3f.ZP.rotationDegrees(-45.0F));
                    this.itemRenderer.renderItem(itemstack, ItemCameraTransforms.TransformType.FIXED, packedLightIn, OverlayTexture.NO_OVERLAY, poseStack, bufferIn);
                } else {
                    poseStack.translate(translations[i][0] / 16.0D, translations[i][1] / 16.0D, translations[i][2] / 16.0D);
                    poseStack.scale(0.8F, 0.8F, 0.8F);
                    poseStack.translate(-0.5D, -0.5D, -0.5D);
                    blockrenderdispatcher.getBlockModelRenderer().renderModelBrightnessColor(poseStack.getLast(), bufferIn.getBuffer(Atlases.getSolidBlockType()), (BlockState)null, model, 1.0F, 1.0F, 1.0F, packedLightIn, OverlayTexture.NO_OVERLAY);
                }
                poseStack.pop();
            }
        }
    }

    protected IBakedModel getBlockModel(ModelManager modelmanager, ItemStack itemStack) {
        Item item = itemStack.getItem();
        String namespace = "minecraft";
        ResourceLocation itemResource = ForgeRegistries.ITEMS.getKey(item);
        if (itemResource != null) {
            namespace = itemResource.getNamespace();
        }
        ResourceLocation modelResource = new ResourceLocation(namespace, "block/" + item);
        return modelmanager.getModel(modelResource);
    }

    protected double[][] getTranslations() {
        return new double[][]{
                {0.0D, 0.1D, 0.0D},
        };
    }
}
