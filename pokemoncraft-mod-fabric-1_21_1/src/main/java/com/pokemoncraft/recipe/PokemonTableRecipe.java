package com.pokemoncraft.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.pokemoncraft.ModRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * Shapeless recipe for the Pokemon Crafting Table.
 * Uses 1-3 ingredients placed in any of the 3 input slots.
 */
public class PokemonTableRecipe implements Recipe<PokemonRecipeInput> {
    public static final int MAX_INGREDIENTS = 3;

    private final List<Ingredient> ingredients;
    private final ItemStack result;

    public PokemonTableRecipe(List<Ingredient> ingredients, ItemStack result) {
        this.ingredients = ingredients;
        this.result = result;
    }

    @Override
    public boolean matches(PokemonRecipeInput input, World world) {
        List<ItemStack> stacks = new ArrayList<>();
        for (ItemStack stack : input.stacks()) {
            if (!stack.isEmpty()) {
                stacks.add(stack);
            }
        }
        if (stacks.size() != ingredients.size()) {
            return false;
        }
        return matchFrom(0, stacks, new boolean[ingredients.size()]);
    }

    private boolean matchFrom(int stackIndex, List<ItemStack> stacks, boolean[] used) {
        if (stackIndex == stacks.size()) {
            return true;
        }
        for (int i = 0; i < ingredients.size(); i++) {
            if (!used[i] && ingredients.get(i).test(stacks.get(stackIndex))) {
                used[i] = true;
                if (matchFrom(stackIndex + 1, stacks, used)) {
                    return true;
                }
                used[i] = false;
            }
        }
        return false;
    }

    @Override
    public ItemStack craft(PokemonRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return result.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup lookup) {
        return result;
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.TYPE;
    }

    public static class Serializer implements RecipeSerializer<PokemonTableRecipe> {
        private static final MapCodec<PokemonTableRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
                instance.group(
                        Ingredient.DISALLOW_EMPTY_CODEC.listOf(1, MAX_INGREDIENTS)
                                .fieldOf("ingredients").forGetter(r -> r.ingredients),
                        ItemStack.VALIDATING_CODEC.fieldOf("result").forGetter(r -> r.result)
                ).apply(instance, PokemonTableRecipe::new));

        private static final PacketCodec<RegistryByteBuf, PokemonTableRecipe> PACKET_CODEC = PacketCodec.tuple(
                Ingredient.PACKET_CODEC.collect(PacketCodecs.toList()), r -> r.ingredients,
                ItemStack.PACKET_CODEC, r -> r.result,
                PokemonTableRecipe::new);

        @Override
        public MapCodec<PokemonTableRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, PokemonTableRecipe> packetCodec() {
            return PACKET_CODEC;
        }
    }
}
