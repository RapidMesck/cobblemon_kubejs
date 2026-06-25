package com.nbp.cobblemon_kubejs.neoforge.network

import com.nbp.cobblemon_kubejs.CobblemonKubejs
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.resources.ResourceLocation

data class PokedexUpdatePayload(
    val species: String,
    val progress: String
) : CustomPacketPayload {
    override fun type(): CustomPacketPayload.Type<out CustomPacketPayload> = TYPE

    companion object {
        @JvmField
        val TYPE = CustomPacketPayload.Type<PokedexUpdatePayload>(
            ResourceLocation.fromNamespaceAndPath(
                CobblemonKubejs.MOD_ID,
                "pokedex_update"
            )
        )

        @JvmField
        val CODEC: StreamCodec<RegistryFriendlyByteBuf, PokedexUpdatePayload> =
            object : StreamCodec<RegistryFriendlyByteBuf, PokedexUpdatePayload> {
                override fun encode(
                    buffer: RegistryFriendlyByteBuf,
                    payload: PokedexUpdatePayload
                ) {
                    buffer.writeResourceLocation(ResourceLocation.parse(payload.species))
                    buffer.writeByte(PokedexSnapshotPayload.progressCode(payload.progress))
                }

                override fun decode(buffer: RegistryFriendlyByteBuf): PokedexUpdatePayload {
                    return PokedexUpdatePayload(
                        buffer.readResourceLocation().toString(),
                        PokedexSnapshotPayload.progressName(
                            buffer.readUnsignedByte().toInt()
                        )
                    )
                }
            }
    }
}
