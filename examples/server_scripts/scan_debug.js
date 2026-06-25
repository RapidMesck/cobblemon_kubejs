CobblemonEvents.pokemonScanned(event => {
  console.log(
    `[SCAN] ${event.player.username} scanned ${event.species} form=${event.form} owned=${event.isOwned}`
  )
})
