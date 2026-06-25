package com.nbp.cobblemon_kubejs.kubejs.event

import com.cobblemon.mod.common.api.events.storage.ReleasePokemonEvent
import com.nbp.cobblemon_kubejs.kubejs.api.PokemonJSView
import com.nbp.cobblemon_kubejs.kubejs.util.StorageUtil
import dev.latvian.mods.kubejs.event.KubeEvent
import net.minecraft.server.level.ServerPlayer

class PokemonReleasedEventJS(
    private val event: ReleasePokemonEvent.Post
) : KubeEvent {
    val player: ServerPlayer
        get() = event.player
    val playerUuid: String
        get() = event.player.uuid.toString()
    val pokemon: PokemonJSView
        get() = PokemonJSView(event.pokemon)
    val species: String
        get() = event.pokemon.species.resourceIdentifier.toString()
    val storage: String
        get() = StorageUtil.name(event.storage)
}
