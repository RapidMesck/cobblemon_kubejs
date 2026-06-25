PlayerEvents.chat(event => {
  var message = String(event.getMessage()).trim()
  var player = event.getPlayer()
  var isAdminCommand =
    message.startsWith('!givepokemon ') ||
    message === '!healparty' ||
    message.startsWith('!spawnpokemon ')

  if (isAdminCommand && !player.hasPermissions(2)) {
    player.tell('You do not have permission to use administrative commands.')
    event.cancel()
    return
  }

  if (message.startsWith('!givepokemon ')) {
    var properties = message.substring('!givepokemon '.length).trim()
    var given = CobblemonJS.admin.givePokemon(player, properties)
    player.tell(`Received ${given.species} at level ${given.level}.`)
    event.cancel()
  }

  if (message === '!healparty') {
    var healed = CobblemonJS.admin.healParty(player)
    player.tell(`Party healed: ${healed} Pokemon changed.`)
    event.cancel()
  }

  if (message.startsWith('!spawnpokemon ')) {
    var spawnProperties = message.substring('!spawnpokemon '.length).trim()
    var spawned = CobblemonJS.admin.spawnPokemon(player, spawnProperties)
    player.tell(`Spawned ${spawned.species} at level ${spawned.level}.`)
    event.cancel()
  }
})
