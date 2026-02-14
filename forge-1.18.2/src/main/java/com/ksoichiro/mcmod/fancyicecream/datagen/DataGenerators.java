package com.ksoichiro.mcmod.fancyicecream.datagen;

import com.ksoichiro.mcmod.fancyicecream.main.FancyIceCreamMod;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Please run ./gradlew runData after cleaning run-data or changing definitions.
 */
@Mod.EventBusSubscriber(modid = FancyIceCreamMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(new FancyIceCreamRecipeProvider(generator));
        generator.addProvider(new FancyIceCreamAdvancementProvider(generator, existingFileHelper));
    }
}
