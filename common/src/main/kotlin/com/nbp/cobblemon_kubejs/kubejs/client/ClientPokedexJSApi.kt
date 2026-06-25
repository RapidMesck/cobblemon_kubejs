package com.nbp.cobblemon_kubejs.kubejs.client

import com.cobblemon.mod.common.api.pokedex.PokedexEntryProgress
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.nbp.cobblemon_kubejs.kubejs.util.SpeciesUtil
import com.nbp.cobblemon_kubejs.kubejs.util.TypeUtil

object ClientPokedexJSApi {
    fun isReady(): Boolean = ClientPokedexCache.isReady

    fun getProgress(speciesId: String): String {
        val species = SpeciesUtil.requireSpecies(speciesId)
        return ClientPokedexCache.getProgress(species.resourceIdentifier.toString())
    }

    fun hasSeen(speciesId: String): Boolean {
        return progressValue(getProgress(speciesId)) >= PokedexEntryProgress.ENCOUNTERED
    }

    fun hasCaught(speciesId: String): Boolean {
        return progressValue(getProgress(speciesId)) >= PokedexEntryProgress.CAUGHT
    }

    fun countSeen(): Int = countAll(PokedexEntryProgress.ENCOUNTERED)

    fun countCaught(): Int = countAll(PokedexEntryProgress.CAUGHT)

    fun countSeenByType(typeName: String): Int {
        return countByType(typeName, PokedexEntryProgress.ENCOUNTERED)
    }

    fun countCaughtByType(typeName: String): Int {
        return countByType(typeName, PokedexEntryProgress.CAUGHT)
    }

    private fun countAll(minimum: PokedexEntryProgress): Int {
        return ClientPokedexCache.snapshot().values.count {
            progressValue(it) >= minimum
        }
    }

    private fun countByType(typeName: String, minimum: PokedexEntryProgress): Int {
        val wantedType = TypeUtil.requireType(typeName).showdownId()
        return PokemonSpecies.implemented.count { species ->
            species.types.any {
                it.showdownId().equals(wantedType, ignoreCase = true)
            } && progressValue(
                ClientPokedexCache.getProgress(species.resourceIdentifier.toString())
            ) >= minimum
        }
    }

    private fun progressValue(value: String): PokedexEntryProgress {
        return when (value) {
            "caught" -> PokedexEntryProgress.CAUGHT
            "encountered" -> PokedexEntryProgress.ENCOUNTERED
            else -> PokedexEntryProgress.NONE
        }
    }
}
