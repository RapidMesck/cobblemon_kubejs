package com.nbp.cobblemon_kubejs.neoforge.network

import com.nbp.cobblemon_kubejs.kubejs.client.ClientPokedexCache
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent
import net.neoforged.neoforge.common.NeoForge

object ClientNetworkHooks {
    @JvmStatic
    fun register() {
        NeoForge.EVENT_BUS.addListener(::onLoggingOut)
    }

    private fun onLoggingOut(event: ClientPlayerNetworkEvent.LoggingOut) {
        ClientPokedexCache.clear()
    }
}
