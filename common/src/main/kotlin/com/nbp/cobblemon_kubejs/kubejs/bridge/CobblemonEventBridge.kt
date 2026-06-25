package com.nbp.cobblemon_kubejs.kubejs.bridge

import com.cobblemon.mod.common.api.events.CobblemonEvents
import com.nbp.cobblemon_kubejs.CobblemonKubejs
import com.nbp.cobblemon_kubejs.kubejs.CobblemonKubeJSEvents
import com.nbp.cobblemon_kubejs.kubejs.event.PokedexChangedEventJS
import com.nbp.cobblemon_kubejs.kubejs.event.PokemonCapturedEventJS
import com.nbp.cobblemon_kubejs.kubejs.event.PokemonScannedEventJS
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
            if (CobblemonKubeJSEvents.POKEDEX_CHANGED.hasListeners()) {
                CobblemonKubeJSEvents.POKEDEX_CHANGED.post(
                    ScriptType.SERVER,
                    PokedexChangedEventJS(event)
                )
            }
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
    }
}
