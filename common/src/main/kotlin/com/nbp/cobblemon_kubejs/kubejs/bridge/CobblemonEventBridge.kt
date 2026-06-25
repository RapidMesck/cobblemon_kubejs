package com.nbp.cobblemon_kubejs.kubejs.bridge

import com.cobblemon.mod.common.api.events.CobblemonEvents
import com.cobblemon.mod.common.util.getPlayer
import com.nbp.cobblemon_kubejs.CobblemonKubejs
import com.nbp.cobblemon_kubejs.kubejs.CobblemonKubeJSEvents
import com.nbp.cobblemon_kubejs.kubejs.client.PokedexClientSync
import com.nbp.cobblemon_kubejs.kubejs.event.BattleDefeatEventJS
import com.nbp.cobblemon_kubejs.kubejs.event.BattleFledEventJS
import com.nbp.cobblemon_kubejs.kubejs.event.BattleVictoryEventJS
import com.nbp.cobblemon_kubejs.kubejs.event.EvolutionCompleteEventJS
import com.nbp.cobblemon_kubejs.kubejs.event.ExperienceGainedEventJS
import com.nbp.cobblemon_kubejs.kubejs.event.LevelUpEventJS
import com.nbp.cobblemon_kubejs.kubejs.event.PokemonGainedEventJS
import com.nbp.cobblemon_kubejs.kubejs.event.PokemonHealedEventJS
import com.nbp.cobblemon_kubejs.kubejs.event.PokemonRecallEventJS
import com.nbp.cobblemon_kubejs.kubejs.event.PokemonReleasedEventJS
import com.nbp.cobblemon_kubejs.kubejs.event.PokedexChangedEventJS
import com.nbp.cobblemon_kubejs.kubejs.event.PokemonCapturedEventJS
import com.nbp.cobblemon_kubejs.kubejs.event.PokemonFaintedEventJS
import com.nbp.cobblemon_kubejs.kubejs.event.PokemonScannedEventJS
import com.nbp.cobblemon_kubejs.kubejs.event.PokemonSentEventJS
import dev.latvian.mods.kubejs.script.ScriptType
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.ConcurrentHashMap

object CobblemonEventBridge {
    private const val SCAN_DEDUPLICATION_WINDOW_MS = 1_000L
    private val initialized = AtomicBoolean(false)
    private val recentScans = ConcurrentHashMap<String, Long>()

    fun init() {
        if (!initialized.compareAndSet(false, true)) {
            return
        }

        CobblemonKubejs.LOGGER.info("Subscribing Cobblemon events for KubeJS bridge")

        CobblemonEvents.POKEDEX_DATA_CHANGED_POST.subscribe { event ->
            event.playerUUID.getPlayer()?.let { player ->
                PokedexClientSync.sendUpdate(
                    player,
                    event.record.speciesDexRecord.id.toString()
                )
            }

            if (CobblemonKubeJSEvents.POKEDEX_CHANGED.hasListeners()) {
                CobblemonKubeJSEvents.POKEDEX_CHANGED.post(
                    ScriptType.SERVER,
                    PokedexChangedEventJS(event)
                )
            }
        }

        CobblemonEvents.DATA_SYNCHRONIZED.subscribe { player ->
            PokedexClientSync.sendSnapshot(player)
        }

        CobblemonEvents.POKEMON_SCANNED.subscribe { event ->
            val now = System.currentTimeMillis()
            val scanKey = "${event.player.uuid}:${event.scannedPokemonEntityData.pokemon.uuid}"
            val previousScan = recentScans.put(scanKey, now)

            if (previousScan != null && now - previousScan < SCAN_DEDUPLICATION_WINDOW_MS) {
                return@subscribe
            }

            if (recentScans.size > 512) {
                recentScans.entries.removeIf {
                    now - it.value >= SCAN_DEDUPLICATION_WINDOW_MS
                }
            }

            if (CobblemonKubeJSEvents.POKEMON_SCANNED.hasListeners()) {
                CobblemonKubeJSEvents.POKEMON_SCANNED.post(
                    ScriptType.SERVER,
                    PokemonScannedEventJS(event)
                )
            }
        }

        CobblemonEvents.POKEMON_CAPTURED.subscribe { event ->
            if (CobblemonKubeJSEvents.POKEMON_CAPTURED.hasListeners()) {
                CobblemonKubeJSEvents.POKEMON_CAPTURED.post(
                    ScriptType.SERVER,
                    PokemonCapturedEventJS(event)
                )
            }
        }

        CobblemonEvents.POKEMON_SENT_POST.subscribe { event ->
            if (CobblemonKubeJSEvents.POKEMON_SENT.hasListeners()) {
                CobblemonKubeJSEvents.POKEMON_SENT.post(
                    ScriptType.SERVER,
                    PokemonSentEventJS(event)
                )
            }
        }

        CobblemonEvents.POKEMON_FAINTED.subscribe { event ->
            if (CobblemonKubeJSEvents.POKEMON_FAINTED.hasListeners()) {
                CobblemonKubeJSEvents.POKEMON_FAINTED.post(
                    ScriptType.SERVER,
                    PokemonFaintedEventJS(event)
                )
            }
        }

        CobblemonEvents.BATTLE_VICTORY.subscribe { event ->
            if (CobblemonKubeJSEvents.BATTLE_VICTORY.hasListeners()) {
                CobblemonKubeJSEvents.BATTLE_VICTORY.post(
                    ScriptType.SERVER,
                    BattleVictoryEventJS(event)
                )
            }

            if (CobblemonKubeJSEvents.BATTLE_DEFEAT.hasListeners()) {
                CobblemonKubeJSEvents.BATTLE_DEFEAT.post(
                    ScriptType.SERVER,
                    BattleDefeatEventJS(event)
                )
            }

            if (
                BattleFledEventJS.wasForfeit(event) &&
                CobblemonKubeJSEvents.BATTLE_FLED.hasListeners()
            ) {
                CobblemonKubeJSEvents.BATTLE_FLED.post(
                    ScriptType.SERVER,
                    BattleFledEventJS.fromForfeit(event)
                )
            }
        }

        CobblemonEvents.BATTLE_FLED.subscribe { event ->
            if (CobblemonKubeJSEvents.BATTLE_FLED.hasListeners()) {
                CobblemonKubeJSEvents.BATTLE_FLED.post(
                    ScriptType.SERVER,
                    BattleFledEventJS.fromEscape(event)
                )
            }
        }

        CobblemonEvents.EVOLUTION_COMPLETE.subscribe { event ->
            if (CobblemonKubeJSEvents.EVOLUTION_COMPLETE.hasListeners()) {
                CobblemonKubeJSEvents.EVOLUTION_COMPLETE.post(
                    ScriptType.SERVER,
                    EvolutionCompleteEventJS(event)
                )
            }
        }

        CobblemonEvents.LEVEL_UP_EVENT.subscribe { event ->
            if (CobblemonKubeJSEvents.LEVEL_UP.hasListeners()) {
                CobblemonKubeJSEvents.LEVEL_UP.post(
                    ScriptType.SERVER,
                    LevelUpEventJS(event)
                )
            }
        }

        CobblemonEvents.EXPERIENCE_GAINED_EVENT_POST.subscribe { event ->
            if (CobblemonKubeJSEvents.EXPERIENCE_GAINED.hasListeners()) {
                CobblemonKubeJSEvents.EXPERIENCE_GAINED.post(
                    ScriptType.SERVER,
                    ExperienceGainedEventJS(event)
                )
            }
        }

        CobblemonEvents.POKEMON_HEALED.subscribe { event ->
            if (CobblemonKubeJSEvents.POKEMON_HEALED.hasListeners()) {
                CobblemonKubeJSEvents.POKEMON_HEALED.post(
                    ScriptType.SERVER,
                    PokemonHealedEventJS(event)
                )
            }
        }

        CobblemonEvents.POKEMON_RECALL_POST.subscribe { event ->
            if (CobblemonKubeJSEvents.POKEMON_RECALL.hasListeners()) {
                CobblemonKubeJSEvents.POKEMON_RECALL.post(
                    ScriptType.SERVER,
                    PokemonRecallEventJS(event)
                )
            }
        }

        CobblemonEvents.POKEMON_GAINED.subscribe { event ->
            if (CobblemonKubeJSEvents.POKEMON_GAINED.hasListeners()) {
                CobblemonKubeJSEvents.POKEMON_GAINED.post(
                    ScriptType.SERVER,
                    PokemonGainedEventJS(event)
                )
            }
        }

        CobblemonEvents.POKEMON_RELEASED_EVENT_POST.subscribe { event ->
            if (CobblemonKubeJSEvents.POKEMON_RELEASED.hasListeners()) {
                CobblemonKubeJSEvents.POKEMON_RELEASED.post(
                    ScriptType.SERVER,
                    PokemonReleasedEventJS(event)
                )
            }
        }
    }
}
