package com.nbp.cobblemon_kubejs.kubejs.event

import com.cobblemon.mod.common.api.events.pokemon.PokemonSentEvent
import com.nbp.cobblemon_kubejs.kubejs.api.PokemonJSView
import dev.latvian.mods.kubejs.event.KubeEvent
import net.minecraft.server.level.ServerPlayer

class PokemonSentEventJS(
    private val event: PokemonSentEvent.Post
) : KubeEvent {
    val player: ServerPlayer?
        get() = event.pokemon.getOwnerPlayer()

    val playerUuid: String?
        get() = event.pokemon.getOwnerUUID()?.toString()

    val pokemon: PokemonJSView
        get() = PokemonJSView(event.pokemon)

    val species: String
        get() = event.pokemon.species.resourceIdentifier.toString()

    val entityUuid: String
        get() = event.pokemonEntity.uuid.toString()

    val dimension: String
        get() = event.level.dimension().location().toString()

    val x: Double
        get() = event.position.x

    val y: Double
        get() = event.position.y

    val z: Double
        get() = event.position.z
}
