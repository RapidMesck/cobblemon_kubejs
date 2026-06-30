# Event Reference

All `CobblemonEvents` listeners run on the server and belong in
`kubejs/server_scripts/`.

```js
CobblemonEvents.eventName(event => {
  // Read event fields here.
})
```

## Event summary

| Event | Trigger |
| --- | --- |
| `pokedexChanged` | A player's Pokedex knowledge changes |
| `pokemonScanned` | A player scans a Pokemon |
| `pokemonCaptured` | A player captures a Pokemon |
| `pokemonSent` | A Pokemon is sent into the world |
| `pokemonFainted` | A Pokemon faints |
| `battleVictory` | A battle completes, presented from the winning side |
| `battleDefeat` | The same result, presented from the losing side |
| `battleFled` | A player escapes or forfeits |
| `levelUp` | A Pokemon gains one or more levels |
| `evolutionComplete` | A Pokemon finishes evolving |
| `experienceGained` | Experience has been applied |
| `pokemonHealed` | A Pokemon is healed |
| `pokemonRecall` | A sent Pokemon is recalled |
| `pokemonGained` | A player receives a Pokemon |
| `pokemonReleased` | A player releases a Pokemon |

## `pokedexChanged`

Fires after Pokedex data changes.

| Field | Type | Description |
| --- | --- | --- |
| `player` | `ServerPlayer?` | Online player or `null` |
| `playerUuid` | `String` | Player UUID |
| `species` | `String` | Namespaced species ID |
| `form` | `String` | Form name |
| `progress` | `String` | `none`, `encountered`, or `caught` |
| `knowledge` | `String` | Alias of `progress` |
| `isSeen` | `Boolean` | Progress is at least encountered |
| `isCaught` | `Boolean` | Progress is caught |

```js
CobblemonEvents.pokedexChanged(event => {
  console.log(
    `${event.playerUuid}: ${event.species} is now ${event.progress}`
  )
})
```

## `pokemonScanned`

Fires when a player uses Cobblemon's scanning system. Repeated native scanner
notifications for the same player and Pokemon entity are collapsed into one
bridge event per second.

| Field | Type | Description |
| --- | --- | --- |
| `player` | `ServerPlayer` | Scanning player |
| `playerUuid` | `String` | Player UUID |
| `species` | `String` | Apparent species ID |
| `form` | `String` | Apparent form |
| `isOwned` | `Boolean` | Whether the scanned Pokemon belongs to the player |
| `level` | `Int` | Pokemon level |
| `shiny` | `Boolean` | Shiny state |

```js
CobblemonEvents.pokemonScanned(event => {
  event.player.tell(`Scanned ${event.species}, level ${event.level}.`)
})
```

## `pokemonCaptured`

Fires after a successful capture.

| Field | Type | Description |
| --- | --- | --- |
| `player` | `ServerPlayer` | Capturing player |
| `playerUuid` | `String` | Player UUID |
| `species` | `String` | Captured species ID |
| `form` | `String` | Captured form |
| `level` | `Int` | Pokemon level |
| `shiny` | `Boolean` | Shiny state |
| `pokemonUuid` | `String` | Captured Pokemon UUID |

```js
CobblemonEvents.pokemonCaptured(event => {
  console.log(`[CAPTURE] ${event.playerUuid} ${event.pokemonUuid}`)
})
```

## `pokemonSent`

Fires after a Pokemon entity is sent into the world.

| Field | Type | Description |
| --- | --- | --- |
| `player` | `ServerPlayer?` | Owner player or `null` |
| `playerUuid` | `String?` | Owner UUID or `null` |
| `pokemon` | `PokemonJSView` | Pokemon snapshot |
| `species` | `String` | Species ID |
| `entityUuid` | `String` | Spawned entity UUID |
| `dimension` | `String` | Dimension ID |
| `x` | `Double` | Spawn X |
| `y` | `Double` | Spawn Y |
| `z` | `Double` | Spawn Z |

```js
CobblemonEvents.pokemonSent(event => {
  console.log(
    `${event.species} sent at ${event.x}, ${event.y}, ${event.z}`
  )
})
```

## `pokemonFainted`

Fires when a Pokemon faints. Ownership fields may be `null` for wild Pokemon.

| Field | Type | Description |
| --- | --- | --- |
| `player` | `ServerPlayer?` | Owner player or `null` |
| `playerUuid` | `String?` | Owner UUID or `null` |
| `pokemon` | `PokemonJSView` | Pokemon snapshot |
| `species` | `String` | Species ID |
| `faintedTimer` | `Int` | Cobblemon faint timer value |

```js
CobblemonEvents.pokemonFainted(event => {
  if (event.player) {
    event.player.tell(`${event.pokemon.name} fainted.`)
  }
})
```

## `battleVictory`

Fires once for a completed battle and presents the winning side as the subject.

| Field | Type | Description |
| --- | --- | --- |
| `battleId` | `String` | Battle UUID |
| `winnerPlayers` | `List<ServerPlayer>` | Online winning players |
| `loserPlayers` | `List<ServerPlayer>` | Online losing players |
| `winnerPlayerUuids` | `List<String>` | Winning player UUIDs |
| `loserPlayerUuids` | `List<String>` | Losing player UUIDs |
| `winnerTeam` | `List<PokemonJSView>` | Pokemon used by the winning actors |
| `loserTeam` | `List<PokemonJSView>` | Pokemon used by the losing actors |
| `winnerTypes` | `List<String>` | Winning actor types |
| `loserTypes` | `List<String>` | Losing actor types |
| `wasWildCapture` | `Boolean` | Battle ended through a wild capture |
| `isPvp` | `Boolean` | Player versus player |
| `isPvn` | `Boolean` | Player versus NPC |
| `isPvw` | `Boolean` | Player versus wild |
| `hasPlayerWinner` | `Boolean` | At least one winning actor is a player |

```js
CobblemonEvents.battleVictory(event => {
  event.winnerPlayers.forEach(player => {
    player.tell(`Victory in battle ${event.battleId}!`)
  })
})
```

## `battleDefeat`

Fires from the same final result as `battleVictory`, but presents the losing
side as the subject.

| Field | Type | Description |
| --- | --- | --- |
| `battleId` | `String` | Battle UUID |
| `defeatedPlayers` | `List<ServerPlayer>` | Online defeated players |
| `defeatedPlayerUuids` | `List<String>` | Defeated player UUIDs |
| `winnerPlayers` | `List<ServerPlayer>` | Online winning players |
| `winnerPlayerUuids` | `List<String>` | Winning player UUIDs |
| `defeatedTeam` | `List<PokemonJSView>` | Pokemon used by the defeated actors |
| `loserTeam` | `List<PokemonJSView>` | Alias of `defeatedTeam` |
| `winnerTeam` | `List<PokemonJSView>` | Pokemon used by the winning actors |
| `defeatedTypes` | `List<String>` | Defeated actor types |
| `winnerTypes` | `List<String>` | Winning actor types |
| `forfeitingPlayerUuids` | `List<String>` | Players detected as forfeiting |
| `wasForfeit` | `Boolean` | At least one losing actor forfeited |
| `wasWildCapture` | `Boolean` | Battle ended through a wild capture |
| `isPvp` | `Boolean` | Player versus player |
| `isPvn` | `Boolean` | Player versus NPC |
| `isPvw` | `Boolean` | Player versus wild |
| `hasPlayerDefeat` | `Boolean` | At least one losing actor is a player |

```js
CobblemonEvents.battleDefeat(event => {
  event.defeatedPlayers.forEach(player => {
    player.tell(event.wasForfeit ? 'You forfeited.' : 'You were defeated.')
  })
})
```

## `battleFled`

Fires for supported battle exit paths:

- `reason === "escape"` for Cobblemon's distance-based flee event.
- `reason === "forfeit"` when a player's forfeit produces `>forcelose`.

| Field | Type | Description |
| --- | --- | --- |
| `battleId` | `String` | Battle UUID |
| `players` | `List<ServerPlayer>` | Online fleeing players |
| `player` | `ServerPlayer?` | First player convenience field |
| `playerUuids` | `List<String>` | Fleeing player UUIDs |
| `playerUuid` | `String?` | First UUID convenience field |
| `actorTypes` | `List<String>` | Fleeing actor types |
| `reason` | `String` | `escape` or `forfeit` |
| `wasForfeit` | `Boolean` | Reason is forfeit |
| `wasEscape` | `Boolean` | Reason is escape |
| `isPvp` | `Boolean` | Player versus player |
| `isPvn` | `Boolean` | Player versus NPC |
| `isPvw` | `Boolean` | Player versus wild |

```js
CobblemonEvents.battleFled(event => {
  console.log(
    `[FLED] ${event.playerUuids.join(',')} reason=${event.reason}`
  )
})
```

## `levelUp`

| Field | Type | Description |
| --- | --- | --- |
| `player` | `ServerPlayer?` | Owner player or `null` |
| `playerUuid` | `String?` | Owner UUID or `null` |
| `pokemon` | `PokemonJSView` | Updated Pokemon snapshot |
| `species` | `String` | Species ID |
| `oldLevel` | `Int` | Previous level |
| `newLevel` | `Int` | New level |
| `levelsGained` | `Int` | `newLevel - oldLevel` |

```js
CobblemonEvents.levelUp(event => {
  if (event.player) {
    event.player.tell(
      `${event.pokemon.name} reached level ${event.newLevel}!`
    )
  }
})
```

## `evolutionComplete`

| Field | Type | Description |
| --- | --- | --- |
| `player` | `ServerPlayer?` | Owner player or `null` |
| `playerUuid` | `String?` | Owner UUID or `null` |
| `pokemon` | `PokemonJSView` | Evolved Pokemon snapshot |
| `sourcePokemon` | `PokemonJSView` | Source Pokemon snapshot |
| `sourceSpecies` | `String` | Species before evolution |
| `species` | `String` | Species after evolution |
| `evolutionType` | `String` | Derived evolution implementation name |

```js
CobblemonEvents.evolutionComplete(event => {
  if (event.player) {
    event.player.tell(
      `${event.sourceSpecies} evolved into ${event.species}!`
    )
  }
})
```

## `experienceGained`

Fires after experience has been applied.

| Field | Type | Description |
| --- | --- | --- |
| `player` | `ServerPlayer?` | Owner player or `null` |
| `playerUuid` | `String?` | Owner UUID or `null` |
| `pokemon` | `PokemonJSView` | Updated Pokemon snapshot |
| `species` | `String` | Species ID |
| `source` | `String` | Normalized experience source |
| `experience` | `Int` | Experience gained |
| `previousLevel` | `Int` | Level before applying experience |
| `currentLevel` | `Int` | Level after applying experience |
| `learnedMoves` | `List<String>` | Learned move IDs, sorted |

```js
CobblemonEvents.experienceGained(event => {
  console.log(
    `${event.species} gained ${event.experience} XP from ${event.source}`
  )
})
```

## `pokemonHealed`

| Field | Type | Description |
| --- | --- | --- |
| `player` | `ServerPlayer?` | Owner player or `null` |
| `playerUuid` | `String?` | Owner UUID or `null` |
| `pokemon` | `PokemonJSView` | Healed Pokemon snapshot |
| `species` | `String` | Species ID |
| `amount` | `Int` | Native Cobblemon heal amount |
| `source` | `String` | Lowercase healing source |
| `isFullHeal` | `Boolean` | Whether Cobblemon marks it as a full heal |

```js
CobblemonEvents.pokemonHealed(event => {
  console.log(
    `${event.species} healed: amount=${event.amount} full=${event.isFullHeal}`
  )
})
```

## `pokemonRecall`

| Field | Type | Description |
| --- | --- | --- |
| `player` | `ServerPlayer?` | Owner player or `null` |
| `playerUuid` | `String?` | Owner UUID or `null` |
| `pokemon` | `PokemonJSView` | Recalled Pokemon snapshot |
| `species` | `String` | Species ID |
| `oldEntityUuid` | `String?` | Previous world entity UUID |

```js
CobblemonEvents.pokemonRecall(event => {
  console.log(`[RECALL] ${event.species} entity=${event.oldEntityUuid}`)
})
```

## `pokemonGained`

| Field | Type | Description |
| --- | --- | --- |
| `player` | `ServerPlayer?` | Online receiving player or `null` |
| `playerUuid` | `String` | Receiving player UUID |
| `pokemon` | `PokemonJSView` | Received Pokemon snapshot |
| `species` | `String` | Species ID |

```js
CobblemonEvents.pokemonGained(event => {
  if (event.player) {
    event.player.tell(`Received ${event.species}.`)
  }
})
```

## `pokemonReleased`

| Field | Type | Description |
| --- | --- | --- |
| `player` | `ServerPlayer` | Releasing player |
| `playerUuid` | `String` | Player UUID |
| `pokemon` | `PokemonJSView` | Released Pokemon snapshot |
| `species` | `String` | Species ID |
| `storage` | `String` | Source storage name |

```js
CobblemonEvents.pokemonReleased(event => {
  console.log(
    `${event.playerUuid} released ${event.species} from ${event.storage}`
  )
})
```

## Nullable player fields

Some Pokemon events can represent wild, unowned, offline, or otherwise
unresolved owners. Always check nullable player fields before calling player
methods:

```js
CobblemonEvents.pokemonFainted(event => {
  if (!event.player) return
  event.player.tell(`${event.species} fainted.`)
})
```

