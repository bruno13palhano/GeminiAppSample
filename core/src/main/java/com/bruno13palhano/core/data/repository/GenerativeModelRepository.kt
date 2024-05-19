package com.bruno13palhano.core.data.repository

import com.bruno13palhano.core.di.DefaultRandom
import com.bruno13palhano.core.di.LessRandom
import com.bruno13palhano.core.di.MoreRandom
import com.google.ai.client.generativeai.GenerativeModel
import javax.inject.Inject

internal class GenerativeModelRepository @Inject constructor(
    @LessRandom private val lessRandom: GenerativeModel,
    @DefaultRandom private val defaultRandom: GenerativeModel,
    @MoreRandom private val moreRandom: GenerativeModel
) : Repository {
    override fun lessRandomModel(): GenerativeModel = lessRandom

    override fun defaultModel(): GenerativeModel = defaultRandom

    override fun moreRandoModel(): GenerativeModel = moreRandom
}