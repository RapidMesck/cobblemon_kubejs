# Installation and Getting Started

## Requirements

Install the following matching Minecraft 1.21.1 builds:

1. NeoForge.
2. Cobblemon.
3. KubeJS.
4. Rhino, as required by KubeJS.
5. Cobblemon KubeJS Bridge.

The bridge jar must be installed on both the client and server when using the
client Pokedex API. Server-only scripts and events require the jar on the
server.

## Script directories

Place server scripts in:

```text
kubejs/server_scripts/
```

Place client scripts in:

```text
kubejs/client_scripts/
```

Ready-to-copy scripts are available under:

```text
examples/server_scripts/
examples/client_scripts/
```

## Verify the server binding

Create `kubejs/server_scripts/bridge_test.js`:

```js
PlayerEvents.chat(event => {
  var message = String(event.getMessage()).trim()
  if (message !== '!bridge') return

  event.getPlayer().tell(`CobblemonJS loaded: ${!!CobblemonJS}`)
  event.cancel()
})
```

Enter `!bridge` in chat. The expected result is:

```text
CobblemonJS loaded: true
```

## Verify an event

```js
CobblemonEvents.pokedexChanged(event => {
  console.log(
    `[POKEDEX] ${event.playerUuid} ${event.species} ${event.progress}`
  )
})
```

Encounter or catch a new species and check the server log.

## Verify the client binding

Use the tooltip example from
[Client API Reference](Client-API.md#dynamic-tooltip-example). Join a world
and hover over a spyglass.

Client synchronization requires a full game restart after installing or
replacing the mod jar. Reloading scripts does not register network payloads.

## Reload behavior

- `/reload` reloads server scripts and data packs.
- `/kubejs reload client_scripts` reloads client scripts when supported by the
  installed KubeJS build.
- Replacing the bridge jar requires a full restart.
- Native Cobblemon subscriptions are installed once and are not duplicated by
  repeated KubeJS script reloads.

## Rhino scripting note

Use `var` for variables declared inside frequently reloaded event callbacks.
Some Rhino contexts can report redeclaration errors for repeated top-level
`const` declarations.

When handling invalid input, call `event.cancel()` and then `return`:

```js
if (!CobblemonJS.species.exists(species)) {
  player.tell(`Unknown species: ${species}`)
  event.cancel()
  return
}
```

