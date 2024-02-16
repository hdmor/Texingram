package m.eight.texingram.data.remote

import kotlinx.coroutines.flow.Flow
import m.eight.texingram.domain.model.Message
import m.eight.texingram.util.Resource

interface ChatSocketService {

    suspend fun initSession(username: String): Resource<Unit>
    suspend fun sendMessage(message: String)
    fun observeMessages(): Flow<Message>
    suspend fun closeSession()

    companion object {
        const val BASE_URL = "ws://192.168.0.2:8080" // webSocket
    }

    sealed class Endpoints(val url: String) {
        data object ChatSocket : Endpoints("$BASE_URL/chat-socket")
    }
}