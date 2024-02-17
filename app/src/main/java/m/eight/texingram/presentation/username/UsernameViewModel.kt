package m.eight.texingram.presentation.username

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsernameViewModel @Inject constructor() : ViewModel() {

    private val _username = mutableStateOf("")
    val username: State<String> get() = _username

    private val _onJoinChat = MutableSharedFlow<String>()
    val onJoinChat get() = _onJoinChat.asSharedFlow()

    fun onUsernameChange(username: String) {
        _username.value = username
    }

    fun onJoinClick() {
        viewModelScope.launch {
            if (username.value.isNotBlank()) _onJoinChat.emit(username.value)
        }
    }
}