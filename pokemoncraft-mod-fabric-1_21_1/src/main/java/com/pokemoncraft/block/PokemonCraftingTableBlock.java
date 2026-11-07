package com.pokemoncraft.block;

import com.mojang.serialization.MapCodec;
import com.pokemoncraft.screen.PokemonTableScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PokemonCraftingTableBlock extends Block {
    public static final MapCodec<PokemonCraftingTableBlock> CODEC = createCodec(PokemonCraftingTableBlock::new);
    private static final Text TITLE = Text.translatable("container.pokemoncraft.pokemon_crafting_table");

    public PokemonCraftingTableBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends Block> getCodec() {
        return CODEC;
    }

    @Override
    protected NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return new ExtendedScreenHandlerFactory<BlockPos>() {
            @Override
            public BlockPos getScreenOpeningData(ServerPlayerEntity player) {
                return pos;
            }

            @Override
            public Text getDisplayName() {
                return TITLE;
            }

            @Override
            public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity player) {
                return new PokemonTableScreenHandler(syncId, inventory, ScreenHandlerContext.create(world, pos));
            }
        };
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player,
                                 BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
        return ActionResult.CONSUME;
    }
}
