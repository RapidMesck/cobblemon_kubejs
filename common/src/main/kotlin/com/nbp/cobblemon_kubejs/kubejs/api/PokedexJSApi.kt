package com.nbp.cobblemon_kubejs.kubejs.api

import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.api.pokedex.PokedexEntryProgress
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.nbp.cobblemon_kubejs.kubejs.util.SpeciesUtil
import com.nbp.cobblemon_kubejs.kubejs.util.TypeUtil
import net.minecraft.server.level.ServerPlayer

object PokedexJSApi {
    fun getProgress(player: ServerPlayer, speciesId: String): String {
        return getProgressEnum(player, speciesId).name.lowercase()
    }

    fun hasSeen(player: ServerPlayer, speciesId: String): Boolean {
        return getProgressEnum(player, speciesId) >= PokedexEntryProgress.ENCOUNTERED
    }

    fun hasCaught(player: ServerPlayer, speciesId: String): Boolean {
        return getProgressEnum(player, speciesId) >= PokedexEntryProgress.CAUGHT
    }

    fun countSeen(player: ServerPlayer): Int {
        return countAll(player, PokedexEntryProgress.ENCOUNTERED)
    }

    fun countCaught(player: ServerPlayer): Int {
        return countAll(player, PokedexEntryProgress.CAUGHT)
    }

    fun countSeenByType(player: ServerPlayer, typeName: String): Int {
        return countByType(player, typeName, "seen")
    }

    fun countCaughtByType(player: ServerPlayer, typeName: String): Int {
        return countByType(player, typeName, "caught")
    }

    fun countByType(player: ServerPlayer, typeName: String, minimumProgress: String): Int {
        val minimum = parseProgress(minimumProgress)
        val wantedType = TypeUtil.requireType(typeName).showdownId()
        val dex = Cobblemon.playerDataManager.getPokedexData(player)

        return PokemonSpecies.implemented.count { species ->
            species.types.any { it.showdownId().equals(wantedType, ignoreCase = true) } &&
                dex.getKnowledgeForSpecies(species.resourceIdentifier) >= minimum
        }
    }

    fun countAll(player: ServerPlayer, minimumProgress: String): Int {
        return countAll(player, parseProgress(minimumProgress))
    }

    private fun countAll(
        player: ServerPlayer,
        minimumProgress: PokedexEntryProgress
    ): Int {
        val dex = Cobblemon.playerDataManager.getPokedexData(player)
        return PokemonSpecies.implemented.count { species ->
            dex.getKnowledgeForSpecies(species.resourceIdentifier) >= minimumProgress
        }
    }

    private fun getProgressEnum(
        player: ServerPlayer,
        speciesId: String
    ): PokedexEntryProgress {
        val species = SpeciesUtil.requireSpecies(speciesId)
        val dex = Cobblemon.playerDataManager.getPokedexData(player)
        return dex.getKnowledgeForSpecies(species.resourceIdentifier)
    }

    private fun parseProgress(value: String): PokedexEntryProgress {
        return when (value.trim().lowercase()) {
            "none", "unknown", "unregistered" -> PokedexEntryProgress.NONE
            "encountered", "seen", "scanned" -> PokedexEntryProgress.ENCOUNTERED
            "caught", "captured", "owned" -> PokedexEntryProgress.CAUGHT
            else -> throw IllegalArgumentException(
                "Invalid Pokedex progress: '$value'. Use none, encountered/seen, or caught/owned."
            )
        }
    }
}
