package com.nbp.cobblemon_kubejs.kubejs.util

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.cobblemon.mod.common.pokemon.Species

object SpeciesUtil {
    fun getOrNull(speciesId: String): Species? {
        return PokemonSpecies.getByIdentifier(ResourceLocationUtil.parseSpeciesId(speciesId))
    }

    fun requireSpecies(speciesId: String): Species {
        return getOrNull(speciesId)
            ?: throw IllegalArgumentException(
                "Unknown Cobblemon species: '$speciesId'. " +
                    "Use CobblemonJS.species.exists(id) to validate species IDs."
            )
    }
}
