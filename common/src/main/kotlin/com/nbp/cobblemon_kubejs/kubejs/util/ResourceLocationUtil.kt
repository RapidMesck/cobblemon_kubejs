package com.nbp.cobblemon_kubejs.kubejs.util

import net.minecraft.resources.ResourceLocation

object ResourceLocationUtil {
    fun parseSpeciesId(value: String): ResourceLocation {
        val normalized = value.trim().lowercase()
        require(normalized.isNotEmpty()) { "Species ID cannot be empty." }

        return if (':' in normalized) {
            ResourceLocation.parse(normalized)
        } else {
            ResourceLocation.fromNamespaceAndPath("cobblemon", normalized)
        }
    }
}
