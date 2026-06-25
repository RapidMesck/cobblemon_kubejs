package com.nbp.cobblemon_kubejs.kubejs.api

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.nbp.cobblemon_kubejs.kubejs.util.SpeciesUtil
import com.nbp.cobblemon_kubejs.kubejs.util.TypeUtil

object SpeciesJSApi {
    fun exists(speciesId: String): Boolean {
        return SpeciesUtil.getOrNull(speciesId) != null
    }

    fun getTypes(speciesId: String): List<String> {
        return SpeciesUtil.requireSpecies(speciesId).types.map { it.showdownId() }
    }

    fun allIds(): List<String> {
        return PokemonSpecies.implemented
            .map { it.resourceIdentifier.toString() }
            .sorted()
    }

    fun typeExists(typeName: String): Boolean {
        return TypeUtil.getOrNull(typeName) != null
    }

    fun allTypes(): List<String> {
        return TypeUtil.allIds()
    }

    fun idsByType(typeName: String): List<String> {
        val wantedType = TypeUtil.requireType(typeName).showdownId()
        return PokemonSpecies.implemented
            .asSequence()
            .filter { species ->
                species.types.any { it.showdownId().equals(wantedType, ignoreCase = true) }
            }
            .map { it.resourceIdentifier.toString() }
            .sorted()
            .toList()
    }
}
