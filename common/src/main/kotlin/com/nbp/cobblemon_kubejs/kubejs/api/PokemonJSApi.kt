package com.nbp.cobblemon_kubejs.kubejs.api

import com.cobblemon.mod.common.Cobblemon
import com.nbp.cobblemon_kubejs.kubejs.util.SpeciesUtil
import com.nbp.cobblemon_kubejs.kubejs.util.TypeUtil
import net.minecraft.server.level.ServerPlayer

object PokemonJSApi {
    fun getParty(player: ServerPlayer): List<PokemonJSView?> {
        val party = Cobblemon.storage.getParty(player)
        return party.toGappyList().mapIndexed { slot, pokemon ->
            pokemon?.let { PokemonJSView(it, storage = "party", slot = slot) }
        }
    }

    fun getPC(player: ServerPlayer): List<PokemonJSView> {
        val pc = Cobblemon.storage.getPC(player)
        return pc.boxes.flatMapIndexed { boxIndex, box ->
            box.toList().mapIndexedNotNull { slot, pokemon ->
                pokemon?.let {
                    PokemonJSView(it, storage = "pc", slot = slot, box = boxIndex)
                }
            }
        }
    }

    fun countPartyByType(player: ServerPlayer, typeName: String): Int {
        val wantedType = TypeUtil.requireType(typeName).showdownId()
        return Cobblemon.storage.getParty(player).count { pokemon ->
            hasType(pokemon, wantedType)
        }
    }

    fun countPCByType(player: ServerPlayer, typeName: String): Int {
        val wantedType = TypeUtil.requireType(typeName).showdownId()
        return Cobblemon.storage.getPC(player).count { pokemon ->
            hasType(pokemon, wantedType)
        }
    }

    fun countStorageByType(player: ServerPlayer, typeName: String): Int {
        return countPartyByType(player, typeName) + countPCByType(player, typeName)
    }

    fun countPartyBySpecies(player: ServerPlayer, speciesId: String): Int {
        val wantedSpecies = SpeciesUtil.requireSpecies(speciesId).resourceIdentifier
        return Cobblemon.storage.getParty(player).count {
            it.species.resourceIdentifier == wantedSpecies
        }
    }

    fun countPCBySpecies(player: ServerPlayer, speciesId: String): Int {
        val wantedSpecies = SpeciesUtil.requireSpecies(speciesId).resourceIdentifier
        return Cobblemon.storage.getPC(player).count {
            it.species.resourceIdentifier == wantedSpecies
        }
    }

    fun countStorageBySpecies(player: ServerPlayer, speciesId: String): Int {
        return countPartyBySpecies(player, speciesId) + countPCBySpecies(player, speciesId)
    }

    private fun hasType(
        pokemon: com.cobblemon.mod.common.pokemon.Pokemon,
        wantedType: String
    ): Boolean {
        return pokemon.form.types.any {
            it.showdownId().equals(wantedType, ignoreCase = true)
        }
    }
}
