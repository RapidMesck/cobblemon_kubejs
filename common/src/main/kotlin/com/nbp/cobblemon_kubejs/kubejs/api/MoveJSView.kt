package com.nbp.cobblemon_kubejs.kubejs.api

import com.cobblemon.mod.common.api.moves.Move

class MoveJSView(move: Move) {
    val name: String = move.name
    val displayName: String = move.displayName.string
    val type: String = move.type.showdownId()
    val category: String = move.damageCategory.name.lowercase()
    val power: Double = move.power
    val accuracy: Double = move.accuracy
    val currentPp: Int = move.currentPp
    val maxPp: Int = move.maxPp
}
