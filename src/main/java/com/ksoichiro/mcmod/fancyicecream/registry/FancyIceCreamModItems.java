package com.ksoichiro.mcmod.fancyicecream.registry;

import com.ksoichiro.mcmod.fancyicecream.item.*;
import com.ksoichiro.mcmod.fancyicecream.main.FancyIceCreamMod;
import net.minecraft.client.resources.model.BlockStateDefinitions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public class FancyIceCreamModItems {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FancyIceCreamMod.MOD_ID);

    public static final RegistryObject<Item> VANILLA_ICE_CREAM = registerItem("vanilla_ice_cream", VanillaIceCream::new);
    public static final RegistryObject<Item> APPLE_ICE_CREAM = registerItem("apple_ice_cream", AppleIceCream::new);
    public static final RegistryObject<Item> CHOCO_CHIP_ICE_CREAM = registerItem("choco_chip_ice_cream", ChocoChipIceCream::new);
    public static final RegistryObject<Item> CHOCOLATE_ICE_CREAM = registerItem("chocolate_ice_cream", ChocolateIceCream::new);
    public static final RegistryObject<Item> GLOW_BERRY_ICE_CREAM = registerItem("glow_berry_ice_cream", GlowBerryIceCream::new);
    public static final RegistryObject<Item> GOLDEN_APPLE_ICE_CREAM = registerItem("golden_apple_ice_cream", GoldenAppleIceCream::new);
    public static final RegistryObject<Item> HONEY_ICE_CREAM = registerItem("honey_ice_cream", HoneyIceCream::new);
    public static final RegistryObject<Item> ICE_CREAM_STAND = registerItem("ice_cream_stand", IceCreamStandItem::new);
    public static final RegistryObject<Item> TRIPLE_ICE_CREAM_STAND = registerItem("triple_ice_cream_stand", TripleIceCreamStandItem::new);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    private static RegistryObject<Item> registerItem(String name, Function<String, Item> item) {
        return ITEMS.register(name, () -> item.apply(name));
    }

    @Mod.EventBusSubscriber(modid = FancyIceCreamMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Registerer {
        public static void registerModels() {
            // Since ModelEvent.RegisterAdditional has been removed in 1.21.3,
            // we have to register our models by replacing vanilla definitions.
            // This map is private and is made accessible using AT.
            Map<ResourceLocation, StateDefinition<Block, BlockState>> tmp = new LinkedHashMap<>(BlockStateDefinitions.STATIC_DEFINITIONS);
            registerModel(tmp, "vanilla_ice_cream");
            registerModel(tmp, "apple_ice_cream");
            registerModel(tmp, "choco_chip_ice_cream");
            registerModel(tmp, "chocolate_ice_cream");
            registerModel(tmp, "glow_berry_ice_cream");
            registerModel(tmp, "golden_apple_ice_cream");
            registerModel(tmp, "honey_ice_cream");
            registerModel(tmp, "ice_cream_stand");
            registerModel(tmp, "triple_ice_cream_stand");
            BlockStateDefinitions.STATIC_DEFINITIONS = tmp;
        }

        private static void registerModel(Map<ResourceLocation, StateDefinition<Block, BlockState>> pDefinitions, String pPath) {
            pDefinitions.put(ResourceLocation.fromNamespaceAndPath(FancyIceCreamMod.MOD_ID, pPath), createFakeBlockState());
        }

        private static StateDefinition<Block, BlockState> createFakeBlockState() {
            return new StateDefinition.Builder<Block, BlockState>(Blocks.AIR).create(Block::defaultBlockState, BlockState::new);
        }
    }
}
