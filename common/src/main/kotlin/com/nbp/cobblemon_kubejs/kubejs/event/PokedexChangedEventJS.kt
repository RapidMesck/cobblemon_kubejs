package com.nbp.cobblemon_kubejs.kubejs.event

import com.cobblemon.mod.common.api.events.pokemon.PokedexDataChangedEvent
import com.cobblemon.mod.common.api.pokedex.PokedexEntryProgress
import com.cobblemon.mod.common.util.getPlayer
import dev.latvian.mods.kubejs.event.KubeEvent
import net.minecraft.server.level.ServerPlayer
import kotlin.jvm.JvmName

class PokedexChangedEventJS(
    private val event: PokedexDataChangedEvent.Post
) : KubeEvent {
    val player: ServerPlayer?
        get() = event.playerUUID.getPlayer()

    val playerUuid: String
        get() = event.playerUUID.toString()

    val species: String
        get() = event.record.speciesDexRecord.id.toString()

    val form: String
        get() = event.record.formName

    val progress: String
        get() = event.knowledge.name.lowercase()

    val knowledge: String
        get() = progress

    @get:JvmName("getIsSeen")
    val isSeen: Boolean
        get() = event.knowledge >= PokedexEntryProgress.ENCOUNTERED

    @get:JvmName("getIsCaught")
    val isCaught: Boolean
        get() = event.knowledge >= PokedexEntryProgress.CAUGHT
}
