package com.bruno13palhano.core.data.repository

import com.bruno13palhano.core.data.local.Data
import com.bruno13palhano.core.di.DefaultRandom
import com.bruno13palhano.core.di.HistoryData
import com.bruno13palhano.core.di.LessRandom
import com.bruno13palhano.core.di.MoreRandom
import com.bruno13palhano.core.model.ChatMessage
import com.bruno13palhano.core.model.History
import com.bruno13palhano.core.model.ModelType
import com.bruno13palhano.core.model.Participant
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class GenerativeModelRepository @Inject constructor(
    @LessRandom private val lessRandom: GenerativeModel,
    @DefaultRandom private val defaultRandom: GenerativeModel,
    @MoreRandom private val moreRandom: GenerativeModel,
    @HistoryData private val historyData: Data<History>
) : Repository {
    private var chat = defaultRandom.startChat()

    override fun getMessages(): Flow<List<ChatMessage>> {
        return historyData.getAll().map {
                it.map { history ->
                    ChatMessage(
                        text = history.textContent,
                        participant = if (history.participant == "user") {
                            Participant.USER
                        } else {
                            Participant.MODEL
                        },
                        isPending = history.isPending
                    )
                }
            }
    }

    override suspend fun sendMessage(message: String): String {
        return try {
            historyData.insert(
                History(id = 0, participant = "user", textContent = message, isPending = true)
            )

            val response = chat.sendMessage(message)

            val newMessage = response.text ?: ""

            historyData.update(
                History(
                    id = historyData.getLastInsertId(),
                    participant = "user",
                    textContent = message,
                    isPending = false
                )
            )
            historyData.insert(
                History(
                    id = 0,
                    participant = "model",
                    textContent = newMessage,
                    isPending = false
                )
            )

            newMessage
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