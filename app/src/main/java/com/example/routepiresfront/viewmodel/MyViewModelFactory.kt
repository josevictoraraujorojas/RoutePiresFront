package com.example.routepiresfront.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.routepiresfront.data.repository.MototaxistaRepository

class MyViewModelFactory(private val repo: MototaxistaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CadastroMototaxistaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CadastroMototaxistaViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}