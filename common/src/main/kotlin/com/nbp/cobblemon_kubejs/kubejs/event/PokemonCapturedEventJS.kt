package com.nbp.cobblemon_kubejs.kubejs.event

import com.cobblemon.mod.common.api.events.pokemon.PokemonCapturedEvent
import dev.latvian.mods.kubejs.event.KubeEvent
import net.minecraft.server.level.ServerPlayer

class PokemonCapturedEventJS(
    private val event: PokemonCapturedEvent
) : KubeEvent {
    val player: ServerPlayer
        get() = event.player

    val playerUuid: String
        get() = event.player.uuid.toString()

    val species: String
        get() = event.pokemon.species.resourceIdentifier.toString()

    val form: String
        get() = event.pokemon.form.name

    val level: Int
        get() = event.pokemon.level

    val shiny: Boolean
        get() = event.pokemon.shiny

    val pokemonUuid: String
        get() = event.pokemon.uuid.toString()
}
