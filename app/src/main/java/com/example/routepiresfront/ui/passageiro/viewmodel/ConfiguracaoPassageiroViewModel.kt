package com.example.routepiresfront.ui.passageiro.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.routepiresfront.core.Resultado
import com.example.routepiresfront.data.model.passageiro.CorridaPassageiroResponse
import com.example.routepiresfront.data.model.passageiro.PassageiroCadastroRequest
import com.example.routepiresfront.data.model.passageiro.PassageiroResponse
import com.example.routepiresfront.data.repository.passageiro.PassageiroRepository
import kotlinx.coroutines.launch

/**
 * ViewModel para a tela de configurações do passageiro.
 * Responsável por carregar/salvar dados e histórico.
 */
class ConfiguracaoPassageiroViewModel(
    private val repository: PassageiroRepository = PassageiroRepository()
) : ViewModel() {

    private val _dadosPassageiro = MutableLiveData<Resultado<PassageiroResponse>>()
    val dadosPassageiro: LiveData<Resultado<PassageiroResponse>> = _dadosPassageiro

    private val _historico = MutableLiveData<Resultado<List<CorridaPassageiroResponse>>>()
    val historico: LiveData<Resultado<List<CorridaPassageiroResponse>>> = _historico

    fun carregarDados(id: String) {
        viewModelScope.launch {
            _dadosPassageiro.value = Resultado.Carregando
            _dadosPassageiro.value = repository.buscarPassageiro(id)
        }
    }

    fun salvarDados(id: String, body: PassageiroCadastroRequest) {
        viewModelScope.launch {
            _dadosPassageiro.value = Resultado.Carregando
            _dadosPassageiro.value = repository.atualizarPassageiro(id, body)
        }
    }

    fun carregarHistorico(id: String) {
        viewModelScope.launch {
            _historico.value = Resultado.Carregando
            _historico.value = repository.historicoCorridas(id)
        }
    }
}
