package com.nbp.cobblemon_kubejs.kubejs.client

import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.api.pokedex.PokedexEntryProgress
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import net.minecraft.server.level.ServerPlayer

object PokedexClientSync {
    private var snapshotSender: ((ServerPlayer, Map<String, String>) -> Unit)? = null
    private var updateSender: ((ServerPlayer, String, String) -> Unit)? = null

    fun install(
        snapshotSender: (ServerPlayer, Map<String, String>) -> Unit,
        updateSender: (ServerPlayer, String, String) -> Unit
    ) {
        this.snapshotSender = snapshotSender
        this.updateSender = updateSender
    }

    fun sendSnapshot(player: ServerPlayer) {
        val pokedex = Cobblemon.playerDataManager.getPokedexData(player)
        val progress = PokemonSpecies.implemented.mapNotNull { species ->
            val knowledge = pokedex.getKnowledgeForSpecies(species.resourceIdentifier)
            if (knowledge == PokedexEntryProgress.NONE) {
                null
            } else {
                species.resourceIdentifier.toString() to knowledge.name.lowercase()
            }
        }.toMap()
        snapshotSender?.invoke(player, progress)
    }

    fun sendUpdate(
        player: ServerPlayer,
        speciesId: String
    ) {
        val progress = Cobblemon.playerDataManager
            .getPokedexData(player)
            .getKnowledgeForSpecies(
                net.minecraft.resources.ResourceLocation.parse(speciesId)
            )
        updateSender?.invoke(player, speciesId, progress.name.lowercase())
    }
}
