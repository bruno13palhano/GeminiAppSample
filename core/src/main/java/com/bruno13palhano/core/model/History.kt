package com.bruno13palhano.core.model

data class History(
    val id: Long,
    val participant: String,
    val textContent: String,
    val isPending: Boolean
)