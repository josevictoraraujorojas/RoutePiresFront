package com.example.routepiresfront.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.routepiresfront.data.model.VeiculoUpdate
import com.example.routepiresfront.data.remote.responses.VeiculoResponse
import com.example.routepiresfront.data.repository.MototaxistaRepository
import kotlinx.coroutines.launch

class EditarVeiculoViewModel(
    private val repository: MototaxistaRepository,
    private val mototaxistaId: String
) : ViewModel() {

    private val _veiculo = MutableLiveData<VeiculoResponse?>()
    val veiculo: LiveData<VeiculoResponse?> = _veiculo

    private val _resultadoEdicao = MutableLiveData<Result<Unit>>()
    val resultadoEdicao: LiveData<Result<Unit>> = _resultadoEdicao

    fun carregarDados() {
        viewModelScope.launch {
            val resultado = repository.buscarVeiculo(mototaxistaId)
            resultado.onSuccess { dados ->
                _veiculo.postValue(dados)
            }.onFailure { erro ->
                _resultadoEdicao.postValue(Result.failure(erro))
            }
        }
    }

    fun editarVeiculo(placa: String, modelo: String, renavam: String, ano: Int) {
        val veiculo = VeiculoUpdate(
            placa = placa,
            modelo = modelo,
            renavam = renavam,
            ano = ano
        )

        viewModelScope.launch {
            val resultado = repository.atualizarVeiculo(mototaxistaId, veiculo)
            resultado.onSuccess {
                _resultadoEdicao.postValue(Result.success(Unit))
            }.onFailure { erro ->
                _resultadoEdicao.postValue(Result.failure(erro))
            }
        }
    }
}