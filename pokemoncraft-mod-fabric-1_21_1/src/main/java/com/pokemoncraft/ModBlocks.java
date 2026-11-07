package com.pokemoncraft;

import com.pokemoncraft.block.PokemonCraftingTableBlock;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static final Block POKEMON_CRAFTING_TABLE = new PokemonCraftingTableBlock(
            AbstractBlock.Settings.copy(Blocks.SMITHING_TABLE));

    public static void register() {
        Identifier id = Identifier.of(PokemonCraft.MOD_ID, "pokemon_crafting_table");
        Registry.register(Registries.BLOCK, id, POKEMON_CRAFTING_TABLE);
        BlockItem item = new BlockItem(POKEMON_CRAFTING_TABLE, new Item.Settings());
        Registry.register(Registries.ITEM, id, item);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL)
                .register(entries -> entries.add(item));
    }
}
