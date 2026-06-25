PlayerEvents.chat(event => {
  var message = String(event.getMessage()).trim()
  var player = event.getPlayer()
  var isAdminCommand =
    message.startsWith('!givepokemon ') ||
    message === '!healparty' ||
    message.startsWith('!spawnpokemon ')

  if (isAdminCommand && !player.hasPermissions(2)) {
    player.tell('Sem permissao para comandos administrativos.')
    event.cancel()
  }

  if (message.startsWith('!givepokemon ')) {
    var properties = message.substring('!givepokemon '.length).trim()
    var given = CobblemonJS.admin.givePokemon(player, properties)
    player.tell(`Recebeu ${given.species} lvl ${given.level}`)
    event.cancel()
  }

  if (message === '!healparty') {
    var healed = CobblemonJS.admin.healParty(player)
    player.tell(`Party curada: ${healed} Pokemon alterados`)
    event.cancel()
  }

  if (message.startsWith('!spawnpokemon ')) {
    var spawnProperties = message.substring('!spawnpokemon '.length).trim()
    var spawned = CobblemonJS.admin.spawnPokemon(player, spawnProperties)
    player.tell(`Spawnado ${spawned.species} lvl ${spawned.level}`)
    event.cancel()
  }
})
