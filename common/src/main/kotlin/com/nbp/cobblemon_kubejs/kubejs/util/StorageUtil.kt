package com.nbp.cobblemon_kubejs.kubejs.util

import com.cobblemon.mod.common.api.storage.PokemonStore
import com.cobblemon.mod.common.api.storage.party.PartyStore
import com.cobblemon.mod.common.api.storage.pc.PCStore

object StorageUtil {
    fun name(storage: PokemonStore<*>): String {
        return when (storage) {
            is PartyStore -> "party"
            is PCStore -> "pc"
            else -> "custom"
        }
    }
}
