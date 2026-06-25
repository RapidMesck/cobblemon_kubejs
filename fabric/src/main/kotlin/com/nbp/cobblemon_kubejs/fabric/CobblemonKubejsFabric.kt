package com.nbp.cobblemon_kubejs.fabric

import com.nbp.cobblemon_kubejs.CobblemonKubejs
import net.fabricmc.api.ModInitializer

class CobblemonKubejsFabric : ModInitializer {
    override fun onInitialize() {
        CobblemonKubejs.init()
        CobblemonKubejs.LOGGER.warn(
            "Fabric integration is a stub for this target; KubeJS events and bindings are only supported on NeoForge."
        )
    }
}
