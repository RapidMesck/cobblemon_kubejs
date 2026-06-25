package com.nbp.cobblemon_kubejs.kubejs.event

import com.cobblemon.mod.common.api.events.pokemon.PokemonFaintedEvent
import com.nbp.cobblemon_kubejs.kubejs.api.PokemonJSView
import dev.latvian.mods.kubejs.event.KubeEvent
import net.minecraft.server.level.ServerPlayer

class PokemonFaintedEventJS(
    private val event: PokemonFaintedEvent
) : KubeEvent {
    val player: ServerPlayer?
        get() = event.pokemon.getOwnerPlayer()

    val playerUuid: String?
        get() = event.pokemon.getOwnerUUID()?.toString()

    val pokemon: PokemonJSView
        get() = PokemonJSView(event.pokemon)

    val species: String
        get() = event.pokemon.species.resourceIdentifier.toString()

    val faintedTimer: Int
        get() = event.faintedTimer
}
