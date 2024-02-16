package m.eight.texingram.presentation.chat

import m.eight.texingram.domain.model.Message

data class ChatState(
    val messages: List<Message> = emptyList(),
    val isLoading: Boolean = false
)
