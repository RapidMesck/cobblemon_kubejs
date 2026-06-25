# Client API Reference

The `CobblemonClientJS` global is available in `kubejs/client_scripts/`.
It exposes a synchronized, read-only Pokedex cache for the local player.

The cache is:

- Replaced after login and Cobblemon data synchronization.
- Updated incrementally when Pokedex progress changes.
- Cleared when disconnecting.
- Read locally, without sending a packet every tooltip frame.

Both the server and client must have the bridge installed.

## `CobblemonClientJS.pokedex`

### `isReady()`

Returns whether the client has received a full Pokedex snapshot.

| Return | `Boolean` |
| --- | --- |

```js
if (!CobblemonClientJS.pokedex.isReady()) {
  console.log('Waiting for Pokedex synchronization.')
}
```

### `getProgress(speciesId)`

Returns `none`, `encountered`, or `caught` from the local cache.

| Return | `String` |
| --- | --- |

Throws when the species ID does not exist.

```js
var progress = CobblemonClientJS.pokedex.getProgress('charizard')
```

### `hasSeen(speciesId)`

Returns `true` for `encountered` and `caught`.

```js
var seenMew = CobblemonClientJS.pokedex.hasSeen('mew')
```

| Return | `Boolean` |
| --- | --- |

### `hasCaught(speciesId)`

Returns `true` only for `caught`.

```js
var caughtMew = CobblemonClientJS.pokedex.hasCaught('mew')
```

| Return | `Boolean` |
| --- | --- |

### `countSeen()`

Returns the total number of seen species in the synchronized cache.

| Return | `Int` |
| --- | --- |

### `countCaught()`

Returns the total number of caught species in the synchronized cache.

| Return | `Int` |
| --- | --- |

### `countSeenByType(typeName)`

Returns the number of seen species matching an elemental type.

| Return | `Int` |
| --- | --- |

Throws when the type does not exist.

### `countCaughtByType(typeName)`

Returns the number of caught species matching an elemental type.

| Return | `Int` |
| --- | --- |

## Dynamic tooltip example

`ItemEvents.dynamicTooltips` targets a dynamic action ID, not an item ID.
Attach the action to the item with `modifyTooltips`, then provide its live
contents:

```js
ItemEvents.modifyTooltips(event => {
  event.modify('minecraft:spyglass', tooltip => {
    tooltip.dynamic('cobblemon_kubejs:pokedex')
  })
})

ItemEvents.dynamicTooltips('cobblemon_kubejs:pokedex', event => {
  if (!CobblemonClientJS.pokedex.isReady()) {
    event.add([Text.gray('Synchronizing Pokedex...')])
    return
  }

  event.add([
    Text.gray('Pokedex'),
    Text.gold(`Seen: ${CobblemonClientJS.pokedex.countSeen()}`),
    Text.green(`Caught: ${CobblemonClientJS.pokedex.countCaught()}`),
    Text.red(
      `Fire caught: ${
        CobblemonClientJS.pokedex.countCaughtByType('fire')
      }`
    )
  ])
})
```

## Client API limitations

- The client API exposes only Pokedex progress and counts.
- It does not expose party or PC data.
- It cannot mutate Pokedex data.
- Values are unavailable until `isReady()` returns `true`.
- The server remains authoritative.

