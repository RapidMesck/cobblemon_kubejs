# Examples and Recipes

Complete ready-to-copy scripts are stored under the repository's `examples/`
directory.

## Available example scripts

| File | Side | Purpose |
| --- | --- | --- |
| `client_scripts/pokedex_tooltip.js` | Client | Dynamic synchronized Pokedex tooltip |
| `server_scripts/admin_debug.js` | Server | Give, heal, and spawn admin commands |
| `server_scripts/battle_debug.js` | Server | Battle and faint event logging |
| `server_scripts/cobblemon_kubejs_test.js` | Server | Basic binding, Pokedex, and event checks |
| `server_scripts/lifecycle_debug.js` | Server | XP, healing, recall, gain, and release logs |
| `server_scripts/pokedex_rewards.js` | Server | Reward for catching Fire-type species |
| `server_scripts/pokedex_stats.js` | Server | Type-based Pokedex chat command |
| `server_scripts/progression_debug.js` | Server | Level and evolution logs |
| `server_scripts/scan_debug.js` | Server | Scanner event log |
| `server_scripts/storage_debug.js` | Server | Party, PC, type, species, and UUID queries |

## Pokedex reward

```js
CobblemonEvents.pokedexChanged(event => {
  if (!event.isCaught || !event.player) return

  var caughtFire = CobblemonJS.pokedex.countCaughtByType(
    event.player,
    'fire'
  )

  if (caughtFire >= 10 && !event.player.persistentData.gotFireReward) {
    event.player.persistentData.gotFireReward = true
    event.player.give('minecraft:diamond')
    event.player.tell('Reward unlocked: caught 10 Fire-type Pokemon!')
  }
})
```

`persistentData` prevents the reward from being granted repeatedly.

## Safe admin chat command

```js
PlayerEvents.chat(event => {
  var message = String(event.getMessage()).trim()
  if (!message.startsWith('!givepokemon ')) return

  var player = event.getPlayer()

  if (!player.hasPermissions(2)) {
    player.tell('You do not have permission to use this command.')
    event.cancel()
    return
  }

  var properties = message.substring('!givepokemon '.length).trim()
  var pokemon = CobblemonJS.admin.givePokemon(player, properties)

  player.tell(`Received ${pokemon.species} at level ${pokemon.level}.`)
  event.cancel()
})
```

## Party summary

```js
var party = CobblemonJS.pokemon.getParty(player)

party.forEach(pokemon => {
  if (!pokemon) return
  player.tell(
    `#${pokemon.slot + 1}: ${pokemon.species}, level ${pokemon.level}`
  )
})
```

## Party and PC counts

```js
var species = 'aggron'
var partyCount = CobblemonJS.pokemon.countPartyBySpecies(player, species)
var pcCount = CobblemonJS.pokemon.countPCBySpecies(player, species)

player.tell(
  `${species}: party=${partyCount}, PC=${pcCount}, ` +
  `total=${partyCount + pcCount}`
)
```

## Find a Pokemon by UUID

```js
var pokemon = CobblemonJS.pokemon.getByUuid(player, pokemonUuid)

if (!pokemon) {
  player.tell(`Pokemon not found: ${pokemonUuid}`)
} else {
  player.tell(
    `${pokemon.species}: storage=${pokemon.storage}, ` +
    `box=${pokemon.box}, slot=${pokemon.slot}`
  )
}
```

## Spawn at coordinates

```js
var pokemon = CobblemonJS.admin.spawnPokemon(
  player.serverLevel,
  'pikachu level=20 shiny=true',
  player.x + 5,
  player.y,
  player.z
)

player.tell(`Spawned ${pokemon.species}.`)
```

## Dynamic tooltip

See the complete and tested pattern in
[Client API Reference](Client-API.md#dynamic-tooltip-example).

The important distinction is:

```js
tooltip.dynamic('your_namespace:action_id')
```

registers the action on an item, while:

```js
ItemEvents.dynamicTooltips('your_namespace:action_id', event => {
  // Add live lines.
})
```

supplies the action's dynamic output.

