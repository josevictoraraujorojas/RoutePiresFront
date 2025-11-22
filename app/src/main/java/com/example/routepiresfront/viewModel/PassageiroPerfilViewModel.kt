package com.example.routepiresfront.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.routepiresfront.data.model.*
import com.example.routepiresfront.repository.PassageiroPerfilRepository
import kotlinx.coroutines.launch

class PassageiroPerfilViewModel : ViewModel() {

    private val repo = PassageiroPerfilRepository()

    private val _perfil = MutableLiveData<PassageiroResponseDTO>()
    val perfil: LiveData<PassageiroResponseDTO> = _perfil

    private val _historico = MutableLiveData<List<CorridaDTOResponse>>()
    val historico: LiveData<List<CorridaDTOResponse>> = _historico

    private val _notificacoes = MutableLiveData<List<NotificacaoDTOResponse>>()
    val notificacoes: LiveData<List<NotificacaoDTOResponse>> = _notificacoes

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _updateStatus = MutableLiveData<Boolean?>()
    val updateStatus: LiveData<Boolean?> = _updateStatus

    fun carregarPerfil(id: String) {
        viewModelScope.launch {
            val result = repo.getPerfilPassageiro(id)
            result.fold(
                onSuccess = { _perfil.value = it; _error.value = null },
                onFailure = { _error.value = it.message }
            )
        }
    }

    fun atualizarPerfil(id: String, update: PassageiroUpdateDTO) {
        viewModelScope.launch {
            val result = repo.updatePerfil(id, update)
            result.fold(
                onSuccess = { response ->
                    when (response) {
                        is PassageiroResponseDTO -> {
                            _perfil.value = response
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
                carregarPerfil(id)
            } catch (_: Exception) {}
        }
    }

    fun carregarHistorico(id: String) {
        viewModelScope.launch {
            val result = repo.getHistoricoCorridas(id)
            result.fold(
                onSuccess = { list -> _historico.value = list; _error.value = null },
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

    fun resetUpdateStatus() { _updateStatus.value = null }
}
