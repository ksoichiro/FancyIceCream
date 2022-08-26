package com.ksoichiro.mcmod.fancyicecream.entity.decoration;

import com.ksoichiro.mcmod.fancyicecream.main.FancyIceCreamMod;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StandRenderer extends EntityRenderer<Stand> {
    // blockstate map property is not found
//    private static final ModelResourceLocation STAND_LOCATION = new ModelResourceLocation("minecraft:item_frame", "map=false");
    public static final ResourceLocation STAND_LOCATION = new ResourceLocation(FancyIceCreamMod.MOD_ID, "block/stand");
//    public static final ModelResourceLocation STAND_LOCATION = new ModelResourceLocation(new ResourceLocation(FancyIceCreamMod.MOD_ID, "stand"), "map=false");
    private static final ModelResourceLocation FRAME_LOCATION = new ModelResourceLocation("item_frame", "map=false");
    private final Minecraft minecraft = Minecraft.getInstance();
//    private final ItemRenderer itemRenderer;
    private static final Logger LOGGER = LogManager.getLogger();

    public StandRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public ResourceLocation getTextureLocation(Stand p_114482_) {
        return new ResourceLocation(FancyIceCreamMod.MOD_ID, "textures/entity/stand.png");
    }

    @Override
    //   public void render(T p_115076_, float p_115077_, float p_115078_, PoseStack p_115079_, MultiBufferSource p_115080_, int p_115081_) {
    public void render(Stand stand, float p_115077_, float p_115078_, PoseStack p_115079_, MultiBufferSource p_115080_, int p_115081_) {
        // from EntityRender#render()
        net.minecraftforge.client.event.RenderNameplateEvent renderNameplateEvent = new net.minecraftforge.client.event.RenderNameplateEvent(stand, stand.getDisplayName(), this, p_115079_, p_115080_, p_115081_, p_115078_);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(renderNameplateEvent);
        if (renderNameplateEvent.getResult() != net.minecraftforge.eventbus.api.Event.Result.DENY && (renderNameplateEvent.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW || this.shouldShowName(stand))) {
            this.renderNameTag(stand, renderNameplateEvent.getContent(), p_115079_, p_115080_, p_115081_);
        }

        p_115079_.pushPose();
        Direction direction = stand.getDirection();
        Vec3 vec3 = this.getRenderOffset(stand, p_115078_);
        p_115079_.translate(-vec3.x(), -vec3.y(), -vec3.z());
        double d0 = 0.46875D;
        p_115079_.translate((double)direction.getStepX() * 0.46875D, (double)direction.getStepY() * 0.46875D, (double)direction.getStepZ() * 0.46875D);
        p_115079_.mulPose(Vector3f.XP.rotationDegrees(stand.getXRot()));
        p_115079_.mulPose(Vector3f.YP.rotationDegrees(180.0F - stand.getYRot()));
        boolean flag = stand.isInvisible();
        ItemStack itemstack = stand.getItem();
        if (!flag) {
            BlockRenderDispatcher blockrenderdispatcher = this.minecraft.getBlockRenderer();
            ModelManager modelmanager = blockrenderdispatcher.getBlockModelShaper().getModelManager();
//            LOGGER.info("StandRenderer#render(): STAND_LOCATION: {}", STAND_LOCATION);
//            LOGGER.info("StandRenderer#render(): FRAME_LOCATION: {}", FRAME_LOCATION);
//            ModelResourceLocation modelresourcelocation = STAND_LOCATION; // not found?
            ResourceLocation modelresourcelocation = STAND_LOCATION; // not found?
//            ResourceLocation modelresourcelocation = new ResourceLocation(FancyIceCreamMod.MOD_ID, "stand"); // not found?
//            ModelResourceLocation modelresourcelocation = FRAME_LOCATION;
            p_115079_.pushPose();
            p_115079_.translate(-0.5D, -0.5D, -0.5D);
            blockrenderdispatcher.getModelRenderer().renderModel(p_115079_.last(), p_115080_.getBuffer(Sheets.solidBlockSheet()), (BlockState)null, modelmanager.getModel(modelresourcelocation), 1.0F, 1.0F, 1.0F, p_115081_, OverlayTexture.NO_OVERLAY);
            p_115079_.popPose();
        }

        if (!itemstack.isEmpty()) {
            MapItemSavedData mapitemsaveddata = MapItem.getSavedData(itemstack, stand.level);
            if (flag) {
                p_115079_.translate(0.0D, 0.0D, 0.5D);
            } else {
                p_115079_.translate(0.0D, 0.0D, 0.4375D);
            }

            int j = mapitemsaveddata != null ? stand.getRotation() % 4 * 2 : stand.getRotation();
            p_115079_.mulPose(Vector3f.ZP.rotationDegrees((float)j * 360.0F / 8.0F));
//            if (!net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderItemInFrameEvent(stand, this, p_115079_, p_115080_, p_115081_))) {
//                if (mapitemsaveddata != null) {
//                    p_115079_.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
//                    float f = 0.0078125F;
//                    p_115079_.scale(0.0078125F, 0.0078125F, 0.0078125F);
//                    p_115079_.translate(-64.0D, -64.0D, 0.0D);
//                    Integer integer = MapItem.getMapId(itemstack);
//                    p_115079_.translate(0.0D, 0.0D, -1.0D);
//                    if (mapitemsaveddata != null) {
//                        int i = this.getLightVal(stand, 15728850, p_115081_);
//                        this.minecraft.gameRenderer.getMapRenderer().render(p_115079_, p_115080_, integer, mapitemsaveddata, true, i);
//                    }
//                } else {
//                    int k = this.getLightVal(stand, 15728880, p_115081_);
//                    p_115079_.scale(0.5F, 0.5F, 0.5F);
//                    this.itemRenderer.renderStatic(itemstack, ItemTransforms.TransformType.FIXED, k, OverlayTexture.NO_OVERLAY, p_115079_, p_115080_, stand.getId());
//                }
//            }
        }

        p_115079_.popPose();
    }
}
