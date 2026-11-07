# Pokemon Crafting Table (Fabric 1.21.1)

Build (JDK 21):
  gradle wrapper --gradle-version 8.8     (once)
  ./gradlew build
Jar: build/libs/pokemoncraft-1.0.0.jar  (requires Fabric API 0.116.17+1.21.1; works next to Cobblemon 1.8.4)

Place the block, right-click to open the GUI.
Recipes (none included yet): put JSON files in data/pokemoncraft/recipe/ or a datapack:
{
  "type": "pokemoncraft:pokemon_crafting",
  "ingredients": [ {"item": "minecraft:iron_ingot"}, {"item": "minecraft:redstone"} ],
  "result": { "id": "cobblemon:fire_stone", "count": 1 }
}
1-3 ingredients, any slot order.
