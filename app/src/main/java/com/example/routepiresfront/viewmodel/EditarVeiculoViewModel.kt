package com.example.routepiresfront.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.routepiresfront.data.model.MototaxistaUpdate
import com.example.routepiresfront.data.model.VeiculoCreate
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
        val req = VeiculoCreate(
            placa = placa,
            modelo = modelo,
            renavam = renavam,
            ano = ano,
            fotoUrl = "https://example.com/default.jpg"
        )

        viewModelScope.launch {
            val resultado = repository.atualizarVeiculo(mototaxistaId, req)
            resultado.onSuccess {
                _resultadoEdicao.postValue(Result.success(Unit))
            }.onFailure { erro ->
                _resultadoEdicao.postValue(Result.failure(erro))
            }
        }
    }
}