package com.nbp.cobblemon_kubejs.neoforge

import com.nbp.cobblemon_kubejs.CobblemonKubejs
import com.nbp.cobblemon_kubejs.neoforge.network.CobblemonKubejsNetwork
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.common.Mod

@Mod(CobblemonKubejs.MOD_ID)
class CobblemonKubejsNeoForge(modBus: IEventBus) {
    init {
        CobblemonKubejs.init()
        CobblemonKubejsNetwork.init(modBus)
    }
}
