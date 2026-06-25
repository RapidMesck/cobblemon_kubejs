package com.nbp.cobblemon_kubejs.kubejs

import com.nbp.cobblemon_kubejs.kubejs.event.PokedexChangedEventJS
import com.nbp.cobblemon_kubejs.kubejs.event.PokemonCapturedEventJS
import com.nbp.cobblemon_kubejs.kubejs.event.PokemonScannedEventJS
import dev.latvian.mods.kubejs.event.EventGroup
import dev.latvian.mods.kubejs.event.EventHandler

object CobblemonKubeJSEvents {
    @JvmField
    val GROUP: EventGroup = EventGroup.of("CobblemonEvents")

    @JvmField
    val POKEDEX_CHANGED: EventHandler =
        GROUP.server("pokedexChanged") { PokedexChangedEventJS::class.java }

    @JvmField
    val POKEMON_SCANNED: EventHandler =
        GROUP.server("pokemonScanned") { PokemonScannedEventJS::class.java }

    @JvmField
    val POKEMON_CAPTURED: EventHandler =
        GROUP.server("pokemonCaptured") { PokemonCapturedEventJS::class.java }
}
