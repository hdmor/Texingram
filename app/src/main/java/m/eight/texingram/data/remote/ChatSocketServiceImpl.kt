package m.eight.texingram.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.url
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.serialization.json.Json
import m.eight.texingram.data.remote.dto.MessageDto
import m.eight.texingram.domain.model.Message
import m.eight.texingram.util.Resource

class ChatSocketServiceImpl(private val client: HttpClient) : ChatSocketService {

    private var socket: WebSocketSession? = null
    override suspend fun initSession(username: String): Resource<Unit> = try {
        socket = client.webSocketSession {
            url("${ChatSocketService.Endpoints.ChatSocket.url}?username=${username}")
        }

        if (socket?.isActive == true) Resource.Success(Unit)
        else Resource.Error("Couldn't establish a connection.")

    } catch (exception: Exception) {
        Resource.Error(exception.localizedMessage ?: "Unknown message")
    }

    override suspend fun sendMessage(message: String) {
        try {
            socket?.send(Frame.Text(message))
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    override fun observeMessages(): Flow<Message> = try {
        socket?.incoming?.receiveAsFlow()?.filter { it is Frame.Text }?.map {
            val json = (it as? Frame.Text)?.readText() ?: ""
            val messageDto = Json.decodeFromString<MessageDto>(json)
            messageDto.toMessage()
        } ?: flow { }
    } catch (exception: Exception) {
        exception.printStackTrace()
        flow {}
    }

    override suspend fun closeSession() {
        socket?.close()
    }
}