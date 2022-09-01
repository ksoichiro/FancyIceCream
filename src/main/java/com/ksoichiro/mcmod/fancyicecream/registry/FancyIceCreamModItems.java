package com.ksoichiro.mcmod.fancyicecream.registry;

import com.ksoichiro.mcmod.fancyicecream.entity.decoration.IceCreamStandRenderer;
import com.ksoichiro.mcmod.fancyicecream.item.ChocoChipIceCream;
import com.ksoichiro.mcmod.fancyicecream.item.ChocolateIceCream;
import com.ksoichiro.mcmod.fancyicecream.item.IceCreamStandItem;
import com.ksoichiro.mcmod.fancyicecream.item.VanillaIceCream;
import com.ksoichiro.mcmod.fancyicecream.main.FancyIceCreamMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class FancyIceCreamModItems {
    public static final Item VANILLA_ICE_CREAM = registerItem("vanilla_ice_cream", new VanillaIceCream());
    public static final Item CHOCO_CHIP_ICE_CREAM = registerItem("choco_chip_ice_cream", new ChocoChipIceCream());
    public static final Item CHOCOLATE_ICE_CREAM = registerItem("chocolate_ice_cream", new ChocolateIceCream());
    public static final Item ICE_CREAM_STAND = registerItem("ice_cream_stand", new IceCreamStandItem());

    private static Item registerItem(String name, Item item) {
        return item.setRegistryName(name);
    }

    @Mod.EventBusSubscriber(modid = FancyIceCreamMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Registerer {
        @SubscribeEvent
        public static void registerItem(final RegistryEvent.Register<Item> event) {
            event.getRegistry().registerAll(
                    VANILLA_ICE_CREAM,
                    CHOCO_CHIP_ICE_CREAM,
                    CHOCOLATE_ICE_CREAM,
                    ICE_CREAM_STAND);
        }

        @SubscribeEvent
        public static void registerModels(final ModelRegistryEvent event) {
            ForgeModelBakery.addSpecialModel(IceCreamStandRenderer.STAND_LOCATION);
        }
    }
}
