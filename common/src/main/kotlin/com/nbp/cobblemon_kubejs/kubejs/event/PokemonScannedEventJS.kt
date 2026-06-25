package com.nbp.cobblemon_kubejs.kubejs.event

import com.cobblemon.mod.common.api.events.pokedex.scanning.PokemonScannedEvent
import dev.latvian.mods.kubejs.event.KubeEvent
import net.minecraft.server.level.ServerPlayer
import kotlin.jvm.JvmName

class PokemonScannedEventJS(
    private val event: PokemonScannedEvent
) : KubeEvent {
    val player: ServerPlayer
        get() = event.player

    val playerUuid: String
        get() = event.player.uuid.toString()

    val species: String
        get() = event.scannedPokemonEntityData.getApparentSpecies().resourceIdentifier.toString()

    val form: String
        get() = event.scannedPokemonEntityData.getApparentForm().name

    @get:JvmName("getIsOwned")
    val isOwned: Boolean
        get() = event.isOwned

    val level: Int
        get() = event.scannedPokemonEntityData.pokemon.level

    val shiny: Boolean
        get() = event.scannedPokemonEntityData.pokemon.shiny
}
