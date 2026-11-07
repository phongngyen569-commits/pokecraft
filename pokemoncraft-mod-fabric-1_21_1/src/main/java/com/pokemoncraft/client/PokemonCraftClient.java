package com.pokemoncraft.client;

import com.pokemoncraft.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class PokemonCraftClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.POKEMON_TABLE, PokemonTableScreen::new);
    }
}
