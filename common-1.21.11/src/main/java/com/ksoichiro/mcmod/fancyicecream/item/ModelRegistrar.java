package com.ksoichiro.mcmod.fancyicecream.item;

import com.ksoichiro.mcmod.fancyicecream.FancyIceCream;
import net.minecraft.client.resources.model.BlockStateDefinitions;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModelRegistrar {
    public static void registerModels() {
        // Since ModelEvent.RegisterAdditional has been removed in 1.21.3,
        // we have to register our models by replacing vanilla definitions.
        // This map is private and is made accessible using AT.
        Map<Identifier, StateDefinition<Block, BlockState>> tmp = new LinkedHashMap<>(BlockStateDefinitions.STATIC_DEFINITIONS);
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
        BlockStateDefinitions.STATIC_DEFINITIONS = tmp;
    }

    private static void registerModel(Map<Identifier, StateDefinition<Block, BlockState>> pDefinitions, String pPath) {
        pDefinitions.put(Identifier.fromNamespaceAndPath(FancyIceCream.MOD_ID, pPath), createFakeBlockState());
    }

    private static StateDefinition<Block, BlockState> createFakeBlockState() {
        return new StateDefinition.Builder<Block, BlockState>(Blocks.AIR).create(Block::defaultBlockState, BlockState::new);
    }
}
