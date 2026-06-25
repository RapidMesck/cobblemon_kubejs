package com.nbp.cobblemon_kubejs.kubejs.api

import com.cobblemon.mod.common.pokemon.Pokemon
import com.cobblemon.mod.common.api.pokemon.stats.Stats
import net.minecraft.core.registries.BuiltInRegistries
import kotlin.jvm.JvmName

class PokemonJSView(
    pokemon: Pokemon,
    val storage: String? = null,
    val slot: Int? = null,
    val box: Int? = null
) {
    val uuid: String = pokemon.uuid.toString()
    val species: String = pokemon.species.resourceIdentifier.toString()
    val form: String = pokemon.form.name
    val name: String = pokemon.getDisplayName().string
    val level: Int = pokemon.level
    val shiny: Boolean = pokemon.shiny
    val gender: String = pokemon.gender.name.lowercase()
    val currentHealth: Int = pokemon.currentHealth
    val maxHealth: Int = pokemon.maxHealth
    val friendship: Int = pokemon.friendship
    val types: List<String> = pokemon.form.types.map { it.showdownId() }
    val ownerUuid: String? = pokemon.getOwnerUUID()?.toString()
    val experience: Int = pokemon.experience
    val experienceToNextLevel: Int = pokemon.getExperienceToNextLevel()
    val nature: String = pokemon.nature.name.toString()
    val effectiveNature: String = pokemon.effectiveNature.name.toString()
    val ability: String = pokemon.ability.name
    val abilityDisplayName: String = pokemon.ability.displayName
    val teraType: String = pokemon.teraType.showdownId()
    val heldItem: String? = pokemon.heldItem()
        .takeUnless { it.isEmpty }
        ?.let { BuiltInRegistries.ITEM.getKey(it.item).toString() }
    val status: String? = pokemon.status?.status?.name?.toString()
    val statusSecondsLeft: Int? = pokemon.status?.secondsLeft
    val originalTrainerType: String = pokemon.originalTrainerType.name.lowercase()
    val originalTrainer: String? = pokemon.originalTrainer
    val originalTrainerName: String? = pokemon.originalTrainerName
    val moves: List<MoveJSView> = pokemon.moveSet.getMoves().map(::MoveJSView)
    val ivs: Map<String, Int> = Stats.PERMANENT.associate {
        it.showdownId to (pokemon.ivs[it] ?: 0)
    }
    val effectiveIvs: Map<String, Int> = Stats.PERMANENT.associate {
        it.showdownId to pokemon.ivs.getEffectiveBattleIV(it)
    }
    val evs: Map<String, Int> = Stats.PERMANENT.associate {
        it.showdownId to (pokemon.evs[it] ?: 0)
    }

    @get:JvmName("getIsFainted")
    val isFainted: Boolean = pokemon.isFainted()
}
