package com.nbp.cobblemon_kubejs.kubejs.api

object CobblemonJSBinding {
    @JvmField
    val pokedex = PokedexJSApi

    @JvmField
    val species = SpeciesJSApi

    @JvmField
    val pokemon = PokemonJSApi

    @JvmField
    val admin = AdminJSApi
}
