package com.example.routepiresfront.ui.corrida

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.routepiresfront.data.model.Corrida
import com.example.routepiresfront.data.remote.ApiResponse
import com.example.routepiresfront.data.repository.CorridaRepository
import com.example.routepiresfront.ui.BaseViewModel
import kotlinx.coroutines.launch

/**
 * ViewModel para a tela que lista as corridas disponíveis.
 */
class ListaCorridasViewModel(private val repository: CorridaRepository) : BaseViewModel() {

    private val _corridasDisponiveis = MutableLiveData<List<Corrida>>()
    val corridasDisponiveis: LiveData<List<Corrida>> = _corridasDisponiveis

    /**
     * Busca as corridas disponíveis na API e atualiza os LiveData de acordo com a resposta.
     */
    fun carregarCorridasDisponiveis(mototaxistaId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            when (val response = repository.getCorridasDisponiveis(mototaxistaId)) {
                is ApiResponse.Success -> {
                    _corridasDisponiveis.value = response.data
                }
                is ApiResponse.Error -> {
                    _errorMessage.value = response.message
                }
                else -> {
                    // O estado Loading é tratado pela flag _isLoading
                }
            }
            _isLoading.value = false
        }
    }
}
