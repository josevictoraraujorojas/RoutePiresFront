package com.example.routepiresfront.ui.corrida

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.routepiresfront.data.model.Corrida
import com.example.routepiresfront.data.remote.ApiResponse
import com.example.routepiresfront.data.repository.CorridaRepository
import com.example.routepiresfront.ui.BaseViewModel
import kotlinx.coroutines.launch


class ListaCorridasViewModel(private val repository: CorridaRepository) : BaseViewModel() {

    private val _corridasDisponiveis = MutableLiveData<List<Corrida>>()
    val corridasDisponiveis: LiveData<List<Corrida>> = _corridasDisponiveis

    fun carregarCorridasDisponiveis() {
        viewModelScope.launch {
            _isLoading.value = true
            when (val response = repository.getCorridasDisponiveis()) {
                is ApiResponse.Success -> {
                    _corridasDisponiveis.value = response.data
                }
                is ApiResponse.Error -> {
                    _errorMessage.value = response.message
                }
                else -> {
                }
            }
            _isLoading.value = false
        }
    }
}
