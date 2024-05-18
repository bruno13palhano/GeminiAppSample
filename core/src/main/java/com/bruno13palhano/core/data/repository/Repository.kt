package com.bruno13palhano.core.data.repository

import com.google.ai.client.generativeai.GenerativeModel

interface Repository {
    suspend fun lessRandomModel(): GenerativeModel
    suspend fun defaultModel(): GenerativeModel
    suspend fun moreRandoModel(): GenerativeModel
}