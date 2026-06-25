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

## Client Pokedex API

NeoForge synchronizes a read-only Pokedex snapshot from the server to the
owning client. Incremental updates are sent when the Pokedex changes.

Available in `kubejs/client_scripts/`:

```js
CobblemonClientJS.pokedex.isReady()
CobblemonClientJS.pokedex.getProgress('charizard')
CobblemonClientJS.pokedex.hasSeen('charizard')
CobblemonClientJS.pokedex.hasCaught('charizard')
CobblemonClientJS.pokedex.countSeen()
CobblemonClientJS.pokedex.countCaught()
CobblemonClientJS.pokedex.countSeenByType('fire')
CobblemonClientJS.pokedex.countCaughtByType('fire')
```

Tooltip example:

```js
ItemEvents.modifyTooltips(event => {
  event.modify('minecraft:spyglass', tooltip => {
    tooltip.dynamic('cobblemon_kubejs:pokedex')
  })
})

ItemEvents.dynamicTooltips('cobblemon_kubejs:pokedex', event => {
  if (!CobblemonClientJS.pokedex.isReady()) return
  event.add([
    Text.gold(`Vistos: ${CobblemonClientJS.pokedex.countSeen()}`),
    Text.green(`Capturados: ${CobblemonClientJS.pokedex.countCaught()}`)
  ])
})
```

`modifyTooltips` attaches a dynamic action ID to the item, while
`dynamicTooltips` supplies the live lines for that ID. The dynamic event target
is an action ID, not an item ID. The tooltip only reads local cache state and
never sends a packet while rendering. The cache is replaced on login/data
synchronization, updated after Pokedex changes, and cleared when disconnecting.

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
- `CobblemonEvents.pokemonSent`
- `CobblemonEvents.pokemonFainted`
- `CobblemonEvents.battleVictory`
- `CobblemonEvents.battleDefeat`
- `CobblemonEvents.battleFled`
- `CobblemonEvents.levelUp`
- `CobblemonEvents.evolutionComplete`
- `CobblemonEvents.experienceGained`
- `CobblemonEvents.pokemonHealed`
- `CobblemonEvents.pokemonRecall`
- `CobblemonEvents.pokemonGained`
- `CobblemonEvents.pokemonReleased`

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

### `pokemonSent`

Exposes `player`, `playerUuid`, `pokemon`, `species`, `entityUuid`,
`dimension`, `x`, `y`, and `z`.

### `pokemonFainted`

Exposes `player`, `playerUuid`, `pokemon`, `species`, and `faintedTimer`.
The player fields may be `null` for wild or otherwise unowned Pokemon.

### `battleVictory`

Exposes:

- `battleId`
- `winnerPlayers` and `loserPlayers`
- `winnerPlayerUuids` and `loserPlayerUuids`
- `winnerTypes` and `loserTypes`
- `wasWildCapture`
- `isPvp`, `isPvn`, and `isPvw`
- `hasPlayerWinner`

### `battleDefeat`

Fires from the same final battle result as `battleVictory`, but presents the
losing side as the subject.

Exposes:

- `battleId`
- `defeatedPlayers` and `defeatedPlayerUuids`
- `winnerPlayers` and `winnerPlayerUuids`
- `defeatedTypes` and `winnerTypes`
- `forfeitingPlayerUuids`
- `wasForfeit` and `wasWildCapture`
- `isPvp`, `isPvn`, and `isPvw`
- `hasPlayerDefeat`

### `battleFled`

Fires for both supported ways of leaving a battle:

- `reason === 'escape'`: the native Cobblemon distance-based flee event.
- `reason === 'forfeit'`: a player selected forfeit and Cobblemon issued
  `>forcelose`.

Exposes:

- `battleId`
- `players` and `playerUuids`
- `player` and `playerUuid` as first-player conveniences
- `actorTypes`
- `reason`
- `wasForfeit` and `wasEscape`
- `isPvp`, `isPvn`, and `isPvw`

### `levelUp`

Exposes `player`, `playerUuid`, `pokemon`, `species`, `oldLevel`, `newLevel`,
and `levelsGained`.

### `evolutionComplete`

Exposes `player`, `playerUuid`, `pokemon`, `sourcePokemon`, `sourceSpecies`,
`species`, and `evolutionType`.

### Lifecycle events

- `experienceGained`: player, Pokemon, source, amount, levels, and learned moves.
- `pokemonHealed`: player, Pokemon, amount, source, and full-heal state.
- `pokemonRecall`: player, Pokemon, and previous entity UUID.
- `pokemonGained`: player and received Pokemon.
- `pokemonReleased`: player, released Pokemon, and source storage.

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

CobblemonJS.pokemon.getParty(player)
CobblemonJS.pokemon.getPC(player)
CobblemonJS.pokemon.getByUuid(player, pokemonUuid)
CobblemonJS.pokemon.getPartyByUuid(player, pokemonUuid)
CobblemonJS.pokemon.getPCByUuid(player, pokemonUuid)
CobblemonJS.pokemon.countPartyByType(player, 'water')
CobblemonJS.pokemon.countPCByType(player, 'water')
CobblemonJS.pokemon.countStorageByType(player, 'water')
CobblemonJS.pokemon.countPartyBySpecies(player, 'charizard')
CobblemonJS.pokemon.countPCBySpecies(player, 'charizard')
CobblemonJS.pokemon.countStorageBySpecies(player, 'charizard')

CobblemonJS.admin.givePokemon(player, 'charizard level=50 shiny=true')
CobblemonJS.admin.healParty(player)
CobblemonJS.admin.spawnPokemon(player, 'rayquaza level=70')
```

Cobblemon 1.7 stores progress as `none`, `encountered`, and `caught`.
For convenience, the API also accepts `seen`/`scanned` for `encountered` and
`owned`/`captured` for `caught`.

Counts are per implemented species, not the number of duplicate captures.
Avoid running type counts every tick.

Queries that require a species or type throw a descriptive error when the
input is unknown. Use `species.exists(id)` and `species.typeExists(type)` when
handling untrusted input such as chat messages.

## Pokemon snapshots

`getParty()` returns a six-element list and preserves empty slots as `null`.
`getPC()` returns only occupied positions.

Pokemon snapshots expose:

- `uuid`
- `species`
- `form`
- `name`
- `level`
- `shiny`
- `gender`
- `currentHealth`
- `maxHealth`
- `friendship`
- `types`
- `ownerUuid`
- `isFainted`
- `experience` and `experienceToNextLevel`
- `nature` and `effectiveNature`
- `ability` and `abilityDisplayName`
- `teraType`
- `heldItem`
- `status` and `statusSecondsLeft`
- `originalTrainerType`, `originalTrainer`, and `originalTrainerName`
- `moves`
- `ivs`, `effectiveIvs`, and `evs`
- `storage`, `box`, and `slot` when returned from a storage query

Snapshots are read-only and do not expose Cobblemon's mutable `Pokemon`
instance.

Each move snapshot exposes `name`, `displayName`, `type`, `category`, `power`,
`accuracy`, `currentPp`, and `maxPp`.

## Administrative API

`CobblemonJS.admin` mutates gameplay state and must only be called from trusted
server scripts.

```js
var pokemon = CobblemonJS.admin.givePokemon(
  player,
  'charizard level=50 shiny=true'
)

var healedCount = CobblemonJS.admin.healParty(player)

var spawned = CobblemonJS.admin.spawnPokemon(
  player,
  'pikachu level=15'
)
```

`givePokemon` uses the normal Cobblemon party flow and overflows into the PC.
It fails if neither storage can accept the Pokemon.

`spawnPokemon(player, properties)` spawns two blocks in front of the player.
An explicit server-level overload is also available:

```js
CobblemonJS.admin.spawnPokemon(
  player.serverLevel,
  'mew level=50',
  player.x,
  player.y,
  player.z
)
```

The properties string uses Cobblemon's standard PokemonProperties syntax.

## Release verification

See `RELEASE_CHECKLIST.md` for automated and manual verification steps.
