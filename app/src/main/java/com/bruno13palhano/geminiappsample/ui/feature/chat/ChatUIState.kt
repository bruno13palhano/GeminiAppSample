package com.bruno13palhano.geminiappsample.ui.feature.chat

import androidx.compose.runtime.toMutableStateList

class ChatUIState(
    messages: List<ChatMessage> = emptyList()
) {
    private val _messages: MutableList<ChatMessage> = messages.toMutableStateList()
    val messages: List<ChatMessage> = _messages

    fun addMessage(message: ChatMessage) = _messages.add(message)

    fun replaceLastPendingMessage() {
        val lastMessage = _messages.lastOrNull()
        lastMessage?.let {
            val newMessage = lastMessage.apply { isPending = false }
            _messages.removeLast()
            _messages.add(newMessage)
        }
    }
}