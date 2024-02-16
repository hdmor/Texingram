package m.eight.texingram.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(
    val id: String,
    val username: String,
    val message: String,
    val timestamp: Long
)
