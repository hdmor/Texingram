package m.eight.texingram.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import m.eight.texingram.data.remote.dto.MessageDto
import m.eight.texingram.domain.model.Message

class MessageServiceImpl(private val client: HttpClient) : MessageService {
    override suspend fun getAll(): List<Message> = try {
        client.get(MessageService.Endpoints.GetAllMessages.url).body<List<MessageDto>>().map { it.toMessage() }
    } catch (exception: Exception) {
        emptyList()
    }
}