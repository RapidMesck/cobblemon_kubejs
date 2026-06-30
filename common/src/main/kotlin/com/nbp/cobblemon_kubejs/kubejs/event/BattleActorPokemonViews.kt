package com.nbp.cobblemon_kubejs.kubejs.event

import com.cobblemon.mod.common.api.battles.model.actor.BattleActor
import com.nbp.cobblemon_kubejs.kubejs.api.PokemonJSView

internal fun List<BattleActor>.toPokemonViews(): List<PokemonJSView> {
    return flatMap { actor ->
        actor.pokemonList.map { battlePokemon ->
            PokemonJSView(battlePokemon.effectedPokemon)
        }
    }
}
