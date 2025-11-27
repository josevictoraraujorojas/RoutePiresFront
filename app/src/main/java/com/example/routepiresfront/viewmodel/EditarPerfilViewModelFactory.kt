package com.example.routepiresfront.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.routepiresfront.data.repository.MototaxistaRepository

class EditarPerfilViewModelFactory(
    private val repository: MototaxistaRepository,
    private val mototaxistaId: String
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditarPerfilViewModel::class.java)) {
            return EditarPerfilViewModel(repository, mototaxistaId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}