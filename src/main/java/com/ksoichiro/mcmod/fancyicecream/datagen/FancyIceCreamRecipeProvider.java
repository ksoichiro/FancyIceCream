package com.ksoichiro.mcmod.fancyicecream.datagen;

import com.ksoichiro.mcmod.fancyicecream.registry.FancyIceCreamModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class FancyIceCreamRecipeProvider extends RecipeProvider {
    public FancyIceCreamRecipeProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        // Base vanilla ice cream recipe
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, FancyIceCreamModItems.VANILLA_ICE_CREAM.get(), 3)
            .pattern("ABA")
            .pattern("CBC")
            .pattern(" D ")
            .define('A', Items.MILK_BUCKET)
            .define('B', Items.EGG)
            .define('C', Items.SUGAR)
            .define('D', Items.WHEAT)
            .unlockedBy("has_milk_bucket", has(Items.MILK_BUCKET))
            .save(recipeOutput);

        // Apple ice cream recipe
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, FancyIceCreamModItems.APPLE_ICE_CREAM.get())
            .pattern("A")
            .pattern("B")
            .define('A', Items.APPLE)
            .define('B', FancyIceCreamModItems.VANILLA_ICE_CREAM.get())
            .unlockedBy("has_vanilla_ice_cream", has(FancyIceCreamModItems.VANILLA_ICE_CREAM.get()))
            .save(recipeOutput);

        // Choco chip ice cream recipe
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, FancyIceCreamModItems.CHOCO_CHIP_ICE_CREAM.get())
            .pattern("A")
            .pattern("B")
            .define('A', Items.COCOA_BEANS)
            .define('B', FancyIceCreamModItems.VANILLA_ICE_CREAM.get())
            .unlockedBy("has_vanilla_ice_cream", has(FancyIceCreamModItems.VANILLA_ICE_CREAM.get()))
            .save(recipeOutput);

        // Chocolate ice cream recipe
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, FancyIceCreamModItems.CHOCOLATE_ICE_CREAM.get())
            .pattern("AA")
            .pattern("B ")
            .define('A', Items.COCOA_BEANS)
            .define('B', FancyIceCreamModItems.VANILLA_ICE_CREAM.get())
            .unlockedBy("has_vanilla_ice_cream", has(FancyIceCreamModItems.VANILLA_ICE_CREAM.get()))
            .save(recipeOutput);

        // Glow berry ice cream recipe
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, FancyIceCreamModItems.GLOW_BERRY_ICE_CREAM.get())
            .pattern("A")
            .pattern("B")
            .define('A', Items.GLOW_BERRIES)
            .define('B', FancyIceCreamModItems.VANILLA_ICE_CREAM.get())
            .unlockedBy("has_vanilla_ice_cream", has(FancyIceCreamModItems.VANILLA_ICE_CREAM.get()))
            .save(recipeOutput);

        // Golden apple ice cream recipe
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, FancyIceCreamModItems.GOLDEN_APPLE_ICE_CREAM.get())
            .pattern("A")
            .pattern("B")
            .define('A', Items.GOLDEN_APPLE)
            .define('B', FancyIceCreamModItems.VANILLA_ICE_CREAM.get())
            .unlockedBy("has_vanilla_ice_cream", has(FancyIceCreamModItems.VANILLA_ICE_CREAM.get()))
            .save(recipeOutput);

        // Honey ice cream recipe
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, FancyIceCreamModItems.HONEY_ICE_CREAM.get())
            .pattern("A")
            .pattern("B")
            .define('A', Items.HONEY_BOTTLE)
            .define('B', FancyIceCreamModItems.VANILLA_ICE_CREAM.get())
            .unlockedBy("has_vanilla_ice_cream", has(FancyIceCreamModItems.VANILLA_ICE_CREAM.get()))
            .save(recipeOutput);

        // Ice cream stand recipe
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, FancyIceCreamModItems.ICE_CREAM_STAND.get())
            .pattern("A A")
            .pattern(" A ")
            .define('A', Items.IRON_NUGGET)
            .unlockedBy("has_iron_nugget", has(Items.IRON_NUGGET))
            .save(recipeOutput);

        // Triple ice cream stand recipe
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, FancyIceCreamModItems.TRIPLE_ICE_CREAM_STAND.get())
            .pattern("A A")
            .pattern(" B ")
            .define('A', Items.IRON_NUGGET)
            .define('B', FancyIceCreamModItems.ICE_CREAM_STAND.get())
            .unlockedBy("has_ice_cream_stand", has(FancyIceCreamModItems.ICE_CREAM_STAND.get()))
            .save(recipeOutput);
    }
}
