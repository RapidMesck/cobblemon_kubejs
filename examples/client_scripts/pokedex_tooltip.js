ItemEvents.modifyTooltips((event) => {
  event.modify("minecraft:spyglass", (tooltip) => {
    tooltip.dynamic("cobblemon_kubejs:pokedex");
  });
});

ItemEvents.dynamicTooltips("cobblemon_kubejs:pokedex", (event) => {
  if (!CobblemonClientJS.pokedex.isReady()) {
    event.add([Text.gray("Sincronizando Pokedex...")]);
    return;
  }

  event.add([
    Text.gray("Pokedex"),
    Text.gold(`Vistos: ${CobblemonClientJS.pokedex.countSeen()}`),
    Text.green(`Capturados: ${CobblemonClientJS.pokedex.countCaught()}`),
    Text.red(
      `Fire vistos: ${CobblemonClientJS.pokedex.countSeenByType("fire")}`,
    ),
    Text.red(
      `Fire capturados: ${CobblemonClientJS.pokedex.countCaughtByType("fire")}`,
    ),
  ]);
});
