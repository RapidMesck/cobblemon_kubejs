# Server API Reference

The `CobblemonJS` global is available in `kubejs/server_scripts/`.

```text
CobblemonJS.pokedex
CobblemonJS.species
CobblemonJS.pokemon
CobblemonJS.admin
```

Species IDs may be written as `charizard` or `cobblemon:charizard`. Unqualified
IDs automatically use the `cobblemon` namespace. Species IDs are normalized to
lowercase.

## Pokedex API

### `getProgress(player, speciesId)`

Returns the player's progress for one species.

| Parameter | Type |
| --- | --- |
| `player` | `ServerPlayer` |
| `speciesId` | `String` |
| Return | `String`: `none`, `encountered`, or `caught` |

Throws when the species does not exist.

```js
var progress = CobblemonJS.pokedex.getProgress(player, 'charizard')
player.tell(`Charizard progress: ${progress}`)
```

### `hasSeen(player, speciesId)`

Returns `true` when progress is at least `encountered`.

| Return | `Boolean` |
| --- | --- |

```js
if (CobblemonJS.pokedex.hasSeen(player, 'mew')) {
  player.tell('Mew has been registered as seen.')
}
```

### `hasCaught(player, speciesId)`

Returns `true` when progress is `caught`.

| Return | `Boolean` |
| --- | --- |

```js
var owned = CobblemonJS.pokedex.hasCaught(player, 'cobblemon:absol')
```

### `countSeen(player)`

Returns the number of implemented species with progress of `encountered` or
`caught`.

| Return | `Int` |
| --- | --- |

```js
player.tell(`Species seen: ${CobblemonJS.pokedex.countSeen(player)}`)
```

### `countCaught(player)`

Returns the number of implemented species with progress of `caught`.
Duplicate captures do not increase this count.

| Return | `Int` |
| --- | --- |

```js
player.tell(`Species caught: ${CobblemonJS.pokedex.countCaught(player)}`)
```

### `countSeenByType(player, typeName)`

Returns the number of seen species that have the requested elemental type.
Dual-type species are included when either type matches.

| Parameter | Type |
| --- | --- |
| `typeName` | `String` |
| Return | `Int` |

```js
var seenWater = CobblemonJS.pokedex.countSeenByType(player, 'water')
```

### `countCaughtByType(player, typeName)`

Returns the number of caught species that have the requested elemental type.

```js
var caughtSteel = CobblemonJS.pokedex.countCaughtByType(player, 'steel')
```

### `countByType(player, typeName, minimumProgress)`

Generic type counter.

| Parameter | Type | Accepted values |
| --- | --- | --- |
| `minimumProgress` | `String` | `none`, `encountered`, `seen`, `scanned`, `caught`, `captured`, `owned` |
| Return | `Int` | Matching implemented species |

```js
var registeredFire = CobblemonJS.pokedex.countByType(
  player,
  'fire',
  'encountered'
)
```

### `countAll(player, minimumProgress)`

Generic total counter using the same progress aliases as `countByType`.

```js
var registered = CobblemonJS.pokedex.countAll(player, 'seen')
```

## Species API

### `exists(speciesId)`

Returns whether an implemented species exists. It does not throw for an
unknown species.

```js
if (!CobblemonJS.species.exists(input)) {
  player.tell(`Unknown species: ${input}`)
}
```

| Return | `Boolean` |
| --- | --- |

### `getTypes(speciesId)`

Returns the species' elemental type IDs.

```js
var types = CobblemonJS.species.getTypes('charizard')
// Java List containing "fire" and "flying"
```

| Return | `List<String>` |
| --- | --- |

Throws when the species does not exist.

### `allIds()`

Returns all implemented namespaced species IDs in alphabetical order.

```js
var speciesIds = CobblemonJS.species.allIds()
```

| Return | `List<String>` |
| --- | --- |

### `typeExists(typeName)`

Returns whether an elemental type exists. Matching is case-insensitive.

```js
var valid = CobblemonJS.species.typeExists('Fire')
```

| Return | `Boolean` |
| --- | --- |

### `allTypes()`

Returns all elemental type IDs in alphabetical order.

```js
player.tell(CobblemonJS.species.allTypes().join(', '))
```

| Return | `List<String>` |
| --- | --- |

### `idsByType(typeName)`

Returns all implemented species with the requested type, sorted by ID.

```js
var dragons = CobblemonJS.species.idsByType('dragon')
```

| Return | `List<String>` |
| --- | --- |

Throws when the type does not exist.

## Pokemon storage API

All returned Pokemon are immutable snapshots described in
[Data Models](Data-Models.md).

### `getParty(player)`

Returns exactly six entries. Empty party slots are preserved as `null`.
Pokemon snapshots include `storage: "party"` and a zero-based `slot`.

```js
var party = CobblemonJS.pokemon.getParty(player)
var lead = party.get(0)
```

| Return | `List<PokemonJSView?>` |
| --- | --- |

### `getPC(player)`

Returns only occupied PC positions. Every snapshot includes `storage: "pc"`,
zero-based `box`, and zero-based `slot`.

```js
var pcPokemon = CobblemonJS.pokemon.getPC(player)
pcPokemon.forEach(pokemon => {
  console.log(`${pokemon.species}: box=${pokemon.box} slot=${pokemon.slot}`)
})
```

| Return | `List<PokemonJSView>` |
| --- | --- |

### `getPartyByUuid(player, pokemonUuid)`

Searches only the player's party.

| Return | `PokemonJSView?`; `null` when not found |
| --- | --- |

Throws when `pokemonUuid` is malformed.

```js
var pokemon = CobblemonJS.pokemon.getPartyByUuid(player, uuid)
```

### `getPCByUuid(player, pokemonUuid)`

Searches only the player's PC.

| Return | `PokemonJSView?`; `null` when not found |
| --- | --- |

### `getByUuid(player, pokemonUuid)`

Searches the party first, then the PC.

```js
var pokemon = CobblemonJS.pokemon.getByUuid(player, uuid)
if (pokemon) {
  player.tell(`${pokemon.species} is in ${pokemon.storage}.`)
}
```

| Return | `PokemonJSView?`; `null` when not found |
| --- | --- |

### Type counters

| Function | Return |
| --- | --- |
| `countPartyByType(player, typeName)` | `Int` in the party |
| `countPCByType(player, typeName)` | `Int` in the PC |
| `countStorageByType(player, typeName)` | `Int` in party + PC |

These count individual Pokemon, not unique species.

```js
var partyFire = CobblemonJS.pokemon.countPartyByType(player, 'fire')
var pcFire = CobblemonJS.pokemon.countPCByType(player, 'fire')
var totalFire = CobblemonJS.pokemon.countStorageByType(player, 'fire')
```

### Species counters

| Function | Return |
| --- | --- |
| `countPartyBySpecies(player, speciesId)` | `Int` in the party |
| `countPCBySpecies(player, speciesId)` | `Int` in the PC |
| `countStorageBySpecies(player, speciesId)` | `Int` in party + PC |

```js
var totalAbra = CobblemonJS.pokemon.countStorageBySpecies(player, 'abra')
```

## Administrative API

Administrative methods mutate gameplay state. Only expose them through trusted
scripts and enforce permissions for player-triggered commands.

### `givePokemon(player, properties)`

Creates a Pokemon from a Cobblemon `PokemonProperties` string and adds it
through the normal party storage flow. A full party normally overflows to the
PC. The method fails if storage cannot accept the Pokemon.

| Return | `PokemonJSView` |
| --- | --- |

```js
var pokemon = CobblemonJS.admin.givePokemon(
  player,
  'charizard level=50 shiny=true nature=adamant'
)
```

The properties string must be non-empty and include a valid species.

### `healParty(player)`

Heals the player's party and returns how many Pokemon could be healed before
the operation. A return value of `0` means the party was already healthy or
empty.

| Return | `Int` |
| --- | --- |

```js
var changed = CobblemonJS.admin.healParty(player)
player.tell(`Healed ${changed} Pokemon.`)
```

### `spawnPokemon(player, properties)`

Spawns a Pokemon two blocks in front of the player.

| Return | `PokemonJSView` |
| --- | --- |

```js
var spawned = CobblemonJS.admin.spawnPokemon(
  player,
  'rayquaza level=70 shiny=false'
)
```

### `spawnPokemon(level, properties, x, y, z)`

Spawns a Pokemon at explicit coordinates in a `ServerLevel`.

| Parameter | Type |
| --- | --- |
| `level` | `ServerLevel` |
| `properties` | `String` |
| `x`, `y`, `z` | `Double` |
| Return | `PokemonJSView` |

```js
var spawned = CobblemonJS.admin.spawnPokemon(
  player.serverLevel,
  'mew level=50',
  player.x,
  player.y + 1,
  player.z
)
```

The method rejects coordinates outside Minecraft's spawnable bounds and throws
if the entity cannot be added to the level.

