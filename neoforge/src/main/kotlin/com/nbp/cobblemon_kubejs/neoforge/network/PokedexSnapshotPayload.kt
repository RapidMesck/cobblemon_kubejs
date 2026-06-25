package com.nbp.cobblemon_kubejs.neoforge.network

import com.nbp.cobblemon_kubejs.CobblemonKubejs
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.resources.ResourceLocation

data class PokedexSnapshotPayload(
    val progress: Map<String, String>
) : CustomPacketPayload {
    override fun type(): CustomPacketPayload.Type<out CustomPacketPayload> = TYPE

    companion object {
        @JvmField
        val TYPE = CustomPacketPayload.Type<PokedexSnapshotPayload>(
            ResourceLocation.fromNamespaceAndPath(
                CobblemonKubejs.MOD_ID,
                "pokedex_snapshot"
            )
        )

        @JvmField
        val CODEC: StreamCodec<RegistryFriendlyByteBuf, PokedexSnapshotPayload> =
            object : StreamCodec<RegistryFriendlyByteBuf, PokedexSnapshotPayload> {
                override fun encode(
                    buffer: RegistryFriendlyByteBuf,
                    payload: PokedexSnapshotPayload
                ) {
                    buffer.writeVarInt(payload.progress.size)
                    payload.progress.forEach { (species, progress) ->
                        buffer.writeResourceLocation(ResourceLocation.parse(species))
                        buffer.writeByte(progressCode(progress))
                    }
                }

                override fun decode(buffer: RegistryFriendlyByteBuf): PokedexSnapshotPayload {
                    val size = buffer.readVarInt()
                    require(size in 0..10_000) { "Invalid Pokedex snapshot size: $size" }
                    val progress = buildMap(size) {
                        repeat(size) {
                            put(
                                buffer.readResourceLocation().toString(),
                                progressName(buffer.readUnsignedByte().toInt())
                            )
                        }
                    }
                    return PokedexSnapshotPayload(progress)
                }
            }

        internal fun progressCode(progress: String): Int {
            return when (progress) {
                "caught" -> 2
                "encountered" -> 1
                else -> 0
            }
        }

        internal fun progressName(code: Int): String {
            return when (code) {
                2 -> "caught"
                1 -> "encountered"
                else -> "none"
            }
        }
    }
}
