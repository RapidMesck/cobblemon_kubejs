package com.nbp.cobblemon_kubejs.kubejs.event

import com.cobblemon.mod.common.api.events.pokemon.LevelUpEvent
import com.nbp.cobblemon_kubejs.kubejs.api.PokemonJSView
import dev.latvian.mods.kubejs.event.KubeEvent
import net.minecraft.server.level.ServerPlayer

class LevelUpEventJS(
    private val event: LevelUpEvent
) : KubeEvent {
    val player: ServerPlayer?
        get() = event.pokemon.getOwnerPlayer()

    val playerUuid: String?
        get() = event.pokemon.getOwnerUUID()?.toString()

    val pokemon: PokemonJSView
        get() = PokemonJSView(event.pokemon)

    val species: String
        get() = event.pokemon.species.resourceIdentifier.toString()

    val oldLevel: Int
        get() = event.oldLevel

    val newLevel: Int
        get() = event.newLevel

    val levelsGained: Int
        get() = event.newLevel - event.oldLevel
}
