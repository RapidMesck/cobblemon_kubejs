# Release checklist

## Automated

- [x] Full Gradle build succeeds.
- [x] NeoForge jar contains `kubejs.plugins.txt`.
- [x] Fabric jar does not register the KubeJS plugin.
- [x] NeoForge metadata declares Cobblemon and KubeJS dependencies.

## Runtime verified

- [x] `CobblemonJS` is available in server scripts.
- [x] `pokedexChanged` fires for encountered and caught progress.
- [x] `pokemonScanned` fires when scanning a Pokemon.
- [x] `pokemonCaptured` fires after capture.
- [x] Type-based Pokedex counts return coherent results.
- [x] Chat test commands execute and can suppress their chat messages.

## Before publishing

- [ ] Run `/reload` at least three times and confirm one event per action.
- [ ] Test `getProgress`, `hasSeen`, and `hasCaught` with known and unknown species.
- [ ] Test invalid type messages with `!type invalid`.
- [ ] Start a dedicated server once, not only an integrated server.
- [ ] Attach `CHANGELOG.md` to the release.
