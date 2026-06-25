CobblemonEvents.pokedexChanged(event => {
  if (!event.isCaught || !event.player) return

  var caughtFire = CobblemonJS.pokedex.countCaughtByType(event.player, 'fire')

  if (caughtFire >= 10 && !event.player.persistentData.gotFireReward) {
    event.player.persistentData.gotFireReward = true
    event.player.give('minecraft:diamond')
    event.player.tell('Reward unlocked: caught 10 Fire-type Pokemon!')
  }
})
