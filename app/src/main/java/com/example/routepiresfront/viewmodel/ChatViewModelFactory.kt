package com.example.routepiresfront.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.gov.ifgoiano.routepiresfront.repository.ChatRepository
import br.gov.ifgoiano.routepiresfront.repository.MensagemRepository
import com.example.routepiresfront.ui.chat.ChatViewModel

class ChatViewModelFactory(
    private val repository: ChatRepository,
    private val mensagemRepository: MensagemRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            return ChatViewModel(repository, mensagemRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}