package com.bruno13palhano.geminiappsample.ui.feature.chat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bruno13palhano.core.data.repository.Repository
import com.bruno13palhano.core.di.GenerativeModelRep
import com.bruno13palhano.core.model.ChatMessage
import com.bruno13palhano.core.model.ModelType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    @GenerativeModelRep private val repository: Repository
) : ViewModel() {
    private val _uiState: MutableStateFlow<List<ChatMessage>> =
        MutableStateFlow(listOf())
    val uiState = _uiState.asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5000),
            initialValue = listOf()
        )

    fun setModel(model: String) {
        repository.setModel(ModelType.valueOf(model))
    }

    fun getMessages() {
        viewModelScope.launch {
            repository.getMessages().collect {
                _uiState.value = it
            }
        }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch {
            try { repository.sendMessage(message = message) }
            catch (e: Exception) { _uiState.value = listOf() }
        }
    }
}