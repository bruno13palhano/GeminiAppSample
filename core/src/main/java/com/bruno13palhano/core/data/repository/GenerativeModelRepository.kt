package com.bruno13palhano.core.data.repository

import com.bruno13palhano.core.di.DefaultRandom
import com.bruno13palhano.core.di.LessRandom
import com.bruno13palhano.core.di.MoreRandom
import com.bruno13palhano.core.model.ChatMessage
import com.bruno13palhano.core.model.ModelType
import com.bruno13palhano.core.model.Participant
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.asTextOrNull
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class GenerativeModelRepository @Inject constructor(
    @LessRandom private val lessRandom: GenerativeModel,
    @DefaultRandom private val defaultRandom: GenerativeModel,
    @MoreRandom private val moreRandom: GenerativeModel
) : Repository {
    private var chat = defaultRandom.startChat()

    override fun getMessages(): Flow<List<ChatMessage>> {
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

    override fun setModel(model: ModelType) {
        chat = when (model) {
            ModelType.LESS -> lessRandom.startChat()
            ModelType.DEFAULT -> defaultRandom.startChat()
            ModelType.MORE -> moreRandom.startChat()
        }
    }
}