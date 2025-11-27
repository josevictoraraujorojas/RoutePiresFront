package com.example.routepiresfront.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.routepiresfront.data.model.MototaxistaUpdate
import com.example.routepiresfront.data.remote.responses.MototaxistaResponse
import com.example.routepiresfront.data.repository.MototaxistaRepository
import kotlinx.coroutines.launch

class EditarPerfilViewModel(
    private val repository: MototaxistaRepository,
    private val mototaxistaId: String
) : ViewModel() {

    private val _resultadoEdicao = MutableLiveData<Result<MototaxistaResponse>>()
    val resultadoEdicao: LiveData<Result<MototaxistaResponse>> = _resultadoEdicao

    private val _mototaxista = MutableLiveData<MototaxistaResponse>()
    val mototaxista: LiveData<MototaxistaResponse> = _mototaxista

    fun carregarDados() {
        viewModelScope.launch {
            val resultado = repository.buscarPorId(mototaxistaId)
            if (resultado.isSuccess) {
                _mototaxista.postValue(resultado.getOrNull())
            }
        }
    }

    fun editarPerfil(nome: String, email: String, tel: String, senha: String) {
        val dados = MototaxistaUpdate(
            nome = nome,
            email = email,
            telefone = tel,
            senha = senha
        )

        viewModelScope.launch {
            val resultado = repository.editar(mototaxistaId, dados)
            _resultadoEdicao.postValue(resultado)
        }
    }
}