console.log('Cobblemon KubeJS test loaded')

PlayerEvents.chat(event => {
  const message = String(event.getMessage()).trim()
  const player = event.getPlayer()

  if (message === '!ckjs') {
    player.tell('CobblemonJS carregado: ' + !!CobblemonJS)
    event.cancel()
  }

  if (message.startsWith('!caught ')) {
    const species = message.substring('!caught '.length).trim()

    if (!CobblemonJS.species.exists(species)) {
      player.tell(`Especie desconhecida: ${species}`)
      event.cancel()
    }

    player.tell(`${species}: ${CobblemonJS.pokedex.hasCaught(player, species)}`)
    event.cancel()
  }

  if (message.startsWith('!type ')) {
    const type = message.substring('!type '.length).trim()

    if (!CobblemonJS.species.typeExists(type)) {
      player.tell(`Tipo desconhecido: ${type}`)
      player.tell(`Tipos validos: ${CobblemonJS.species.allTypes().join(', ')}`)
      event.cancel()
    }

    player.tell(`Seen ${type}: ${CobblemonJS.pokedex.countSeenByType(player, type)}`)
    player.tell(`Caught ${type}: ${CobblemonJS.pokedex.countCaughtByType(player, type)}`)
    event.cancel()
  }
})

CobblemonEvents.pokedexChanged(event => {
  console.log(`[POKEDEX] ${event.playerUuid} ${event.species} ${event.progress}`)
})

CobblemonEvents.pokemonScanned(event => {
  console.log(`[SCAN] ${event.player.username} ${event.species}`)
})

CobblemonEvents.pokemonCaptured(event => {
  console.log(`[CAPTURE] ${event.player.username} ${event.species}`)
})
