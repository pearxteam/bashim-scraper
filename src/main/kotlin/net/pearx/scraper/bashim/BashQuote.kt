package net.pearx.scraper.bashim

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.Instant

@Serializable
data class BashQuote(
    val id: Int,
    @Serializable(with = InstantSerializer::class)
    val date: Instant,
    val rating: Int?,
    val text: String
)

object InstantSerializer : KSerializer<Instant> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("net.pearx.scraper.bashim.InstantSerializer", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Instant = Instant.parse(decoder.decodeString())

    override fun serialize(encoder: Encoder, value: Instant) {
        encoder.encodeString(value.toString())
    }
}