package com.nbp.cobblemon_kubejs.kubejs.util

import com.cobblemon.mod.common.api.pokemon.experience.BattleExperienceSource
import com.cobblemon.mod.common.api.pokemon.experience.CandyExperienceSource
import com.cobblemon.mod.common.api.pokemon.experience.CommandExperienceSource
import com.cobblemon.mod.common.api.pokemon.experience.ExperienceSource
import com.cobblemon.mod.common.api.pokemon.experience.SidemodExperienceSource

object ExperienceSourceUtil {
    fun name(source: ExperienceSource): String {
        return when (source) {
            is BattleExperienceSource -> "battle"
            is CandyExperienceSource -> "interaction"
            is CommandExperienceSource -> "command"
            is SidemodExperienceSource -> source.sidemodId
            else -> "unknown"
        }
    }
}
