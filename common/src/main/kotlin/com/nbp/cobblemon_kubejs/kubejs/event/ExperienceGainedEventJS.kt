package com.nbp.cobblemon_kubejs.kubejs.event

import com.cobblemon.mod.common.api.events.pokemon.ExperienceGainedEvent
import com.nbp.cobblemon_kubejs.kubejs.api.PokemonJSView
import com.nbp.cobblemon_kubejs.kubejs.util.ExperienceSourceUtil
import dev.latvian.mods.kubejs.event.KubeEvent
import net.minecraft.server.level.ServerPlayer

class ExperienceGainedEventJS(
    private val event: ExperienceGainedEvent.Post
) : KubeEvent {
    val player: ServerPlayer?
        get() = event.pokemon.getOwnerPlayer()
    val playerUuid: String?
        get() = event.pokemon.getOwnerUUID()?.toString()
    val pokemon: PokemonJSView
        get() = PokemonJSView(event.pokemon)
    val species: String
        get() = event.pokemon.species.resourceIdentifier.toString()
    val source: String
        get() = ExperienceSourceUtil.name(event.source)
    val experience: Int
        get() = event.experience
    val previousLevel: Int
        get() = event.previousLevel
    val currentLevel: Int
        get() = event.currentLevel
    val learnedMoves: List<String>
        get() = event.learnedMoves.map { it.name }.sorted()
}
