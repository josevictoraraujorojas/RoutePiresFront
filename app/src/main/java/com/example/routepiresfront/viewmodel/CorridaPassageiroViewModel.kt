package com.example.routepiresfront.ui.passageiro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.routepiresfront.data.model.CorridaPassageiroRequest
import com.example.routepiresfront.data.model.CorridaPassageiroResponse
import com.example.routepiresfront.data.model.Mototaxista
import com.example.routepiresfront.data.repository.CorridaPassageiroRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CorridaPassageiroViewModel(
    private val repository: CorridaPassageiroRepository
) : ViewModel() {

    // --- PARTE 1: CRIAR CORRIDA ---
    private val _corridaState = MutableLiveData<Result<CorridaPassageiroResponse>>()
    val corridaState: LiveData<Result<CorridaPassageiroResponse>> = _corridaState

    private val _dadosMotorista = MutableLiveData<Mototaxista?>()
    val dadosMotorista: LiveData<Mototaxista?> = _dadosMotorista

    private val _cancelamentoState = MutableLiveData<Boolean>()
    val cancelamentoState: LiveData<Boolean> = _cancelamentoState

    fun buscarDetalhesMotorista(id: String) {
        viewModelScope.launch {
            try {
                val response = repository.buscarMototaxista(id)
                if (response.isSuccessful) {
                    _dadosMotorista.value = response.body()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun cancelarCorridaAtual(id: String) {
        viewModelScope.launch {
            try {
                val response = repository.cancelarCorrida(id)
                if (response.isSuccessful) {
                    _cancelamentoState.value = true
                    monitorando = false // Para de monitorar
                } else {
                    _cancelamentoState.value = false
                }
            } catch (e: Exception) {
                _cancelamentoState.value = false
            }
        }
    }
    fun criarCorrida(request: CorridaPassageiroRequest) {
        viewModelScope.launch {
            try {
                val response = repository.criarCorrida(request)
                if (response.isSuccessful && response.body() != null) {
                    _corridaState.value = Result.success(response.body()!!)
                } else {
                    _corridaState.value = Result.failure(Exception("Erro na API: ${response.code()}"))
                }
            } catch (e: Exception) {
                _corridaState.value = Result.failure(e)
            }
        }
    }

    // --- PARTE 2: MONITORAMENTO (O que estava faltando) ---
    private val _statusCorrida = MutableLiveData<CorridaPassageiroResponse?>()
    val statusCorrida: LiveData<CorridaPassageiroResponse?> = _statusCorrida

    private var monitorando = false

    fun iniciarMonitoramento(corridaId: String) {
        if (monitorando) return // Evita iniciar duas vezes
        monitorando = true

        viewModelScope.launch {
            while (monitorando) {
                try {
                    // Chama o repositório para buscar o status atualizado
                    val response = repository.buscarCorrida(corridaId)

                    if (response.isSuccessful && response.body() != null) {
                        val corrida = response.body()!!

                        // Se o status NÃO for PENDENTE (ou seja, foi ACEITA), avisa a tela
                        if (corrida.status != "PENDENTE") {
                            _statusCorrida.value = corrida
                            monitorando = false // Para o loop pois já achou motorista
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace() // Apenas loga erro de conexão e tenta de novo
                }
                delay(5000) // Espera 5 segundos antes da próxima verificação
            }
        }
    }

    fun pararMonitoramento() {
        monitorando = false
    }
}