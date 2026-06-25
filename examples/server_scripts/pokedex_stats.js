PlayerEvents.chat(event => {
  const message = String(event.getMessage()).trim()
  if (!message.startsWith('!dextype ')) return

  const player = event.getPlayer()
  const type = message.substring('!dextype '.length).trim().toLowerCase()

  if (!CobblemonJS.species.typeExists(type)) {
    player.tell(`Tipo desconhecido: ${type}`)
    player.tell(`Tipos validos: ${CobblemonJS.species.allTypes().join(', ')}`)
    event.cancel()
  }

  const seen = CobblemonJS.pokedex.countSeenByType(player, type)
  const caught = CobblemonJS.pokedex.countCaughtByType(player, type)

  player.tell(`Tipo ${type}: ${seen} vistos / ${caught} capturados`)
  event.cancel()
})
