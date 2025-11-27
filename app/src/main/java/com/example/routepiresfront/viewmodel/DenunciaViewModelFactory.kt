package com.example.routepiresfront.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.routepiresfront.repository.DenunciaRepository

class DenunciaViewModelFactory(
    private val repository: DenunciaRepository,
    private val usuarioLogadoId: String,
    private val usuarioDenunciadoId: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DenunciaViewModel::class.java)) {
            return DenunciaViewModel(repository, usuarioLogadoId, usuarioDenunciadoId) as T
        }
        throw IllegalArgumentException("ViewModel desconhecido")
    }
}