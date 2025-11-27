package com.example.routepiresfront.ui.passageiro.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.routepiresfront.core.Resultado
import com.example.routepiresfront.data.model.passageiro.CorridaPassageiroRequest
import com.example.routepiresfront.data.model.passageiro.CorridaPassageiroResponse
import com.example.routepiresfront.data.model.passageiro.Localizacao
import com.example.routepiresfront.data.repository.passageiro.PassageiroRepository
import java.util.Date
import kotlinx.coroutines.launch

/**
 * ViewModel para seleção de origem/destino e criação da corrida.
 */
class SelecaoLocalViewModel(
    private val repository: PassageiroRepository = PassageiroRepository()
) : ViewModel() {

    private val _origem = MutableLiveData<Localizacao?>()
    val origem: LiveData<Localizacao?> = _origem

    private val _destino = MutableLiveData<Localizacao?>()
    val destino: LiveData<Localizacao?> = _destino

    private val _paradas = MutableLiveData<List<Localizacao>>(emptyList())
    val paradas: LiveData<List<Localizacao>> = _paradas

    private val _resultadoSolicitacao = MutableLiveData<Resultado<CorridaPassageiroResponse>?>()
    val resultadoSolicitacao: LiveData<Resultado<CorridaPassageiroResponse>?> = _resultadoSolicitacao

    val carregando = MutableLiveData(false)

    private val _mensagem = MutableLiveData<String?>()
    val mensagem: LiveData<String?> = _mensagem

    fun definirOrigem(localizacao: Localizacao) {
        _origem.value = localizacao
    }

    fun definirDestino(localizacao: Localizacao) {
        _destino.value = localizacao
    }

    fun adicionarParada(localizacao: Localizacao) {
        val atual = _paradas.value ?: emptyList()
        _paradas.value = atual + localizacao
    }

    fun limparParadas() {
        _paradas.value = emptyList()
    }

    /**
     * Monta o DTO esperado pelo backend e dispara a criação da corrida.
     */
    fun solicitarCorrida(passageiroId: String, mototaxistaId: String? = null, status: String = "SOLICITADA") {
        val origemSelecionada = _origem.value
        val destinoSelecionado = _destino.value

        // Não dispara se faltarem pontos básicos
        if (origemSelecionada == null || destinoSelecionado == null) {
            _mensagem.value = "Defina origem e destino antes de continuar"
            return
        }

        val dto = CorridaPassageiroRequest(
            passageiroId = passageiroId,
            mototaxistaId = mototaxistaId,
            origem = origemSelecionada,
            destino = destinoSelecionado,
            paradasIds = _paradas.value,
            dataHoraSolicitacao = Date(),
            status = status
        )

        viewModelScope.launch {
            carregando.value = true
            _resultadoSolicitacao.value = Resultado.Carregando
            val resultado = repository.solicitarCorrida(dto)
            _resultadoSolicitacao.value = resultado
            _mensagem.value = when (resultado) {
                is Resultado.Sucesso -> "Corrida solicitada com sucesso!"
                is Resultado.Erro -> resultado.mensagem
                Resultado.Carregando -> null
            }
            carregando.value = false
        }
    }

    fun limparMensagem() {
        _mensagem.value = null
    }

    fun limparResultado() {
        _resultadoSolicitacao.value = null
    }
}
