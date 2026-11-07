package com.pokemoncraft;

import net.fabricmc.api.ModInitializer;

public class PokemonCraft implements ModInitializer {
    public static final String MOD_ID = "pokemoncraft";

    @Override
    public void onInitialize() {
        ModBlocks.register();
        ModRecipes.register();
        ModScreenHandlers.register();
    }
}
