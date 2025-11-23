package com.example.routepiresfront.ui.comum.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.gov.ifgoiano.routepiresfront.repository.ChatRepository

class NegociacaoViewModelFactory(
    private val repository: ChatRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NegociacaoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NegociacaoViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel desconhecido")
    }
}
