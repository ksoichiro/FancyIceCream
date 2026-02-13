package com.ksoichiro.mcmod.fancyicecream.datagen;

import com.ksoichiro.mcmod.fancyicecream.main.FancyIceCreamMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

/**
 * Please run ./gradlew runData after cleaning run-data or changing definitions.
 */
@Mod.EventBusSubscriber(modid = FancyIceCreamMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new RecipeProvider.Runner(packOutput, lookupProvider) {
            @Override
            protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput recipeOutput) {
                return new FancyIceCreamRecipeProvider(registries, recipeOutput);
            }

            @Override
            public String getName() {
                return "FancyIceCreamRecipes";
            }
        });
        generator.addProvider(event.includeServer(), new FancyIceCreamAdvancementProvider(packOutput, lookupProvider, existingFileHelper));
    }
}
