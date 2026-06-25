package com.nbp.cobblemon_kubejs.kubejs.event

import com.cobblemon.mod.common.api.battles.model.PokemonBattle
import com.cobblemon.mod.common.api.battles.model.actor.BattleActor
import com.cobblemon.mod.common.api.events.battles.BattleFledEvent
import com.cobblemon.mod.common.api.events.battles.BattleVictoryEvent
import com.cobblemon.mod.common.util.getPlayer
import dev.latvian.mods.kubejs.event.KubeEvent
import net.minecraft.server.level.ServerPlayer
import kotlin.jvm.JvmName

class BattleFledEventJS private constructor(
    private val battle: PokemonBattle,
    private val fleeingActors: List<BattleActor>,
    val reason: String
) : KubeEvent {
    val battleId: String
        get() = battle.battleId.toString()

    val players: List<ServerPlayer>
        get() = fleeingActors
            .flatMap { it.getPlayerUUIDs() }
            .mapNotNull { it.getPlayer() }

    val player: ServerPlayer?
        get() = players.firstOrNull()

    val playerUuids: List<String>
        get() = fleeingActors.flatMap { it.getPlayerUUIDs() }.map { it.toString() }

    val playerUuid: String?
        get() = playerUuids.firstOrNull()

    val actorTypes: List<String>
        get() = fleeingActors.map { it.type.name.lowercase() }

    @get:JvmName("getWasForfeit")
    val wasForfeit: Boolean
        get() = reason == "forfeit"

    @get:JvmName("getWasEscape")
    val wasEscape: Boolean
        get() = reason == "escape"

    @get:JvmName("getIsPvp")
    val isPvp: Boolean
        get() = battle.isPvP

    @get:JvmName("getIsPvn")
    val isPvn: Boolean
        get() = battle.isPvN

    @get:JvmName("getIsPvw")
    val isPvw: Boolean
        get() = battle.isPvW

    companion object {
        private val FORCE_LOSE_PATTERN = Regex("""^>forcelose\s+(\S+)\s*$""")

        fun fromEscape(event: BattleFledEvent): BattleFledEventJS {
            return BattleFledEventJS(event.battle, listOf(event.player), "escape")
        }

        fun fromForfeit(event: BattleVictoryEvent): BattleFledEventJS {
            return BattleFledEventJS(event.battle, getForfeitingActors(event), "forfeit")
        }

        fun wasForfeit(event: BattleVictoryEvent): Boolean {
            return getForfeitingActors(event).isNotEmpty()
        }

        fun getForfeitingActors(event: BattleVictoryEvent): List<BattleActor> {
            val showdownIds = event.battle.battleLog.mapNotNull { line ->
                FORCE_LOSE_PATTERN.matchEntire(line.trim())?.groupValues?.get(1)
            }.toSet()

            return event.losers.filter { it.showdownId in showdownIds }
        }
    }
}
