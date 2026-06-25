console.log('Cobblemon KubeJS test loaded')

PlayerEvents.chat(event => {
  var message = String(event.getMessage()).trim()
  var player = event.getPlayer()

  if (message === '!ckjs') {
    player.tell('CobblemonJS loaded: ' + !!CobblemonJS)
    event.cancel()
  }

  if (message.startsWith('!caught ')) {
    var species = message.substring('!caught '.length).trim()

    if (!CobblemonJS.species.exists(species)) {
      player.tell(`Unknown species: ${species}`)
      event.cancel()
      return
    }

    player.tell(`${species}: ${CobblemonJS.pokedex.hasCaught(player, species)}`)
    event.cancel()
  }

  if (message.startsWith('!type ')) {
    var type = message.substring('!type '.length).trim()

    if (!CobblemonJS.species.typeExists(type)) {
      player.tell(`Unknown type: ${type}`)
      player.tell(`Valid types: ${CobblemonJS.species.allTypes().join(', ')}`)
      event.cancel()
      return
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
