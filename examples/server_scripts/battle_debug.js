CobblemonEvents.pokemonSent(event => {
  console.log(`[SENT] ${event.playerUuid} ${event.species} ${event.dimension}`)
})

CobblemonEvents.pokemonFainted(event => {
  console.log(`[FAINTED] ${event.playerUuid} ${event.species}`)
})

CobblemonEvents.battleVictory(event => {
  console.log(
    `[VICTORY] battle=${event.battleId} winners=${event.winnerPlayerUuids.join(',')} ` +
    `types=${event.winnerTypes.join(',')} ` +
    `winnerTeam=${Array.from(event.winnerTeam).map(pokemon => pokemon.species).join(',')} ` +
    `loserTeam=${Array.from(event.loserTeam).map(pokemon => pokemon.species).join(',')}`
  )
})

CobblemonEvents.battleDefeat(event => {
  console.log(
    `[DEFEAT] battle=${event.battleId} players=${event.defeatedPlayerUuids.join(',')} ` +
    `types=${event.defeatedTypes.join(',')} forfeit=${event.wasForfeit} ` +
    `defeatedTeam=${Array.from(event.defeatedTeam).map(pokemon => pokemon.species).join(',')} ` +
    `winnerTeam=${Array.from(event.winnerTeam).map(pokemon => pokemon.species).join(',')}`
  )
})

CobblemonEvents.battleFled(event => {
  console.log(
    `[FLED] battle=${event.battleId} players=${event.playerUuids.join(',')} reason=${event.reason}`
  )
})
