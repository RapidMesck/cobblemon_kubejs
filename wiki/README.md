# Cobblemon KubeJS Bridge Wiki

Cobblemon KubeJS Bridge exposes Cobblemon data and events to KubeJS scripts.
The current implementation targets NeoForge and provides:

- Server-side Pokedex, species, storage, and administrative APIs.
- Server-side Cobblemon lifecycle and battle events.
- Read-only Pokemon and move snapshots.
- A synchronized client-side Pokedex cache for dynamic UI and tooltips.

## Documentation

- [Installation and Getting Started](Installation-and-Getting-Started.md)
- [Server API Reference](Server-API.md)
- [Client API Reference](Client-API.md)
- [Event Reference](Events.md)
- [Data Models](Data-Models.md)
- [Examples and Recipes](Examples-and-Recipes.md)
- [Troubleshooting](Troubleshooting.md)

## Supported environment

| Dependency | Supported version |
| --- | --- |
| Minecraft | 1.21.1 |
| Mod loader | NeoForge 21.1.199 or newer compatible build |
| Cobblemon |  |
| KubeJS |  |

Fabric is currently a compile-only stub. It does not register the KubeJS
plugin, bindings, events, or client synchronization.

## Script globals

| Global | Script side | Purpose |
| --- | --- | --- |
| `CobblemonJS` | Server | Pokedex, species, Pokemon storage, and admin APIs |
| `CobblemonEvents` | Server | Cobblemon event listeners |
| `CobblemonClientJS` | Client | Synchronized read-only Pokedex cache |

## Quick example

```js
CobblemonEvents.pokemonCaptured(event => {
  event.player.tell(`You caught ${event.species} at level ${event.level}!`)
})
```

```js
var caughtFire = CobblemonJS.pokedex.countCaughtByType(player, 'fire')
```

