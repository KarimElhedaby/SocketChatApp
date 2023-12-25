package com.example.socketapp.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.WebSocketMessageEntity
import com.example.domain.usecase.*
import com.example.socketapp.presentation.ui.adapter.MessageAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val closeWebSocketConnection: CloseWebSocketConnection,
    private val sendMessageUseCase: SendMessageUseCase,
    private val receiveMessagesUseCase: ReceiveMessagesUseCase,
    private val cacheMessagesUseCase: CacheMessagesUseCase,
    private val receiveCachedMessagesUseCase: ReceiveCachedMessagesUseCase
) : ViewModel() {

    private val _incomingMessages = MutableLiveData<String>()
    val incomingMessages: LiveData<String> get() = _incomingMessages

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _sentMessageStatus = MutableLiveData<Boolean>()
    val sentMessageStatus: LiveData<Boolean> get() = _sentMessageStatus

    private val _cachedMessages = MutableLiveData<List<WebSocketMessageEntity>>()
    val cachedMessages: LiveData<List<WebSocketMessageEntity>> get() = _cachedMessages

    //send messages to server
    fun sendMessage(message: String) {
        viewModelScope.launch {
            try {
                sendMessageUseCase(message)
                cacheMessagesUseCase(WebSocketMessageEntity(content = message, type = MessageAdapter.VIEW_TYPE_ME))
                _sentMessageStatus.value = true
            } catch (e: Exception) {
                _sentMessageStatus.value = false
                _errorMessage.value = "Error sending message: ${e.message}"
            }
        }
    }

    // receive messages from server
    fun receiveMessages() {
        viewModelScope.launch {
            receiveMessagesUseCase()
                .catch { e ->
                    _errorMessage.value = "Error receiving message: ${e.message}"
                }
                .collect { message ->
                    cacheMessagesUseCase(WebSocketMessageEntity(content = message,type = MessageAdapter.VIEW_TYPE_SERVER))
                    _incomingMessages.value = message
                }
        }
    }

    // receive cached messages from db
    fun receiveCachedMessages() {
        viewModelScope.launch {
            receiveCachedMessagesUseCase()
                .catch { e ->
                    _errorMessage.value = "Error receiving message: ${e.message}"
                }
                .collect { cachedMessages ->
                    _cachedMessages.value = cachedMessages
                }
        }

    }
    // close websocket connection
    override fun onCleared() {
        // clear websocket connection to stop listen observers msgs from server
        viewModelScope.launch { closeWebSocketConnection() }
        super.onCleared()

    }
}
