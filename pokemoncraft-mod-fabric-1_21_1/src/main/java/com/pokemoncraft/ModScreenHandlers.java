package com.pokemoncraft;

import com.pokemoncraft.screen.PokemonTableScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ModScreenHandlers {
    public static final ScreenHandlerType<PokemonTableScreenHandler> POKEMON_TABLE =
            new ExtendedScreenHandlerType<>(
                    (syncId, inventory, pos) -> new PokemonTableScreenHandler(syncId, inventory),
                    BlockPos.PACKET_CODEC);

    public static void register() {
        Registry.register(Registries.SCREEN_HANDLER,
                Identifier.of(PokemonCraft.MOD_ID, "pokemon_crafting_table"), POKEMON_TABLE);
    }
}
