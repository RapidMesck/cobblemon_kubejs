PlayerEvents.chat(event => {
  var message = String(event.getMessage()).trim()
  if (!message.startsWith('!dextype ')) return

  var player = event.getPlayer()
  var type = message.substring('!dextype '.length).trim().toLowerCase()

  if (!CobblemonJS.species.typeExists(type)) {
    player.tell(`Unknown type: ${type}`)
    player.tell(`Valid types: ${CobblemonJS.species.allTypes().join(', ')}`)
    event.cancel()
    return
  }

  var seen = CobblemonJS.pokedex.countSeenByType(player, type)
  var caught = CobblemonJS.pokedex.countCaughtByType(player, type)

  player.tell(`Type ${type}: ${seen} seen / ${caught} caught`)
  event.cancel()
})
