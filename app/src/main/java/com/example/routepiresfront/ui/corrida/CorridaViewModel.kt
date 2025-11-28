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

/**
 * ViewModel compartilhado para gerenciar o estado de uma única corrida durante todo o seu ciclo de vida.
 */
class CorridaViewModel(private val repository: CorridaRepository) : BaseViewModel() {

    // Armazena os detalhes da corrida atualmente selecionada ou em andamento.
    private val _corridaAtual = MutableLiveData<Corrida?>()
    val corridaAtual: LiveData<Corrida?> = _corridaAtual

    // Eventos de navegação para coordenar o fluxo entre os fragmentos.
    val navegarParaAguardandoInicio = SingleLiveEvent<Void>()
    val navegarParaCorridaEmAndamento = SingleLiveEvent<Void>()
    val navegarParaTelaDeAvaliacao = SingleLiveEvent<Void>()
    val fecharFluxoCorrida = SingleLiveEvent<Void>()

    /**
     * Define a corrida que foi selecionada da lista.
     */
    fun selecionarCorrida(corrida: Corrida) {
        _corridaAtual.value = corrida
    }

    /**
     * Ação de aceitar a corrida.
     * Chama o repositório e, em caso de sucesso, dispara o evento para navegar para a próxima tela.
     */
    fun aceitarCorrida(mototaxistaId: Long) {
        val corridaId = _corridaAtual.value?.id?.toString() ?: return

        viewModelScope.launch {
            _isLoading.value = true
            when (val response = repository.aceitarCorrida(corridaId, mototaxistaId)) {
                is ApiResponse.Success -> {
                    // Atualiza o status local da corrida e navega
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

    /**
     * Ação de iniciar a corrida.
     */
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

    /**
     * Ação de finalizar a corrida.
     */
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

    /**
     * Ação de cancelar a corrida.
     */
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
