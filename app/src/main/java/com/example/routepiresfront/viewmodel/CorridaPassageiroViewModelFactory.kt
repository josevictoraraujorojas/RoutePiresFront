package com.example.routepiresfront.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.routepiresfront.data.repository.CorridaPassageiroRepository
import com.example.routepiresfront.ui.passageiro.CorridaPassageiroViewModel

class CorridaPassageiroViewModelFactory(
    private val repository: CorridaPassageiroRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CorridaPassageiroViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CorridaPassageiroViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}