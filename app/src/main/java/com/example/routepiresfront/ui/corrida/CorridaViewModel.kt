package com.example.routepiresfront.ui.corrida

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.routepiresfront.data.model.Corrida
import com.example.routepiresfront.data.model.StatusCorrida
import com.example.routepiresfront.data.remote.ApiResponse
import com.example.routepiresfront.data.repository.CorridaRepository
import com.example.routepiresfront.ui.BaseViewModel
import com.example.routepiresfront.util.SingleLiveEvent
import kotlinx.coroutines.launch


class CorridaViewModel(private val repository: CorridaRepository) : BaseViewModel() {

    // Armazena os detalhes da corrida atualmente selecionada ou em andamento.
    private val _corridaAtual = MutableLiveData<Corrida?>()
    val corridaAtual: LiveData<Corrida?> = _corridaAtual

    val navegarParaAguardandoInicio = SingleLiveEvent<Void>()
    val navegarParaCorridaEmAndamento = SingleLiveEvent<Void>()
    val navegarParaTelaDeAvaliacao = SingleLiveEvent<Void>()
    val fecharFluxoCorrida = SingleLiveEvent<Void>()


    fun selecionarCorrida(corrida: Corrida) {
        _corridaAtual.value = corrida
    }

    fun aceitarCorrida(mototaxistaId: String) {
        val corridaId = _corridaAtual.value?.id?.toString() ?: return

        viewModelScope.launch {
            _isLoading.value = true
            when (val response = repository.aceitarCorrida(corridaId, mototaxistaId)) {
                is ApiResponse.Success -> {
                    _corridaAtual.value = _corridaAtual.value?.copy(status = StatusCorrida.ACEITA)
                    navegarParaAguardandoInicio.call()
                }
                is ApiResponse.Error -> {
                    _errorMessage.value = response.message
                }
                else -> {}
            }
            _isLoading.value = false
        }
    }


    fun iniciarCorrida() {
        val corridaId = _corridaAtual.value?.id?.toString() ?: return

        viewModelScope.launch {
            _isLoading.value = true
            when (val response = repository.iniciarCorrida(corridaId)) {
                is ApiResponse.Success -> {
                    _corridaAtual.value = _corridaAtual.value?.copy(status = StatusCorrida.EM_ANDAMENTO)
                    navegarParaCorridaEmAndamento.call()
                }
                is ApiResponse.Error -> {
                    _errorMessage.value = response.message
                }
                else -> {}
            }
            _isLoading.value = false
        }
    }


    fun finalizarCorrida() {
        val corridaId = _corridaAtual.value?.id?.toString() ?: return

        viewModelScope.launch {
            _isLoading.value = true
            when (val response = repository.finalizarCorrida(corridaId)) {
                is ApiResponse.Success -> {
                    _corridaAtual.value = _corridaAtual.value?.copy(status = StatusCorrida.FINALIZADA)
                    navegarParaTelaDeAvaliacao.call()
                }
                is ApiResponse.Error -> {
                    _errorMessage.value = response.message
                }
                else -> {}
            }
            _isLoading.value = false
        }
    }


    fun cancelarCorrida(motivo: String? = null) {
        val corridaId = _corridaAtual.value?.id?.toString() ?: return

        viewModelScope.launch {
            _isLoading.value = true
            when (val response = repository.cancelarCorrida(corridaId, motivo)) {
                is ApiResponse.Success -> {
                    _corridaAtual.value = null // Limpa a corrida atual
                    fecharFluxoCorrida.call() // Fecha o fluxo e volta para a lista
                }
                is ApiResponse.Error -> {
                    _errorMessage.value = response.message
                }
                else -> {}
            }
            _isLoading.value = false
        }
    }
}
