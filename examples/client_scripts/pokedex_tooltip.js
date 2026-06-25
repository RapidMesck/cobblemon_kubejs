ItemEvents.modifyTooltips((event) => {
  event.modify("minecraft:spyglass", (tooltip) => {
    tooltip.dynamic("cobblemon_kubejs:pokedex");
  });
});

ItemEvents.dynamicTooltips("cobblemon_kubejs:pokedex", (event) => {
  if (!CobblemonClientJS.pokedex.isReady()) {
    event.add([Text.gray("Synchronizing Pokedex...")]);
    return;
  }

  event.add([
    Text.gray("Pokedex"),
    Text.gold(`Seen: ${CobblemonClientJS.pokedex.countSeen()}`),
    Text.green(`Caught: ${CobblemonClientJS.pokedex.countCaught()}`),
    Text.red(
      `Fire seen: ${CobblemonClientJS.pokedex.countSeenByType("fire")}`,
    ),
    Text.red(
      `Fire caught: ${CobblemonClientJS.pokedex.countCaughtByType("fire")}`,
    ),
  ]);
});
