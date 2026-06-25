# Changelog

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
