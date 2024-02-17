package m.eight.texingram.data.remote.dto

import kotlinx.serialization.Serializable
import m.eight.texingram.domain.model.Message
import m.eight.texingram.util.convertToTimeAgo

@Serializable
data class MessageDto(
    val id: String,
    val username: String,
    val message: String,
    val timestamp: Long
) {
    fun toMessage(): Message =
        Message(username = username, message = message, timestamp = convertToTimeAgo(timestamp))
}