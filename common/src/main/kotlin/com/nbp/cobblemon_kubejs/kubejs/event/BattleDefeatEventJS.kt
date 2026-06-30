package com.nbp.cobblemon_kubejs.kubejs.event

import com.cobblemon.mod.common.api.battles.model.actor.ActorType
import com.cobblemon.mod.common.api.events.battles.BattleVictoryEvent
import com.cobblemon.mod.common.util.getPlayer
import com.nbp.cobblemon_kubejs.kubejs.api.PokemonJSView
import dev.latvian.mods.kubejs.event.KubeEvent
import net.minecraft.server.level.ServerPlayer
import kotlin.jvm.JvmName

class BattleDefeatEventJS(
    private val event: BattleVictoryEvent
) : KubeEvent {
    val battleId: String
        get() = event.battle.battleId.toString()

    val defeatedPlayers: List<ServerPlayer>
        get() = event.losers
            .flatMap { it.getPlayerUUIDs() }
            .mapNotNull { it.getPlayer() }

    val defeatedPlayerUuids: List<String>
        get() = event.losers.flatMap { it.getPlayerUUIDs() }.map { it.toString() }

    val winnerPlayers: List<ServerPlayer>
        get() = event.winners
            .flatMap { it.getPlayerUUIDs() }
            .mapNotNull { it.getPlayer() }

    val winnerPlayerUuids: List<String>
        get() = event.winners.flatMap { it.getPlayerUUIDs() }.map { it.toString() }

    val defeatedTeam: List<PokemonJSView>
        get() = event.losers.toPokemonViews()

    val loserTeam: List<PokemonJSView>
        get() = defeatedTeam

    val winnerTeam: List<PokemonJSView>
        get() = event.winners.toPokemonViews()

    val defeatedTypes: List<String>
        get() = event.losers.map { it.type.name.lowercase() }

    val winnerTypes: List<String>
        get() = event.winners.map { it.type.name.lowercase() }

    val forfeitingPlayerUuids: List<String>
        get() = BattleFledEventJS.getForfeitingActors(event)
            .flatMap { it.getPlayerUUIDs() }
            .map { it.toString() }

    @get:JvmName("getWasForfeit")
    val wasForfeit: Boolean
        get() = BattleFledEventJS.wasForfeit(event)

    @get:JvmName("getWasWildCapture")
    val wasWildCapture: Boolean
        get() = event.wasWildCapture

    @get:JvmName("getIsPvp")
    val isPvp: Boolean
        get() = event.battle.isPvP

    @get:JvmName("getIsPvn")
    val isPvn: Boolean
        get() = event.battle.isPvN

    @get:JvmName("getIsPvw")
    val isPvw: Boolean
        get() = event.battle.isPvW

    val hasPlayerDefeat: Boolean
        get() = event.losers.any { it.type == ActorType.PLAYER }
}
