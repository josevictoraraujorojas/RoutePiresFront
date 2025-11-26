package com.example.routepiresfront.ui.mototaxista.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.routepiresfront.data.remote.RetrofitClient
import com.example.routepiresfront.data.repository.CorridaRepository

/**
 * Factory para criar instâncias de ViewModels que possuem dependências.
 * Isso permite a injeção de dependências nos ViewModels, como o CorridaRepository.
 */
class ViewModelFactory : ViewModelProvider.Factory {

    // Cria uma única instância do repositório para ser compartilhada
    private val corridaRepository by lazy {
        CorridaRepository(RetrofitClient.apiService)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CorridaMototaxistaViewModel::class.java)) {
            // Se a classe solicitada for CorridaMototaxistaViewModel, cria uma instância com o repositório
            @Suppress("UNCHECKED_CAST")
            return CorridaMototaxistaViewModel(corridaRepository) as T
        }
        // Lança um erro se a factory não souber como criar o ViewModel solicitado
        throw IllegalArgumentException("Classe de ViewModel desconhecida: ${modelClass.name}")
    }
}
