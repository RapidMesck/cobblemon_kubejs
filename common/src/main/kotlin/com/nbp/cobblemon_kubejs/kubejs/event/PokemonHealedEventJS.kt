package com.nbp.cobblemon_kubejs.kubejs.event

import com.cobblemon.mod.common.api.events.pokemon.healing.PokemonHealedEvent
import com.nbp.cobblemon_kubejs.kubejs.api.PokemonJSView
import dev.latvian.mods.kubejs.event.KubeEvent
import net.minecraft.server.level.ServerPlayer
import kotlin.jvm.JvmName

class PokemonHealedEventJS(
    private val event: PokemonHealedEvent
) : KubeEvent {
    val player: ServerPlayer?
        get() = event.pokemon.getOwnerPlayer()
    val playerUuid: String?
        get() = event.pokemon.getOwnerUUID()?.toString()
    val pokemon: PokemonJSView
        get() = PokemonJSView(event.pokemon)
    val species: String
        get() = event.pokemon.species.resourceIdentifier.toString()
    val amount: Int
        get() = event.amount
    val source: String
        get() = event.source.toString().lowercase()

    @get:JvmName("getIsFullHeal")
    val isFullHeal: Boolean
        get() = event.isFullHeal()
}
