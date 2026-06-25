package com.nbp.cobblemon_kubejs.kubejs

import com.nbp.cobblemon_kubejs.CobblemonKubejs
import com.nbp.cobblemon_kubejs.kubejs.api.CobblemonJSBinding
import com.nbp.cobblemon_kubejs.kubejs.bridge.CobblemonEventBridge
import com.nbp.cobblemon_kubejs.kubejs.client.CobblemonClientJSBinding
import dev.latvian.mods.kubejs.event.EventGroupRegistry
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin
import dev.latvian.mods.kubejs.script.BindingRegistry

class CobblemonKubeJSPlugin : KubeJSPlugin {
    override fun init() {
        CobblemonKubejs.LOGGER.info("Loading KubeJS plugin for Cobblemon bridge")
        CobblemonEventBridge.init()
    }

    override fun registerEvents(registry: EventGroupRegistry) {
        registry.register(CobblemonKubeJSEvents.GROUP)
        CobblemonKubejs.LOGGER.info(
            "Registered CobblemonEvents: pokedexChanged, pokemonScanned, pokemonCaptured, " +
                "pokemonSent, pokemonFainted, battleVictory, battleDefeat, battleFled, " +
                "evolutionComplete, levelUp, experienceGained, pokemonHealed, pokemonRecall, " +
                "pokemonGained, pokemonReleased"
        )
    }

    override fun registerBindings(bindings: BindingRegistry) {
        if (bindings.type().isServer) {
            bindings.add("CobblemonJS", CobblemonJSBinding)
        } else if (bindings.type().isClient) {
            bindings.add("CobblemonClientJS", CobblemonClientJSBinding)
        }
    }
}
