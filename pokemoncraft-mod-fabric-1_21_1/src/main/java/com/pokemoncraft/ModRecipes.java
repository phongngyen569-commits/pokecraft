package com.pokemoncraft;

import com.pokemoncraft.recipe.PokemonTableRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipes {
    public static final Identifier ID = Identifier.of(PokemonCraft.MOD_ID, "pokemon_crafting");

    public static final RecipeType<PokemonTableRecipe> TYPE = new RecipeType<>() {
        @Override
        public String toString() {
            return ID.toString();
        }
    };
    public static final RecipeSerializer<PokemonTableRecipe> SERIALIZER = new PokemonTableRecipe.Serializer();

    public static void register() {
        Registry.register(Registries.RECIPE_TYPE, ID, TYPE);
        Registry.register(Registries.RECIPE_SERIALIZER, ID, SERIALIZER);
    }
}
