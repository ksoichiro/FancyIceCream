package com.ksoichiro.mcmod.fancyicecream.registry;

import com.ksoichiro.mcmod.fancyicecream.entity.decoration.StandRenderer;
import com.ksoichiro.mcmod.fancyicecream.item.ChocoChipIceCream;
import com.ksoichiro.mcmod.fancyicecream.item.ChocolateIceCream;
import com.ksoichiro.mcmod.fancyicecream.item.Stand;
import com.ksoichiro.mcmod.fancyicecream.item.VanillaIceCream;
import com.ksoichiro.mcmod.fancyicecream.main.FancyIceCreamMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(FancyIceCreamMod.MOD_ID)
public class FancyIceCreamModItems {
    public static final VanillaIceCream VANILLA_ICE_CREAM = new VanillaIceCream();
    public static final ChocoChipIceCream CHOCO_CHIP_ICE_CREAM = new ChocoChipIceCream();
    public static final ChocolateIceCream CHOCOLATE_ICE_CREAM = new ChocolateIceCream();
    public static final Stand STAND = new Stand();

    @Mod.EventBusSubscriber(modid = FancyIceCreamMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Registerer {
        @SubscribeEvent
        public static void registerItem(final RegistryEvent.Register<Item> event) {
            event.getRegistry().registerAll(
                    VANILLA_ICE_CREAM,
                    CHOCO_CHIP_ICE_CREAM,
                    CHOCOLATE_ICE_CREAM,
                    STAND);
        }

        @SubscribeEvent
        public static void registerModels(final ModelRegistryEvent event) {
            ForgeModelBakery.addSpecialModel(StandRenderer.STAND_LOCATION);
        }
    }
}
