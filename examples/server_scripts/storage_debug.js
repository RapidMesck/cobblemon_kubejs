PlayerEvents.chat(event => {
  var message = String(event.getMessage()).trim()
  var player = event.getPlayer()

  if (message === '!party') {
    var partySlots = CobblemonJS.pokemon.getParty(player)
    partySlots.forEach(pokemon => {
      if (pokemon) {
        player.tell(`#${pokemon.slot + 1}: ${pokemon.species} lvl ${pokemon.level}`)
      }
    })
    event.cancel()
  }

  if (message.startsWith('!partyinfo ')) {
    var requestedSlot = Number(message.substring('!partyinfo '.length).trim()) - 1
    var infoPartySlots = CobblemonJS.pokemon.getParty(player)

    if (!Number.isInteger(requestedSlot) || requestedSlot < 0 || requestedSlot >= 6) {
      player.tell('Usage: !partyinfo <slot from 1 to 6>')
      event.cancel()
      return
    }

    var selectedPokemon = infoPartySlots.get(requestedSlot)

    if (!selectedPokemon) {
      player.tell(`No Pokemon in slot ${requestedSlot + 1}`)
      event.cancel()
      return
    }

    player.tell(`${selectedPokemon.species} lvl ${selectedPokemon.level}`)
    player.tell(`Nature=${selectedPokemon.nature} Ability=${selectedPokemon.ability}`)
    player.tell(`HP=${selectedPokemon.currentHealth}/${selectedPokemon.maxHealth}`)
    player.tell(`XP=${selectedPokemon.experience} Next=${selectedPokemon.experienceToNextLevel}`)
    player.tell(`Item=${selectedPokemon.heldItem || 'none'} Status=${selectedPokemon.status || 'none'}`)
    var moveNames = []
    selectedPokemon.moves.forEach(move => moveNames.push(move.name))
    player.tell(`Moves=${moveNames.join(', ')}`)
    player.tell(`IVs=${selectedPokemon.ivs} EVs=${selectedPokemon.evs}`)
    event.cancel()
  }

  if (message.startsWith('!storagecount ')) {
    var species = message.substring('!storagecount '.length).trim()
    if (!CobblemonJS.species.exists(species)) {
      player.tell(`Unknown species: ${species}`)
      event.cancel()
      return
    }

    var partyCount = CobblemonJS.pokemon.countPartyBySpecies(player, species)
    var pcCount = CobblemonJS.pokemon.countPCBySpecies(player, species)
    var totalCount = CobblemonJS.pokemon.countStorageBySpecies(player, species)
    player.tell(`${species}: party=${partyCount} PC=${pcCount} total=${totalCount}`)
    event.cancel()
  }

  if (message.startsWith('!storagetype ')) {
    var type = message.substring('!storagetype '.length).trim()
    if (!CobblemonJS.species.typeExists(type)) {
      player.tell(`Unknown type: ${type}`)
      player.tell(`Valid types: ${CobblemonJS.species.allTypes().join(', ')}`)
      event.cancel()
      return
    }

    var partyTypeCount = CobblemonJS.pokemon.countPartyByType(player, type)
    var pcTypeCount = CobblemonJS.pokemon.countPCByType(player, type)
    var totalTypeCount = CobblemonJS.pokemon.countStorageByType(player, type)
    player.tell(`${type}: party=${partyTypeCount} PC=${pcTypeCount} total=${totalTypeCount}`)
    event.cancel()
  }

  if (message.startsWith('!pokemonuuid ')) {
    var pokemonUuid = message.substring('!pokemonuuid '.length).trim()
    var pokemonByUuid = CobblemonJS.pokemon.getByUuid(player, pokemonUuid)

    if (!pokemonByUuid) {
      player.tell(`Pokemon not found: ${pokemonUuid}`)
      event.cancel()
      return
    }

    player.tell(
      `${pokemonByUuid.species} lvl ${pokemonByUuid.level} ` +
      `storage=${pokemonByUuid.storage} box=${pokemonByUuid.box} slot=${pokemonByUuid.slot}`
    )
    event.cancel()
  }
})
