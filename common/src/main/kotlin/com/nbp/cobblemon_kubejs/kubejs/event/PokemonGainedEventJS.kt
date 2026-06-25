package com.nbp.cobblemon_kubejs.kubejs.event

import com.cobblemon.mod.common.api.events.pokemon.PokemonGainedEvent
import com.cobblemon.mod.common.util.getPlayer
import com.nbp.cobblemon_kubejs.kubejs.api.PokemonJSView
import dev.latvian.mods.kubejs.event.KubeEvent
import net.minecraft.server.level.ServerPlayer

class PokemonGainedEventJS(
    private val event: PokemonGainedEvent
) : KubeEvent {
    val player: ServerPlayer?
        get() = event.playerId.getPlayer()
    val playerUuid: String
        get() = event.playerId.toString()
    val pokemon: PokemonJSView
        get() = PokemonJSView(event.pokemon)
    val species: String
        get() = event.pokemon.species.resourceIdentifier.toString()
}
