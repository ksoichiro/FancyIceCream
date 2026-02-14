package com.ksoichiro.mcmod.fancyicecream.datagen;

import com.ksoichiro.mcmod.fancyicecream.main.FancyIceCreamMod;
import com.ksoichiro.mcmod.fancyicecream.registry.FancyIceCreamModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class FancyIceCreamAdvancementProvider extends ForgeAdvancementProvider {
    public FancyIceCreamAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, java.util.List.of(new IceCreamAdvancements()));
    }

    public static class IceCreamAdvancements implements AdvancementGenerator {
        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
            AdvancementHolder root = Advancement.Builder.advancement()
                .display(FancyIceCreamModItems.VANILLA_ICE_CREAM.get(),
                    Component.translatable("advancement.fancyicecream.root.title"),
                    Component.translatable("advancement.fancyicecream.root.description"),
                    new ResourceLocation("textures/gui/advancements/backgrounds/adventure.png"),
                    FrameType.TASK,
                    false,
                    false,
                    false)
                .addCriterion("has_vanilla_ice_cream", InventoryChangeTrigger.TriggerInstance.hasItems(FancyIceCreamModItems.VANILLA_ICE_CREAM.get()))
                .save(saver, new ResourceLocation(FancyIceCreamMod.MOD_ID, "root"));

            Advancement.Builder.advancement()
                .parent(root)
                .display(FancyIceCreamModItems.GOLDEN_APPLE_ICE_CREAM.get(),
                    Component.translatable("advancement.fancyicecream.ice_cream_master.title"),
                    Component.translatable("advancement.fancyicecream.ice_cream_master.description"),
                    null,
                    FrameType.CHALLENGE,
                    true,
                    true,
                    false)
                .addCriterion("has_vanilla", InventoryChangeTrigger.TriggerInstance.hasItems(FancyIceCreamModItems.VANILLA_ICE_CREAM.get()))
                .addCriterion("has_apple", InventoryChangeTrigger.TriggerInstance.hasItems(FancyIceCreamModItems.APPLE_ICE_CREAM.get()))
                .addCriterion("has_choco_chip", InventoryChangeTrigger.TriggerInstance.hasItems(FancyIceCreamModItems.CHOCO_CHIP_ICE_CREAM.get()))
                .addCriterion("has_chocolate", InventoryChangeTrigger.TriggerInstance.hasItems(FancyIceCreamModItems.CHOCOLATE_ICE_CREAM.get()))
                .addCriterion("has_glow_berry", InventoryChangeTrigger.TriggerInstance.hasItems(FancyIceCreamModItems.GLOW_BERRY_ICE_CREAM.get()))
                .addCriterion("has_golden_apple", InventoryChangeTrigger.TriggerInstance.hasItems(FancyIceCreamModItems.GOLDEN_APPLE_ICE_CREAM.get()))
                .addCriterion("has_honey", InventoryChangeTrigger.TriggerInstance.hasItems(FancyIceCreamModItems.HONEY_ICE_CREAM.get()))
                .addCriterion("has_sweet_berry", InventoryChangeTrigger.TriggerInstance.hasItems(FancyIceCreamModItems.SWEET_BERRY_ICE_CREAM.get()))
                .save(saver, new ResourceLocation(FancyIceCreamMod.MOD_ID, "ice_cream_master"));

            AdvancementHolder decorator = Advancement.Builder.advancement()
                .parent(root)
                .display(FancyIceCreamModItems.ICE_CREAM_STAND.get(),
                    Component.translatable("advancement.fancyicecream.decorator.title"),
                    Component.translatable("advancement.fancyicecream.decorator.description"),
                    null,
                    FrameType.TASK,
                    true,
                    true,
                    false)
                .addCriterion("has_ice_cream_stand", InventoryChangeTrigger.TriggerInstance.hasItems(FancyIceCreamModItems.ICE_CREAM_STAND.get()))
                .save(saver, new ResourceLocation(FancyIceCreamMod.MOD_ID, "decorator"));

            Advancement.Builder.advancement()
                .parent(decorator)
                .display(FancyIceCreamModItems.TRIPLE_ICE_CREAM_STAND.get(),
                    Component.translatable("advancement.fancyicecream.triple_decorator.title"),
                    Component.translatable("advancement.fancyicecream.triple_decorator.description"),
                    null,
                    FrameType.GOAL,
                    true,
                    true,
                    false)
                .addCriterion("has_triple_ice_cream_stand", InventoryChangeTrigger.TriggerInstance.hasItems(FancyIceCreamModItems.TRIPLE_ICE_CREAM_STAND.get()))
                .save(saver, new ResourceLocation(FancyIceCreamMod.MOD_ID, "triple_decorator"));
        }
    }
}
