package com.bruno13palhano.geminiappsample.ui.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {

    @Serializable
    object Main

    @Serializable
    object Home

    @Serializable
    data class Chat(val name: String)
}