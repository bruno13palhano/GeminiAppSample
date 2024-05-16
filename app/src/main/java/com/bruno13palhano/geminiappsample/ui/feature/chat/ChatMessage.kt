package com.bruno13palhano.geminiappsample.ui.feature.chat

import java.util.UUID

enum class Participant {
    USER, MODEL, ERROR
}

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val text: String = "",
    val participant: Participant = Participant.USER,
    var isPending: Boolean = false
)