package com.bruno13palhano.geminiappsample.ui.feature.chat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.core.data.repository.Repository
import com.bruno13palhano.core.di.GenerativeModelRep
import com.bruno13palhano.core.model.ChatMessage
import com.bruno13palhano.core.model.ModelType
import com.bruno13palhano.geminiappsample.ui.feature.chat.ChatUIState
import com.bruno13palhano.core.model.Participant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    @GenerativeModelRep private val repository: Repository
) : ViewModel() {
    private val _uiState: MutableStateFlow<ChatUIState> =
        MutableStateFlow(ChatUIState())
    val uiState = _uiState.asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5000),
            initialValue = ChatUIState()
        )

    fun setModel(model: String) {
        repository.setModel(ModelType.valueOf(model))
    }

    fun getMessages() {
        viewModelScope.launch {
            repository.getMessages().collect {
                _uiState.value = ChatUIState(it)
            }
        }
    }

    fun sendMessage(message: String) {
        _uiState.value.addMessage(
            ChatMessage(
                text = message,
                participant = Participant.USER,
                isPending = true
            )
        )
        viewModelScope.launch {
            try {
                val response = repository.sendMessage(message = message)

                _uiState.value.replaceLastPendingMessage()

                response.let {
                    _uiState.value.addMessage(
                        ChatMessage(
                            text = it,
                            participant = Participant.MODEL,
                            isPending = false
                        )
                    )
                }
            } catch (e: Exception) {
                _uiState.value.replaceLastPendingMessage()
                _uiState.value.addMessage(
                    ChatMessage(
                        text = e.localizedMessage,
                        participant = Participant.ERROR
                    )
                )
            }
        }
    }
}