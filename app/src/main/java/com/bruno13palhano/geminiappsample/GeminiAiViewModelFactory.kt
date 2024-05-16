package com.bruno13palhano.geminiappsample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.bruno13palhano.geminiappsample.ui.feature.chat.ChatViewModel
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.generationConfig

val GeminiAiViewModelFactory = object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {
        val config = generationConfig { temperature = 0.7f }

        return with(modelClass) {
            when {
                isAssignableFrom(ChatViewModel::class.java) -> {
                    val generativeModel = GenerativeModel(
                        modelName = "gemini-1.0-pro",
                        apiKey = BuildConfig.apiKey,
                        generationConfig = config,
                        safetySettings = listOf(
                            SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.NONE),
                            SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.NONE),
                            SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.NONE),
                            SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.NONE)
                        )
                    )
                    ChatViewModel(generativeModel)
                }
                else -> throw IllegalArgumentException("Unknown ViewModel class")
            }
        } as T
    }
}