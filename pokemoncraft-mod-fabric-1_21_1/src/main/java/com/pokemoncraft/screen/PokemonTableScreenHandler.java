package com.pokemoncraft.screen;

import com.pokemoncraft.ModBlocks;
import com.pokemoncraft.ModRecipes;
import com.pokemoncraft.ModScreenHandlers;
import com.pokemoncraft.recipe.PokemonRecipeInput;
import com.pokemoncraft.recipe.PokemonTableRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.ForgingSlotsManager;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Same layout as the smithing table: 3 input slots (0-2), output slot (3).
 * Slot positions match pokemon_table.png.
 */
public class PokemonTableScreenHandler extends ForgingScreenHandler {
    private final World world;
    private RecipeEntry<PokemonTableRecipe> recipe;

    public PokemonTableScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public PokemonTableScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(ModScreenHandlers.POKEMON_TABLE, syncId, playerInventory, context);
        this.world = playerInventory.player.getWorld();
    }

    @Override
    protected ForgingSlotsManager getForgingSlotsManager() {
        return ForgingSlotsManager.create()
                .input(0, 8, 48, stack -> true)
                .input(1, 26, 48, stack -> true)
                .input(2, 44, 48, stack -> true)
                .output(3, 98, 48)
                .build();
    }

    private PokemonRecipeInput createRecipeInput() {
        List<ItemStack> stacks = new ArrayList<>();
        for (int i = 0; i < PokemonTableRecipe.MAX_INGREDIENTS; i++) {
            stacks.add(this.input.getStack(i));
        }
        return new PokemonRecipeInput(stacks);
    }

    @Override
    protected boolean canUse(BlockState state) {
        return state.isOf(ModBlocks.POKEMON_CRAFTING_TABLE);
    }

    @Override
    protected boolean canTakeOutput(PlayerEntity player, boolean present) {
        return recipe != null && recipe.value().matches(createRecipeInput(), this.world);
    }

    @Override
    protected void onTakeOutput(PlayerEntity player, ItemStack stack) {
        for (int i = 0; i < PokemonTableRecipe.MAX_INGREDIENTS; i++) {
            ItemStack in = this.input.getStack(i);
            if (!in.isEmpty()) {
                in.decrement(1);
                this.input.setStack(i, in);
            }
        }
        this.context.run((w, pos) -> w.syncWorldEvent(WorldEvents.SMITHING_TABLE_USED, pos, 0));
    }

    @Override
    public void updateResult() {
        PokemonRecipeInput recipeInput = createRecipeInput();
        Optional<RecipeEntry<PokemonTableRecipe>> match =
                this.world.getRecipeManager().getFirstMatch(ModRecipes.TYPE, recipeInput, this.world);
        if (match.isPresent()) {
            this.recipe = match.get();
            this.output.setStack(0, this.recipe.value().craft(recipeInput, this.world.getRegistryManager()));
        } else {
            this.recipe = null;
            this.output.setStack(0, ItemStack.EMPTY);
        }
    }
}
