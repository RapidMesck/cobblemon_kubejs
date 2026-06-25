CobblemonEvents.experienceGained(event => {
  console.log(`[XP] ${event.playerUuid} ${event.species} +${event.experience} source=${event.source}`)
})

CobblemonEvents.pokemonHealed(event => {
  console.log(`[HEALED] ${event.playerUuid} ${event.species} amount=${event.amount}`)
})

CobblemonEvents.pokemonRecall(event => {
  console.log(`[RECALL] ${event.playerUuid} ${event.species}`)
})

CobblemonEvents.pokemonGained(event => {
  console.log(`[GAINED] ${event.playerUuid} ${event.species}`)
})

CobblemonEvents.pokemonReleased(event => {
  console.log(`[RELEASED] ${event.playerUuid} ${event.species} storage=${event.storage}`)
})
