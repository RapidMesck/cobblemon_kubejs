package com.nbp.cobblemon_kubejs

import org.slf4j.Logger
import org.slf4j.LoggerFactory

object CobblemonKubejs {
    const val MOD_ID = "cobblemon_kubejs"

    @JvmField
    val LOGGER: Logger = LoggerFactory.getLogger("Cobblemon KubeJS Bridge")

    fun init() {
        LOGGER.info("Initializing Cobblemon KubeJS Bridge")
    }
}
