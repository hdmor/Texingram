package m.eight.texingram.data.remote

import m.eight.texingram.domain.model.Message

interface MessageService {

    suspend fun getAll(): List<Message>

    companion object {
        const val BASE_URL = "http://10.0.2.2:8080"
    }

    sealed class Endpoints(val url: String) {
        data object GetAllMessages : Endpoints("$BASE_URL/messages")
    }
}