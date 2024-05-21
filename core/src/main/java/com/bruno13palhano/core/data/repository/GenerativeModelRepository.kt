package com.bruno13palhano.core.data.repository

import com.bruno13palhano.core.di.LessRandom
import com.bruno13palhano.core.model.ChatMessage
import com.bruno13palhano.core.model.Participant
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.asTextOrNull
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class LessRandomModelRepository @Inject constructor(
    @LessRandom private val generativeModel: GenerativeModel
) : Repository {
    private val chat = generativeModel.startChat()

    override suspend fun messages(): Flow<List<ChatMessage>> {
        return flow {
            emit(
                chat.history.map {
                    ChatMessage(
                        text = it.parts.first().asTextOrNull() ?: "",
                        participant = if (it.role == "user") Participant.USER else Participant.MODEL,
                        isPending = false
                    )
                }
            )
        }
    }

    override suspend fun sendMessage(message: String): String {
        return try {
            val response = chat.sendMessage(message)

            response.text ?: ""
        } catch (e: Exception) {
            e.printStackTrace()
            e.localizedMessage
        }
    }
}