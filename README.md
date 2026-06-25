# Cobblemon KubeJS Bridge

Read-only, server-side bridge between Cobblemon and KubeJS.

## Supported target

- Minecraft 1.21.1
- NeoForge 21.1.199+
- Cobblemon 1.7.0+
- KubeJS NeoForge `2101.7.2-build.368`
- Java 21

The Fabric module is currently a compile-only stub and does not expose KubeJS
events or bindings.

## Server scripts

Scripts belong in `kubejs/server_scripts/`.

```js
CobblemonEvents.pokedexChanged(event => {
  if (event.isCaught && event.player) {
    event.player.tell(`Você capturou ${event.species}!`)
  }
})

PlayerEvents.chat(event => {
  const message = String(event.getMessage()).trim()
  if (message !== '!dexfire') return
  const player = event.getPlayer()

  const seen = CobblemonJS.pokedex.countSeenByType(player, 'fire')
  const caught = CobblemonJS.pokedex.countCaughtByType(player, 'fire')
  player.tell(`Fire: ${seen} vistos, ${caught} capturados.`)
  event.cancel()
})
```

In KubeJS 7, `event.cancel()` exits the current event callback. Send messages
and perform command logic before calling it.

More complete scripts are available in `examples/server_scripts`.

## Events

- `CobblemonEvents.pokedexChanged`
- `CobblemonEvents.pokemonScanned`
- `CobblemonEvents.pokemonCaptured`

All events are server-side. The native Cobblemon subscriptions are installed
once, so `/reload` only reloads KubeJS listeners and does not duplicate the
bridge.

Repeated native scanner notifications for the same player and Pokémon entity
are collapsed into one KubeJS event per second.

### `pokedexChanged`

| Field | Type | Description |
| --- | --- | --- |
| `player` | `ServerPlayer?` | Online player, or `null` if unavailable |
| `playerUuid` | `String` | Player UUID |
| `species` | `String` | Namespaced species ID |
| `form` | `String` | Form name |
| `progress` | `String` | `none`, `encountered`, or `caught` |
| `knowledge` | `String` | Alias of `progress` |
| `isSeen` | `Boolean` | At least encountered |
| `isCaught` | `Boolean` | Caught |

### `pokemonScanned`

| Field | Type | Description |
| --- | --- | --- |
| `player` | `ServerPlayer` | Player using the scanner |
| `playerUuid` | `String` | Player UUID |
| `species` | `String` | Apparent species ID |
| `form` | `String` | Apparent form |
| `isOwned` | `Boolean` | Whether the scanned Pokemon belongs to the player |
| `level` | `Int` | Pokemon level |
| `shiny` | `Boolean` | Shiny state |

### `pokemonCaptured`

| Field | Type | Description |
| --- | --- | --- |
| `player` | `ServerPlayer` | Capturing player |
| `playerUuid` | `String` | Player UUID |
| `species` | `String` | Captured species ID |
| `form` | `String` | Captured form |
| `level` | `Int` | Pokemon level |
| `shiny` | `Boolean` | Shiny state |
| `pokemonUuid` | `String` | Captured Pokemon UUID |

## Global API

```js
CobblemonJS.pokedex.getProgress(player, 'cobblemon:charizard')
CobblemonJS.pokedex.hasSeen(player, 'charizard')
CobblemonJS.pokedex.hasCaught(player, 'charizard')
CobblemonJS.pokedex.countSeen(player)
CobblemonJS.pokedex.countCaught(player)
CobblemonJS.pokedex.countSeenByType(player, 'fire')
CobblemonJS.pokedex.countCaughtByType(player, 'fire')
CobblemonJS.pokedex.countByType(player, 'fire', 'seen')

CobblemonJS.species.exists('charizard')
CobblemonJS.species.getTypes('charizard')
CobblemonJS.species.allIds()
CobblemonJS.species.typeExists('water')
CobblemonJS.species.allTypes()
CobblemonJS.species.idsByType('water')
```

Cobblemon 1.7 stores progress as `none`, `encountered`, and `caught`.
For convenience, the API also accepts `seen`/`scanned` for `encountered` and
`owned`/`captured` for `caught`.

Counts are per implemented species, not the number of duplicate captures.
Avoid running type counts every tick.

Queries that require a species or type throw a descriptive error when the
input is unknown. Use `species.exists(id)` and `species.typeExists(type)` when
handling untrusted input such as chat messages.

## Release verification

See `RELEASE_CHECKLIST.md` for automated and manual verification steps.
