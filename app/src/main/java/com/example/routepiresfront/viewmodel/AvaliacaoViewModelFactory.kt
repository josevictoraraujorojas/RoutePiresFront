package com.example.routepiresfront.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.routepiresfront.repository.AvaliacaoRepository

class AvaliacaoViewModelFactory(
    private val repository: AvaliacaoRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AvaliacaoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AvaliacaoViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel desconhecido")
    }
}