package com.example.routepiresfront.ui.mototaxista.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.routepiresfront.data.repository.CorridaRepository


class ViewModelFactory : ViewModelProvider.Factory {

    private val corridaRepository by lazy {
        CorridaRepository()
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CorridaMototaxistaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CorridaMototaxistaViewModel(corridaRepository) as T
        }
        throw IllegalArgumentException("Classe de ViewModel desconhecida: ${modelClass.name}")
    }
}
