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

- [x] Run `/reload` at least three times and confirm one event per action.
- [ ] Test `getProgress`, `hasSeen`, and `hasCaught` with known and unknown species.
- [ ] Test invalid type messages with `!type invalid`.
- [ ] Start a dedicated server once, not only an integrated server.
- [ ] Attach `CHANGELOG.md` to the release.

## Version 1.1 runtime verification

- [ ] Run `!party` and verify occupied slots.
- [ ] Run `!storagecount <species>` for party and PC Pokemon.
- [ ] Send out a Pokemon and verify `pokemonSent`.
- [ ] Faint an owned and a wild Pokemon and verify nullable ownership.
- [ ] Win wild, NPC, and PvP battles and verify `battleVictory` fields.

## Version 1.2 runtime verification

- [ ] Lose a battle and verify `battleDefeat`.
- [ ] Forfeit a battle and verify `battleDefeat.wasForfeit`.
- [ ] Forfeit a battle and verify `battleFled.reason === 'forfeit'`.
- [ ] Escape a wild battle by distance and verify `battleFled.reason === 'escape'`.

## Version 1.4 runtime verification

- [x] Run `!partyinfo <slot>` and verify Pokemon details.
- [x] Gain one or more levels and verify `levelUp`.
- [x] Complete an evolution and verify source and resulting species.

## Version 1.5 runtime verification

- [ ] Gain experience and verify source, amount, levels, and learned moves.
- [ ] Heal a damaged party and verify `pokemonHealed`.
- [ ] Recall a sent Pokemon and verify `pokemonRecall`.
- [ ] Give a Pokemon and verify storage plus `pokemonGained`.
- [ ] Release a Pokemon from party and PC and verify `pokemonReleased`.
- [ ] Spawn a Pokemon in front of the player.
- [ ] Verify invalid properties produce a descriptive script error.
