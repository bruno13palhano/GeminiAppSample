package com.bruno13palhano.core.data.repository

import com.bruno13palhano.core.model.ChatMessage
import com.bruno13palhano.core.model.ModelType
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getMessages(): Flow<List<ChatMessage>>
    suspend fun sendMessage(message: String): String
    fun setModel(model: ModelType)
}