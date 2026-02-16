package com.ksoichiro.mcmod.fancyicecream.entity.decoration;

import com.ksoichiro.mcmod.fancyicecream.FancyIceCream;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
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
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class IceCreamStandRenderer<T extends IceCreamStand> extends EntityRenderer<T, IceCreamStandRenderState> {
    protected final Minecraft minecraft = Minecraft.getInstance();
    protected final ItemRenderer itemRenderer;
    protected final BlockRenderDispatcher blockRenderer;
    private BakedModel cachedStandModel = null;

    public IceCreamStandRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = Minecraft.getInstance().getItemRenderer();
        this.blockRenderer = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(IceCreamStandRenderState iceCreamStand, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        super.render(iceCreamStand, poseStack, bufferIn, packedLightIn);

        poseStack.pushPose();
        Direction direction = iceCreamStand.direction;
        Vec3 vec3 = this.getRenderOffset(iceCreamStand);
        poseStack.translate(-vec3.x(), -vec3.y(), -vec3.z());
        poseStack.translate((double)direction.getStepX() * 0.46875D, (double)direction.getStepY() * 0.46875D, (double)direction.getStepZ() * 0.46875D);

        poseStack.mulPose(Axis.YP.rotationDegrees((float) iceCreamStand.rotation * 360.0F / 8.0F));

        // Render the ice cream stand using block model with proper lighting
        poseStack.pushPose();
        poseStack.translate(-0.5D, -0.5D, -0.5D);
        this.blockRenderer.getModelRenderer().renderModel(poseStack.last(), bufferIn.getBuffer(RenderType.entitySolid(getCachedStandModel().getParticleIcon().atlasLocation())),
            null, getCachedStandModel(), 1.0F, 1.0F, 1.0F, packedLightIn,
            OverlayTexture.NO_OVERLAY);
        poseStack.popPose();

        if (iceCreamStand.hasItems) {
            renderStandItems(iceCreamStand, poseStack, bufferIn, packedLightIn);
        }

        poseStack.popPose();
    }

    protected void renderStandItems(IceCreamStandRenderState iceCreamStand, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        List<ItemStack> itemstacks = iceCreamStand.items;
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
                    this.itemRenderer.renderStatic(itemstack, ItemDisplayContext.FIXED, packedLightIn, OverlayTexture.NO_OVERLAY, poseStack, bufferIn, null, 0);
                } else {
                    poseStack.translate(translations[i][0] / 16.0D, translations[i][1] / 16.0D, translations[i][2] / 16.0D);
                    poseStack.scale(0.8F, 0.8F, 0.8F);
                    poseStack.translate(-0.5D, -0.5D, -0.5D);
                    blockrenderdispatcher.getModelRenderer().renderModel(poseStack.last(), bufferIn.getBuffer(RenderType.entitySolid(model.getParticleIcon().atlasLocation())), null, model, 1.0F, 1.0F, 1.0F, packedLightIn, OverlayTexture.NO_OVERLAY);
                }
                poseStack.popPose();
            }
        }
    }

    protected BakedModel getBlockModel(ModelManager modelmanager, ItemStack itemStack) {
        Item item = itemStack.getItem();
        ResourceLocation itemResource = BuiltInRegistries.ITEM.getKey(item);
        if (itemResource != null) {
            ResourceLocation modelResource = ResourceLocation.fromNamespaceAndPath(itemResource.getNamespace(), itemResource.getPath());
            ModelResourceLocation modelLocation = new ModelResourceLocation(modelResource, "");
            return modelmanager.getModel(modelLocation);
        }
        // Fallback to vanilla model path
        ResourceLocation modelResource = ResourceLocation.fromNamespaceAndPath("minecraft", "block/stone");
        ModelResourceLocation modelLocation = new ModelResourceLocation(modelResource, "inventory");
        return modelmanager.getModel(modelLocation);
    }

    protected ModelResourceLocation getModelResourceLoc() {
        return new ModelResourceLocation(ResourceLocation.fromNamespaceAndPath(FancyIceCream.MOD_ID, "ice_cream_stand"), "");
    }

    @Override
    public @NotNull IceCreamStandRenderState createRenderState() {
        return new IceCreamStandRenderState();
    }

    @Override
    public void extractRenderState(T entity, IceCreamStandRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.items = new ArrayList<>(entity.getItems());
        state.rotation = entity.getRotation();
        state.direction = entity.getDirection();
        state.hasItems = entity.hasItems();
        state.itemStack = entity.getFrameItemStack();
    }

    private BakedModel getCachedStandModel() {
        if (cachedStandModel == null) {
            cachedStandModel = loadStandModel();
        }
        return cachedStandModel;
    }

    private BakedModel loadStandModel() {
        ModelResourceLocation modelLocation = getModelResourceLoc();
        ModelManager modelManager = this.blockRenderer.getBlockModelShaper().getModelManager();
        return modelManager.getModel(modelLocation);
    }

    protected double[][] getTranslations() {
        return new double[][]{
                {0.0D, 0.1D, 0.0D},
        };
    }
}
