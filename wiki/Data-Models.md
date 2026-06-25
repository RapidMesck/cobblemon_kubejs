# Data Models

Bridge data models are read-only snapshots. They do not expose Cobblemon's
mutable internal Pokemon objects.

## `PokemonJSView`

| Field | Type | Description |
| --- | --- | --- |
| `uuid` | `String` | Pokemon UUID |
| `species` | `String` | Namespaced species ID |
| `form` | `String` | Current form name |
| `name` | `String` | Display name |
| `level` | `Int` | Current level |
| `shiny` | `Boolean` | Shiny state |
| `gender` | `String` | Lowercase gender name |
| `currentHealth` | `Int` | Current HP |
| `maxHealth` | `Int` | Maximum HP |
| `friendship` | `Int` | Friendship value |
| `types` | `List<String>` | Current form's elemental types |
| `ownerUuid` | `String?` | Owner UUID or `null` |
| `experience` | `Int` | Current accumulated experience |
| `experienceToNextLevel` | `Int` | Experience required for the next level |
| `nature` | `String` | Stored nature ID |
| `effectiveNature` | `String` | Nature currently affecting stats |
| `ability` | `String` | Ability ID |
| `abilityDisplayName` | `String` | Localized ability display name |
| `teraType` | `String` | Tera type ID |
| `heldItem` | `String?` | Namespaced held item ID or `null` |
| `status` | `String?` | Status ID or `null` |
| `statusSecondsLeft` | `Int?` | Remaining timed status duration |
| `originalTrainerType` | `String` | Lowercase trainer type |
| `originalTrainer` | `String?` | Original trainer identifier |
| `originalTrainerName` | `String?` | Original trainer display name |
| `moves` | `List<MoveJSView>` | Current moves |
| `ivs` | `Map<String, Int>` | Base IVs |
| `effectiveIvs` | `Map<String, Int>` | Effective battle IVs |
| `evs` | `Map<String, Int>` | EVs |
| `isFainted` | `Boolean` | Whether the Pokemon is fainted |
| `storage` | `String?` | `party`, `pc`, or `null` |
| `box` | `Int?` | Zero-based PC box index or `null` |
| `slot` | `Int?` | Zero-based slot index or `null` |

Stat maps use the keys supplied by Cobblemon's permanent stats, normally:

```text
hp, atk, def, spa, spd, spe
```

Storage metadata is populated by storage query methods. Event snapshots and
admin method return values may have `storage`, `box`, and `slot` set to `null`.

```js
var pokemon = CobblemonJS.pokemon.getParty(player).get(0)

if (pokemon) {
  player.tell(`${pokemon.species} level ${pokemon.level}`)
  player.tell(`HP: ${pokemon.currentHealth}/${pokemon.maxHealth}`)
  player.tell(`Ability: ${pokemon.ability}`)
  player.tell(`Attack IV: ${pokemon.ivs.get('atk')}`)
}
```

## `MoveJSView`

| Field | Type | Description |
| --- | --- | --- |
| `name` | `String` | Move ID |
| `displayName` | `String` | Localized display name |
| `type` | `String` | Elemental type ID |
| `category` | `String` | Lowercase damage category |
| `power` | `Double` | Base power |
| `accuracy` | `Double` | Accuracy value |
| `currentPp` | `Int` | Current PP |
| `maxPp` | `Int` | Maximum PP |

```js
pokemon.moves.forEach(move => {
  player.tell(
    `${move.displayName}: ${move.type}/${move.category} ` +
    `power=${move.power} PP=${move.currentPp}/${move.maxPp}`
  )
})
```

## Java collections in KubeJS

Returned lists and maps are Java collections. Common access patterns:

```js
var first = list.get(0)
list.forEach(value => console.log(value))
var attackIv = pokemon.ivs.get('atk')
```

Do not rely on the second argument of Java `List.forEach` being a JavaScript
array index. Use `pokemon.slot` when storage position matters.

