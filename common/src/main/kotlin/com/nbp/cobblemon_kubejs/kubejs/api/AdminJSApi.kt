package com.nbp.cobblemon_kubejs.kubejs.api

import com.cobblemon.mod.common.Cobblemon
import com.cobblemon.mod.common.api.pokemon.PokemonProperties
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.MobSpawnType
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3

object AdminJSApi {
    fun givePokemon(player: ServerPlayer, properties: String): PokemonJSView {
        val pokemon = parseProperties(properties).create(player)
        check(Cobblemon.storage.getParty(player).add(pokemon)) {
            "Could not add Pokemon to ${player.scoreboardName}; both party and PC may be full."
        }
        return PokemonJSView(pokemon)
    }

    fun healParty(player: ServerPlayer): Int {
        val party = Cobblemon.storage.getParty(player)
        val healedCount = party.count { it.canBeHealed() }
        party.heal()
        return healedCount
    }

    fun spawnPokemon(player: ServerPlayer, properties: String): PokemonJSView {
        val position = player.position().add(player.lookAngle.scale(2.0))
        return spawnPokemon(player.serverLevel(), properties, position.x, position.y, position.z, player)
    }

    fun spawnPokemon(
        level: ServerLevel,
        properties: String,
        x: Double,
        y: Double,
        z: Double
    ): PokemonJSView {
        return spawnPokemon(level, properties, x, y, z, null)
    }

    private fun spawnPokemon(
        level: ServerLevel,
        properties: String,
        x: Double,
        y: Double,
        z: Double,
        player: ServerPlayer?
    ): PokemonJSView {
        val position = Vec3(x, y, z)
        val blockPosition = BlockPos.containing(position)
        require(Level.isInSpawnableBounds(blockPosition)) {
            "Invalid Pokemon spawn position: $x, $y, $z."
        }

        val entity = parseProperties(properties).createEntity(level, player)
        entity.moveTo(x, y, z, entity.yRot, entity.xRot)
        entity.entityData.set(PokemonEntity.SPAWN_DIRECTION, entity.random.nextFloat() * 360F)
        entity.finalizeSpawn(
            level,
            level.getCurrentDifficultyAt(blockPosition),
            MobSpawnType.COMMAND,
            null
        )
        check(level.addFreshEntity(entity)) {
            "Unable to spawn Pokemon at $x, $y, $z."
        }
        return PokemonJSView(entity.pokemon)
    }

    private fun parseProperties(value: String): PokemonProperties {
        val normalized = value.trim()
        require(normalized.isNotEmpty()) { "Pokemon properties cannot be empty." }

        val properties = PokemonProperties.parse(normalized)
        require(properties.species != null) {
            "Pokemon properties must include a valid species: '$value'."
        }
        return properties
    }
}
