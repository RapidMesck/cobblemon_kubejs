package com.nbp.cobblemon_kubejs.neoforge.network

import com.nbp.cobblemon_kubejs.kubejs.client.ClientPokedexCache
import com.nbp.cobblemon_kubejs.kubejs.client.PokedexClientSync
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.loading.FMLEnvironment
import net.neoforged.neoforge.common.NeoForge
import net.neoforged.neoforge.event.entity.player.PlayerEvent
import net.neoforged.neoforge.network.PacketDistributor
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent

object CobblemonKubejsNetwork {
    fun init(modBus: IEventBus) {
        modBus.addListener(::registerPayloads)
        NeoForge.EVENT_BUS.addListener(::onPlayerLoggedIn)

        PokedexClientSync.install(
            snapshotSender = { player, progress ->
                PacketDistributor.sendToPlayer(player, PokedexSnapshotPayload(progress))
            },
            updateSender = { player, species, progress ->
                PacketDistributor.sendToPlayer(
                    player,
                    PokedexUpdatePayload(species, progress)
                )
            }
        )

        if (FMLEnvironment.dist == Dist.CLIENT) {
            Class.forName(
                "com.nbp.cobblemon_kubejs.neoforge.network.ClientNetworkHooks"
            ).getMethod("register").invoke(null)
        }
    }

    private fun onPlayerLoggedIn(event: PlayerEvent.PlayerLoggedInEvent) {
        val player = event.entity as? net.minecraft.server.level.ServerPlayer ?: return
        PokedexClientSync.sendSnapshot(player)
    }

    private fun registerPayloads(event: RegisterPayloadHandlersEvent) {
        val registrar = event.registrar("1")
        registrar.playToClient(
            PokedexSnapshotPayload.TYPE,
            PokedexSnapshotPayload.CODEC
        ) { payload, _ ->
            ClientPokedexCache.replace(payload.progress)
        }
        registrar.playToClient(
            PokedexUpdatePayload.TYPE,
            PokedexUpdatePayload.CODEC
        ) { payload, _ ->
            ClientPokedexCache.update(payload.species, payload.progress)
        }
    }
}
