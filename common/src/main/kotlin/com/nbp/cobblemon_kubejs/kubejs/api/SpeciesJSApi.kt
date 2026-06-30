package com.nbp.cobblemon_kubejs.kubejs.api

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.nbp.cobblemon_kubejs.kubejs.util.SpeciesUtil
import com.nbp.cobblemon_kubejs.kubejs.util.TypeUtil

object SpeciesJSApi {
    private fun implementedIds(): List<String> {
        return PokemonSpecies.implemented
            .map { it.resourceIdentifier.toString() }
            .sorted()
    }

    private fun normalizeLabel(labelName: String): String {
        return labelName.trim()
            .lowercase()
            .replace('-', '_')
            .replace(' ', '_')
    }

    fun exists(speciesId: String): Boolean {
        return SpeciesUtil.getOrNull(speciesId) != null
    }

    fun getTypes(speciesId: String): List<String> {
        return SpeciesUtil.requireSpecies(speciesId).types.map { it.showdownId() }
    }

    fun allIds(): List<String> {
        return implementedIds()
    }

    fun listAll(): List<String> {
        return allIds()
    }

    fun listAllSpecies(): List<String> {
        return allIds()
    }

    fun allLabels(): List<String> {
        return PokemonSpecies.implemented
            .flatMap { it.labels }
            .sorted()
            .distinct()
    }

    fun labelExists(labelName: String): Boolean {
        val wantedLabel = normalizeLabel(labelName)
        return PokemonSpecies.implemented.any { species ->
            species.labels.any { it.equals(wantedLabel, ignoreCase = true) }
        }
    }

    fun idsByLabel(labelName: String): List<String> {
        val wantedLabel = normalizeLabel(labelName)
        return PokemonSpecies.implemented
            .asSequence()
            .filter { species ->
                species.labels.any { it.equals(wantedLabel, ignoreCase = true) }
            }
            .map { it.resourceIdentifier.toString() }
            .sorted()
            .toList()
    }

    fun listLegendary(): List<String> {
        return idsByLabel("legendary")
    }

    fun listLegendarySpecies(): List<String> {
        return listLegendary()
    }

    fun listMythical(): List<String> {
        return idsByLabel("mythical")
    }

    fun listMythicalSpecies(): List<String> {
        return listMythical()
    }

    fun listUltraBeasts(): List<String> {
        return idsByLabel("ultra_beast")
    }

    fun listUltraBeastSpecies(): List<String> {
        return listUltraBeasts()
    }

    fun listRestricted(): List<String> {
        return idsByLabel("restricted")
    }

    fun listRestrictedSpecies(): List<String> {
        return listRestricted()
    }

    fun listParadox(): List<String> {
        return idsByLabel("paradox")
    }

    fun listParadoxSpecies(): List<String> {
        return listParadox()
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
