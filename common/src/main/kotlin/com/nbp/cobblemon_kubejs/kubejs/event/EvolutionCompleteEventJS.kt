package com.nbp.cobblemon_kubejs.kubejs.event

import com.cobblemon.mod.common.api.events.pokemon.evolution.EvolutionCompleteEvent
import com.nbp.cobblemon_kubejs.kubejs.api.PokemonJSView
import dev.latvian.mods.kubejs.event.KubeEvent
import net.minecraft.server.level.ServerPlayer

class EvolutionCompleteEventJS(
    private val event: EvolutionCompleteEvent
) : KubeEvent {
    val player: ServerPlayer?
        get() = event.pokemon.getOwnerPlayer()

    val playerUuid: String?
        get() = event.pokemon.getOwnerUUID()?.toString()

    val pokemon: PokemonJSView
        get() = PokemonJSView(event.pokemon)

    val sourcePokemon: PokemonJSView
        get() = PokemonJSView(event.sourcePokemon)

    val sourceSpecies: String
        get() = event.sourcePokemon.species.resourceIdentifier.toString()

    val species: String
        get() = event.pokemon.species.resourceIdentifier.toString()

    val evolutionType: String
        get() = event.evolution.javaClass.simpleName
            .removeSuffix("Evolution")
            .replaceFirstChar { it.lowercase() }
}
