package com.bruno13palhano.core.di

import com.bruno13palhano.core.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.generationConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class LessRandom

@Qualifier
annotation class DefaultRandom

@Qualifier
annotation class MoreRandom

private const val modelName = "gemini-1.5-pro-latest"

private val safetySettings = listOf(
    SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.NONE),
    SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.NONE),
    SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.NONE),
    SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.NONE)
)

@InstallIn(SingletonComponent::class)
@Module
internal object AiModelModule {

    @LessRandom
    @Provides
    @Singleton
    fun provideLessRandomModel(): GenerativeModel = GenerativeModel(
        modelName = modelName,
        apiKey = BuildConfig.apiKey,
        generationConfig = generationConfig {
            temperature = 0.1f
            topP = 0.95f
            maxOutputTokens = 700
        },
        safetySettings = safetySettings
    )

    @DefaultRandom
    @Provides
    @Singleton
    fun providesDefaultRandomModel(): GenerativeModel = GenerativeModel(
        modelName = modelName,
        apiKey = BuildConfig.apiKey,
        generationConfig = generationConfig {
            temperature = 0.5f
            topP = 0.5f
            topK = 40
            maxOutputTokens = 700
        },
        safetySettings = safetySettings
    )

    @MoreRandom
    @Provides
    @Singleton
    fun providesMoreRandomModel(): GenerativeModel = GenerativeModel(
        modelName = modelName,
        apiKey = BuildConfig.apiKey,
        generationConfig = generationConfig {
            temperature = 0.97f
            topP = 0.3f
            topK = 60
            maxOutputTokens = 700
        },
        safetySettings = safetySettings
    )
}