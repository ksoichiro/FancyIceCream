package com.ksoichiro.mcmod.fancyicecream.item;

import com.ksoichiro.mcmod.fancyicecream.FancyIceCream;
import net.minecraft.client.resources.model.BlockStateModelLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModelRegistrar {
    public static void registerModels() {
        // Register fake block state definitions so the model loading system
        // loads our entity models as if they were block models.
        // This map is private and is made accessible using AT.
        Map<ResourceLocation, StateDefinition<Block, BlockState>> tmp = new LinkedHashMap<>(BlockStateModelLoader.STATIC_DEFINITIONS);
        registerModel(tmp, "vanilla_ice_cream");
        registerModel(tmp, "apple_ice_cream");
        registerModel(tmp, "choco_chip_ice_cream");
        registerModel(tmp, "chocolate_ice_cream");
        registerModel(tmp, "glow_berry_ice_cream");
        registerModel(tmp, "golden_apple_ice_cream");
        registerModel(tmp, "honey_ice_cream");
        registerModel(tmp, "sweet_berry_ice_cream");
        registerModel(tmp, "ice_cream_stand");
        registerModel(tmp, "triple_ice_cream_stand");
        BlockStateModelLoader.STATIC_DEFINITIONS = tmp;
    }

    private static void registerModel(Map<ResourceLocation, StateDefinition<Block, BlockState>> pDefinitions, String pPath) {
        pDefinitions.put(ResourceLocation.fromNamespaceAndPath(FancyIceCream.MOD_ID, pPath), createFakeBlockState());
    }

    private static StateDefinition<Block, BlockState> createFakeBlockState() {
        return new StateDefinition.Builder<Block, BlockState>(Blocks.AIR).create(Block::defaultBlockState, BlockState::new);
    }
}
