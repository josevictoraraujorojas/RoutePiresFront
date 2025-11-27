package com.example.routepiresfront.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.routepiresfront.data.repository.MototaxistaRepository

class EditarVeiculoViewModelFactory(
    private val repository: MototaxistaRepository,
    private val mototaxistaId: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EditarVeiculoViewModel(repository, mototaxistaId) as T
    }
}