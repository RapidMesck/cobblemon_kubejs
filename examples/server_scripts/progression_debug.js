CobblemonEvents.levelUp(event => {
  console.log(
    `[LEVEL] ${event.playerUuid} ${event.species} ${event.oldLevel} -> ${event.newLevel}`
  )
})

CobblemonEvents.evolutionComplete(event => {
  console.log(
    `[EVOLUTION] ${event.playerUuid} ${event.sourceSpecies} -> ${event.species} ` +
    `type=${event.evolutionType}`
  )
})
