# Changelog

## 1.7.0 - 2026-06-25

### Added

- Read-only Pokemon lookup by UUID in party.
- Read-only Pokemon lookup by UUID in PC.
- Combined party + PC lookup with storage position metadata.

## 1.6.0 - 2026-06-25

### Added

- Server-to-client Pokedex synchronization.
- Full snapshot on login and Cobblemon data synchronization.
- Incremental updates after Pokedex changes.
- Client cache cleared on disconnect.
- `CobblemonClientJS.pokedex` binding for client scripts.
- Client-side progress, seen/caught, and type-count queries.
- Tooltip example without per-frame network requests.

## 1.5.0 - 2026-06-25

### Added

- `CobblemonEvents.experienceGained`.
- `CobblemonEvents.pokemonHealed`.
- `CobblemonEvents.pokemonRecall`.
- `CobblemonEvents.pokemonGained`.
- `CobblemonEvents.pokemonReleased`.
- `CobblemonJS.admin.givePokemon`.
- `CobblemonJS.admin.healParty`.
- `CobblemonJS.admin.spawnPokemon`.
- Administrative and lifecycle example scripts.

## 1.4.0 - 2026-06-25

### Added

- Expanded read-only Pokemon snapshots.
- Move snapshots with type, category, power, accuracy, and PP.
- IV, effective IV, and EV maps.
- Nature, effective nature, ability, held item, status, experience, Tera type,
  and original trainer fields.
- `CobblemonEvents.levelUp`.
- `CobblemonEvents.evolutionComplete`.

## 1.3.0 - 2026-06-25

### Added

- Separate party, PC, and combined storage counts by species.
- Separate party, PC, and combined storage counts by elemental type.

## 1.2.0 - 2026-06-25

### Added

- `CobblemonEvents.battleDefeat`.
- `CobblemonEvents.battleFled`.
- Unified flee reasons: `escape` and `forfeit`.
- Forfeit detection using Cobblemon's battle `>forcelose` command.
- Defeated and forfeiting player UUID lists.

## 1.1.0 - 2026-06-25

### Added

- Read-only `CobblemonJS.pokemon` API.
- Party snapshots preserving all six slots.
- PC snapshots with box and slot positions.
- Party counting by elemental type.
- Combined party + PC counting by species.
- `CobblemonEvents.pokemonSent`.
- `CobblemonEvents.pokemonFainted`.
- `CobblemonEvents.battleVictory`.
- Read-only Pokemon snapshots for scripts.
- Storage and battle example scripts.

## 1.0.0 - 2026-06-25

Initial NeoForge MVP release for Minecraft 1.21.1.

### Added

- `CobblemonEvents.pokedexChanged`
- `CobblemonEvents.pokemonScanned`
- `CobblemonEvents.pokemonCaptured`
- Global read-only `CobblemonJS.pokedex` API
- Global read-only `CobblemonJS.species` API
- Species and elemental type validation helpers
- Scanner event deduplication
- English and Brazilian Portuguese metadata
- Example server scripts

### Supported

- NeoForge 21.1.199+
- Cobblemon 1.7.0+
- KubeJS `2101.7.2-build.368`
- Rhino `2101.2.7-build.81`
- Java 21

### Notes

- Fabric remains a compileable stub without KubeJS integration.
- Pokedex and Pokemon storage mutation are intentionally outside this release.
