package com.example.routepiresfront.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.routepiresfront.data.model.*
import com.example.routepiresfront.repository.MototaxistaPerfilRepository
import kotlinx.coroutines.launch

class MototaxistaPerfilViewModel : ViewModel() {

    private val repo = MototaxistaPerfilRepository()

    private val _perfil = MutableLiveData<MototaxistaDTOResponse>()
    val perfil: LiveData<MototaxistaDTOResponse> = _perfil

    private val _veiculo = MutableLiveData<VeiculoDTOResponse>()
    val veiculo: LiveData<VeiculoDTOResponse> = _veiculo

    private val _historico = MutableLiveData<List<CorridaDTOResponse>>()
    val historico: LiveData<List<CorridaDTOResponse>> = _historico

    private val _notificacoes = MutableLiveData<List<NotificacaoDTOResponse>>()
    val notificacoes: LiveData<List<NotificacaoDTOResponse>> = _notificacoes

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    // LiveData para indicar resultado da última operação de update (true=sucesso, false=falha, null=neutro)
    private val _updateStatus = MutableLiveData<Boolean?>()
    val updateStatus: LiveData<Boolean?> = _updateStatus

    fun carregarPerfil(id: String) {
        viewModelScope.launch {
            val result = repo.getPerfilMototaxista(id)
            result.fold(
                onSuccess = { _perfil.value = it; _error.value = null },
                onFailure = { _error.value = it.message }
            )
        }
    }

    fun atualizarPerfil(id: String, update: MototaxistaDTOUpdate) {
        viewModelScope.launch {
            val result = repo.updatePerfil(id, update)
            result.fold(
                onSuccess = { response ->
                    // Evita ClassCastException se o repository retornar Unit (204 No Content)
                    when (response) {
                        is MototaxistaDTOResponse -> {
                            _perfil.value = response
                            _error.value = null
                        }
                        else -> {
                            // resposta vazia (Unit) ou formato inesperado; vamos só limpar erro
                            _error.value = null
                        }
                    }
                    // Marca operação como sucesso
                    _updateStatus.value = true
                },
                onFailure = { _error.value = it.message; _updateStatus.value = false }
            )
            // Sempre tenta sincronizar com o servidor após a tentativa de atualização
            try {
                carregarPerfil(id)
            } catch (_: Exception) {
            }
        }
    }

    fun carregarVeiculo(id: String) {
        viewModelScope.launch {
            val result = repo.getVeiculo(id)
            result.fold(
                onSuccess = { _veiculo.value = it; _error.value = null },
                onFailure = { _error.value = it.message }
            )
        }
    }

    fun atualizarVeiculo(id: String, update: VeiculoDTOUpdate) {
        viewModelScope.launch {
            val result = repo.updateVeiculo(id, update)
            result.fold(
                onSuccess = { response ->
                    when (response) {
                        is VeiculoDTOResponse -> {
                            _veiculo.value = response
                            _error.value = null
                        }
                        else -> {
                            _error.value = null
                        }
                    }
                    _updateStatus.value = true
                },
                onFailure = { _error.value = it.message; _updateStatus.value = false }
            )
            try {
                carregarVeiculo(id)
            } catch (_: Exception) {
            }
        }
    }

    fun carregarHistorico(id: String) {
        viewModelScope.launch {
            val result = repo.getHistoricoCorridas(id)
            result.fold(
                onSuccess = { _historico.value = it; _error.value = null },
                onFailure = { _error.value = it.message }
            )
        }
    }

    fun carregarNotificacoes(id: String) {
        viewModelScope.launch {
            val result = repo.getNotificacoes(id)
            result.fold(
                onSuccess = { _notificacoes.value = it; _error.value = null },
                onFailure = { _error.value = it.message }
            )
        }
    }

    fun logout(id: String, onLogoutComplete: () -> Unit) {
        viewModelScope.launch {
            val result = repo.logout(id)
            result.fold(
                onSuccess = { onLogoutComplete() },
                onFailure = { _error.value = it.message }
            )
        }
    }

    // Reseta o status de atualização para o estado neutro (null)
    fun resetUpdateStatus() {
        _updateStatus.value = null
    }
}
