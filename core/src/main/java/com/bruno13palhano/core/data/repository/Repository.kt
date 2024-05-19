package com.bruno13palhano.core.data.repository

import com.google.ai.client.generativeai.GenerativeModel

interface Repository {
    fun lessRandomModel(): GenerativeModel
    fun defaultModel(): GenerativeModel
    fun moreRandoModel(): GenerativeModel
}