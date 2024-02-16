package m.eight.texingram.presentation.chat

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import m.eight.texingram.data.remote.ChatSocketService
import m.eight.texingram.data.remote.MessageService
import m.eight.texingram.util.Resource
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val messageService: MessageService,
    private val chatSocketService: ChatSocketService
) : ViewModel() {

    private val _message = mutableStateOf("")
    val message: State<String> get() = _message

    private val _chatState = mutableStateOf(ChatState())
    val chatState: State<ChatState> get() = _chatState

    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent = _toastEvent.asSharedFlow()

    fun connectToChat() {
        getAllMessages()
        savedStateHandle.get<String>("username")?.let {
            viewModelScope.launch {
                when (val result = chatSocketService.initSession(it)) {
                    is Resource.Error -> _toastEvent.emit(result.message ?: "Unknown error")
                    is Resource.Success -> chatSocketService.observeMessages().onEach { message ->
                        val newList = _chatState.value.messages.toMutableList().apply { add(0, message) }
                        _chatState.value = chatState.value.copy(messages = newList)
                    }.launchIn(viewModelScope)
                }
            }
        }
    }

    fun onMessageChange(message: String) {
        _message.value = message
    }

    fun disconnect() {
        viewModelScope.launch { chatSocketService.closeSession() }
    }

    fun getAllMessages() {
        viewModelScope.launch {
            _chatState.value = chatState.value.copy(isLoading = true)
            val result = messageService.getAll()
            _chatState.value = chatState.value.copy(messages = result, isLoading = false)
        }
    }

    fun sendMessage() {
        viewModelScope.launch {
            if (message.value.isNotBlank()) chatSocketService.sendMessage(message.value)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disconnect()
    }
}