package com.bruno13palhano.geminiappsample.ui.feature.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.core.data.repository.Repository
import com.bruno13palhano.core.di.GenerativeModelRep
import com.google.ai.client.generativeai.type.asTextOrNull
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
    private val chat = repository.moreRandoModel().startChat()

    private val _uiState: MutableStateFlow<ChatUIState> =
        MutableStateFlow(ChatUIState(chat.history.map { content ->
            ChatMessage(
                text = content.parts.first().asTextOrNull() ?: "",
                participant = if (content.role == "user") Participant.USER else Participant.MODEL,
                isPending = false
            )
        }))
    val uiState = _uiState.asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5000),
            initialValue = ChatUIState()
        )

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
                val response = chat.sendMessage(message)

                _uiState.value.replaceLastPendingMessage()

                response.text?.let { modelResponse ->
                    _uiState.value.addMessage(
                        ChatMessage(
                            text = modelResponse,
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