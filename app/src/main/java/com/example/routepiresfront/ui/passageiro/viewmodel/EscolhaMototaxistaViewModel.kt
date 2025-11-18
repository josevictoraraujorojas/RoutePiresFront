package com.example.routepiresfront.ui.passageiro.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.routepiresfront.core.Resultado
import com.example.routepiresfront.data.model.passageiro.MototaxistaPerfil
import com.example.routepiresfront.data.model.passageiro.MototaxistaResumo
import com.example.routepiresfront.data.repository.passageiro.PassageiroRepository
import kotlinx.coroutines.launch

/**
 * ViewModel para a tela/listagem de escolha de mototaxista.
 */
class EscolhaMototaxistaViewModel(
    private val repository: PassageiroRepository = PassageiroRepository()
) : ViewModel() {

    private val _mototaxistas = MutableLiveData<Resultado<List<MototaxistaResumo>>>()
    val mototaxistas: LiveData<Resultado<List<MototaxistaResumo>>> = _mototaxistas

    private val _perfilMototaxista = MutableLiveData<Resultado<MototaxistaPerfil>>()
    val perfilMototaxista: LiveData<Resultado<MototaxistaPerfil>> = _perfilMototaxista

    fun carregarMototaxistas() {
        viewModelScope.launch {
            _mototaxistas.value = Resultado.Carregando
            _mototaxistas.value = repository.listarMototaxistas()
        }
    }

    fun carregarPerfil(id: String) {
        viewModelScope.launch {
            _perfilMototaxista.value = Resultado.Carregando
            _perfilMototaxista.value = repository.perfilMototaxista(id)
        }
    }
}
