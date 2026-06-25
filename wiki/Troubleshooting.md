# Troubleshooting

## `CobblemonJS` is undefined

- Confirm the script is in `kubejs/server_scripts/`.
- Confirm the NeoForge bridge jar is installed on the server.
- Confirm KubeJS, Rhino, and Cobblemon loaded successfully.
- Check `logs/kubejs/server.log` and the main game log.
- Fabric does not currently expose the bridge.

## `CobblemonClientJS` is undefined

- Confirm the script is in `kubejs/client_scripts/`.
- Install the bridge jar on the client.
- Use the same bridge version on client and server.
- Restart the full game after replacing the jar.

## Tooltip shows only the item name

Do not use the item ID as the `dynamicTooltips` target. The target is a dynamic
action ID that must first be attached with `modifyTooltips`:

```js
ItemEvents.modifyTooltips(event => {
  event.modify('minecraft:spyglass', tooltip => {
    tooltip.dynamic('cobblemon_kubejs:pokedex')
  })
})

ItemEvents.dynamicTooltips('cobblemon_kubejs:pokedex', event => {
  event.add([Text.green('Dynamic tooltip is running.')])
})
```

If the test line does not appear, verify that the script loaded on the client
and check the client KubeJS log.

## Tooltip remains on “Synchronizing Pokedex...”

- Confirm both client and server have the bridge installed.
- Confirm they use the same mod version.
- Fully reconnect after installing the mod.
- Check for payload registration or connection errors in the client and server
  logs.

## `TypeError: redeclaration of var ...`

Avoid reusing top-level `const` declarations in reloadable Rhino callbacks.
Use callback-local `var` declarations in example scripts.

## Chat command appears but does nothing

Read the message with:

```js
var message = String(event.getMessage()).trim()
```

Run command logic before the final `event.cancel()`. For rejected input, cancel
and return:

```js
if (!valid) {
  player.tell('Invalid input.')
  event.cancel()
  return
}
```

## Unknown species error

Validate player-provided species before calling APIs that require a valid ID:

```js
if (!CobblemonJS.species.exists(species)) {
  player.tell(`Unknown species: ${species}`)
  return
}
```

Both `charizard` and `cobblemon:charizard` are accepted.

## Unknown type error

```js
if (!CobblemonJS.species.typeExists(type)) {
  player.tell(`Unknown type: ${type}`)
  player.tell(`Valid types: ${CobblemonJS.species.allTypes().join(', ')}`)
  return
}
```

## Invalid Pokemon UUID

UUID lookup methods require the standard format:

```text
xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
```

A well-formed UUID that is not present returns `null`. A malformed UUID throws
a descriptive script error.

## `healParty` returns zero

The return value is the number of Pokemon that could be healed before the
operation. Zero normally means all party Pokemon were already healthy or the
party was empty.

## Duplicate event logs

The bridge installs native Cobblemon subscriptions once. If an event appears
multiple times:

1. Search for duplicate KubeJS listener files.
2. Check whether the same example was copied under multiple names.
3. Distinguish separate native events such as `pokemonGained` and
   `pokedexChanged`, which may both happen during one gameplay action.

Scanner notifications are deduplicated per player and Pokemon entity for one
second.

## Reload versus restart

| Change | Required action |
| --- | --- |
| Server script | `/reload` |
| Client script | Client-script reload or reconnect |
| Bridge jar | Full game/server restart |
| Network synchronization code | Full client and server restart |

