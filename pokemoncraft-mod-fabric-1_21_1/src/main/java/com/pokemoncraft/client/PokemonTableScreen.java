package com.pokemoncraft.client;

import com.pokemoncraft.PokemonCraft;
import com.pokemoncraft.screen.PokemonTableScreenHandler;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class PokemonTableScreen extends HandledScreen<PokemonTableScreenHandler> {
    private static final Identifier TEXTURE =
            Identifier.of(PokemonCraft.MOD_ID, "textures/gui/container/pokemon_table.png");
    private static final Identifier ERROR_TEXTURE =
            Identifier.of(PokemonCraft.MOD_ID, "textures/gui/container/pokemon_table_error.png");

    public PokemonTableScreen(PokemonTableScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.titleX = 8;
        this.titleY = 6;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        context.drawTexture(TEXTURE, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight, 256, 256);
        boolean hasInput = this.handler.getSlot(0).hasStack()
                || this.handler.getSlot(1).hasStack()
                || this.handler.getSlot(2).hasStack();
        if (hasInput && !this.handler.getSlot(3).hasStack()) {
            context.drawTexture(ERROR_TEXTURE, this.x + 65, this.y + 46, 0, 0, 28, 21, 28, 21);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
