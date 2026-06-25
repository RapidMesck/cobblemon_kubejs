PlayerEvents.chat(event => {
  var message = String(event.getMessage()).trim()
  if (!message.startsWith('!dextype ')) return

  var player = event.getPlayer()
  var type = message.substring('!dextype '.length).trim().toLowerCase()

  if (!CobblemonJS.species.typeExists(type)) {
    player.tell(`Tipo desconhecido: ${type}`)
    player.tell(`Tipos validos: ${CobblemonJS.species.allTypes().join(', ')}`)
    event.cancel()
  }

  var seen = CobblemonJS.pokedex.countSeenByType(player, type)
  var caught = CobblemonJS.pokedex.countCaughtByType(player, type)

  player.tell(`Tipo ${type}: ${seen} vistos / ${caught} capturados`)
  event.cancel()
})
