package com.nbp.cobblemon_kubejs.kubejs

import com.nbp.cobblemon_kubejs.kubejs.event.BattleDefeatEventJS
import com.nbp.cobblemon_kubejs.kubejs.event.BattleFledEventJS
import com.nbp.cobblemon_kubejs.kubejs.event.PokedexChangedEventJS
import com.nbp.cobblemon_kubejs.kubejs.event.BattleVictoryEventJS
import com.nbp.cobblemon_kubejs.kubejs.event.EvolutionCompleteEventJS
import com.nbp.cobblemon_kubejs.kubejs.event.ExperienceGainedEventJS
import com.nbp.cobblemon_kubejs.kubejs.event.LevelUpEventJS
import com.nbp.cobblemon_kubejs.kubejs.event.PokemonGainedEventJS
import com.nbp.cobblemon_kubejs.kubejs.event.PokemonHealedEventJS
import com.nbp.cobblemon_kubejs.kubejs.event.PokemonRecallEventJS
import com.nbp.cobblemon_kubejs.kubejs.event.PokemonReleasedEventJS
import com.nbp.cobblemon_kubejs.kubejs.event.PokemonCapturedEventJS
import com.nbp.cobblemon_kubejs.kubejs.event.PokemonFaintedEventJS
import com.nbp.cobblemon_kubejs.kubejs.event.PokemonScannedEventJS
import com.nbp.cobblemon_kubejs.kubejs.event.PokemonSentEventJS
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

    @JvmField
    val POKEMON_SENT: EventHandler =
        GROUP.server("pokemonSent") { PokemonSentEventJS::class.java }

    @JvmField
    val POKEMON_FAINTED: EventHandler =
        GROUP.server("pokemonFainted") { PokemonFaintedEventJS::class.java }

    @JvmField
    val BATTLE_VICTORY: EventHandler =
        GROUP.server("battleVictory") { BattleVictoryEventJS::class.java }

    @JvmField
    val BATTLE_DEFEAT: EventHandler =
        GROUP.server("battleDefeat") { BattleDefeatEventJS::class.java }

    @JvmField
    val BATTLE_FLED: EventHandler =
        GROUP.server("battleFled") { BattleFledEventJS::class.java }

    @JvmField
    val EVOLUTION_COMPLETE: EventHandler =
        GROUP.server("evolutionComplete") { EvolutionCompleteEventJS::class.java }

    @JvmField
    val LEVEL_UP: EventHandler =
        GROUP.server("levelUp") { LevelUpEventJS::class.java }

    @JvmField
    val EXPERIENCE_GAINED: EventHandler =
        GROUP.server("experienceGained") { ExperienceGainedEventJS::class.java }

    @JvmField
    val POKEMON_HEALED: EventHandler =
        GROUP.server("pokemonHealed") { PokemonHealedEventJS::class.java }

    @JvmField
    val POKEMON_RECALL: EventHandler =
        GROUP.server("pokemonRecall") { PokemonRecallEventJS::class.java }

    @JvmField
    val POKEMON_GAINED: EventHandler =
        GROUP.server("pokemonGained") { PokemonGainedEventJS::class.java }

    @JvmField
    val POKEMON_RELEASED: EventHandler =
        GROUP.server("pokemonReleased") { PokemonReleasedEventJS::class.java }
}
