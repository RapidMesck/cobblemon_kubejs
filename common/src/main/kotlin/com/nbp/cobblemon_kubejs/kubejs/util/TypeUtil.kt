package com.nbp.cobblemon_kubejs.kubejs.util

import com.cobblemon.mod.common.api.types.ElementalType
import com.cobblemon.mod.common.api.types.ElementalTypes

object TypeUtil {
    fun getOrNull(typeName: String): ElementalType? {
        val normalized = typeName.trim()
        if (normalized.isEmpty()) {
            return null
        }

        return ElementalTypes.all().firstOrNull {
            it.name.equals(normalized, ignoreCase = true) ||
                it.showdownId().equals(normalized, ignoreCase = true)
        }
    }

    fun requireType(typeName: String): ElementalType {
        return getOrNull(typeName)
            ?: throw IllegalArgumentException(
                "Unknown Cobblemon type: '$typeName'. Valid types: ${allIds().joinToString(", ")}."
            )
    }

    fun allIds(): List<String> {
        return ElementalTypes.all().map { it.showdownId() }.sorted()
    }
}
