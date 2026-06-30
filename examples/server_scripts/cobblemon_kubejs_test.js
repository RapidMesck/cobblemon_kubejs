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

  if (message.startsWith('!specieslabel ')) {
    var label = message.substring('!specieslabel '.length).trim()

    if (!CobblemonJS.species.labelExists(label)) {
      player.tell(`Unknown species label: ${label}`)
      player.tell(`Known labels: ${CobblemonJS.species.allLabels().join(', ')}`)
      event.cancel()
      return
    }

    var species = CobblemonJS.species.idsByLabel(label)
    player.tell(`${label}: ${species.size()} species`)
    player.tell(Array.from(species).slice(0, 12).join(', '))
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
