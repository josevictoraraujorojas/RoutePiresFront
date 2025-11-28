package com.example.routepiresfront.ui.mototaxista.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.routepiresfront.data.model.Corrida
import com.example.routepiresfront.data.model.Localizacao
import com.example.routepiresfront.data.remote.ApiResponse
import com.example.routepiresfront.data.remote.RetrofitClient
import com.example.routepiresfront.data.repository.CorridaRepository
import com.example.routepiresfront.util.SingleLiveEvent
import kotlinx.coroutines.launch

class CorridaMototaxistaViewModel(
    private val repository: CorridaRepository
) : ViewModel() {

    // Construtor sem parâmetros para compatibilidade (cria repository internamente)
    // TODO: Remover quando implementar injeção de dependências (Hilt/Koin)
    constructor() : this(CorridaRepository())

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _corridasDisponiveis = MutableLiveData<List<Corrida>>()
    val corridasDisponiveis: LiveData<List<Corrida>> = _corridasDisponiveis

    private val _corridaAtual = MutableLiveData<Corrida?>()
    val corridaAtual: LiveData<Corrida?> = _corridaAtual

    val navegarParaNegociacao = SingleLiveEvent<Void>()
    val navegarParaAguardandoInicio = SingleLiveEvent<Void>()
    val navegarParaAndamento = SingleLiveEvent<Void>()
    val navegarParaAvaliacao = SingleLiveEvent<Void>()
    val fecharFluxoCorrida = SingleLiveEvent<Void>()

    private var mototaxistaId: String = "1" // TODO: Obter de um sistema de sessão/autenticação

    fun carregarCorridasDisponiveis() {
        viewModelScope.launch {
            _isLoading.value = true
            when (val response = repository.getCorridasDisponiveis()) {
                is ApiResponse.Success -> _corridasDisponiveis.value = response.data
                is ApiResponse.Error -> _errorMessage.value = response.message
                else -> {}
            }
            _isLoading.value = false
        }
    }

    fun selecionarCorrida(corrida: Corrida) {
        _corridaAtual.value = corrida
    }

    fun aceitarCorrida() {
        val corridaId = _corridaAtual.value?.id?.toString() ?: return
        viewModelScope.launch {
            _isLoading.value = true
            when (val response = repository.aceitarCorrida(corridaId, mototaxistaId)) {
                is ApiResponse.Success -> {
                    _corridaAtual.value = response.data
                    navegarParaNegociacao.call()
                }

                is ApiResponse.Error -> _errorMessage.value = response.message
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
                    _corridaAtual.value = response.data
                    navegarParaAndamento.call()
                }

                is ApiResponse.Error -> _errorMessage.value = response.message
                else -> {}
            }
            _isLoading.value = false
        }
    }

    // TODO: Implementar atualização de localização via WebSocket ou endpoint específico
    fun atualizarLocalizacao(latitude: Double, longitude: Double) {
        // Comentado até que o backend forneça endpoint adequado
        // val corridaId = _corridaAtual.value?.id?.toString() ?: return
        // val localizacao = Localizacao(latitude, longitude)
        // viewModelScope.launch {
        //     repository.atualizarLocalizacao(corridaId, localizacao)
        // }
    }

    fun finalizarCorrida() {
        val corridaId = _corridaAtual.value?.id?.toString() ?: return
        viewModelScope.launch {
            _isLoading.value = true
            when (val response = repository.finalizarCorrida(corridaId)) {
                is ApiResponse.Success -> {
                    _corridaAtual.value = response.data
                    navegarParaAvaliacao.call()
                }

                is ApiResponse.Error -> _errorMessage.value = response.message
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
                    _corridaAtual.value = null
                    fecharFluxoCorrida.call()
                }

                is ApiResponse.Error -> _errorMessage.value = response.message
                else -> {}
            }
            _isLoading.value = false
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }

    fun limparErro() {
        _errorMessage.value = null
    }

    fun resetarNavegacao() {
        // Método para resetar flags de navegação se necessário
        // Por enquanto não há ação específica, pois SingleLiveEvent já gerencia isso
    }
    
    /**
     * Chamado após negociação bem-sucedida para navegar para tela de aguardar início
     */
    fun confirmarNegociacaoEAguardarInicio() {
        navegarParaAguardandoInicio.call()
    }
    
    /**
     * Verifica se a corrida atual é uma entrega
     */
    fun isEntrega(): Boolean {
        return _corridaAtual.value?.tipo == "entrega"
    }
    
    /**
     * Verifica se a corrida atual é uma corrida normal
     */
    fun isCorrida(): Boolean {
        return _corridaAtual.value?.tipo == "corrida"
    }

    fun setMototaxistaId(id: String) {
        mototaxistaId = id
    }
}
