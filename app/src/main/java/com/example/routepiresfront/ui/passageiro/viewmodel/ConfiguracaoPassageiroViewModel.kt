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

    // Estado que a tela precisa enxergar de forma reativa
    private val _dadosPassageiro = MutableLiveData<Resultado<PassageiroResponse>>()
    val dadosPassageiro: LiveData<Resultado<PassageiroResponse>> = _dadosPassageiro

    private val _historico = MutableLiveData<Resultado<List<CorridaPassageiroResponse>>>()
    val historico: LiveData<Resultado<List<CorridaPassageiroResponse>>> = _historico

    // Campos expostos para o Data Binding (exibidos direto no XML)
    val nomeExibicao = MutableLiveData("Passageiro")
    val apelidoExibicao = MutableLiveData("@passageiro")
    val fotoUrl = MutableLiveData<String?>()
    val carregando = MutableLiveData(false)

    // Mensagens pontuais para a UI (toast/snackbar)
    private val _mensagem = MutableLiveData<String?>()
    val mensagem: LiveData<String?> = _mensagem

    fun carregarDados(id: String) {
        viewModelScope.launch {
            carregando.value = true
            _dadosPassageiro.value = Resultado.Carregando
            val resultado = repository.buscarPassageiro(id)
            _dadosPassageiro.value = resultado
            tratarResultadoPassageiro(resultado, origemChamada = "carregar")
            carregando.value = false
        }
    }

    fun salvarDados(id: String, body: PassageiroCadastroRequest) {
        viewModelScope.launch {
            carregando.value = true
            _dadosPassageiro.value = Resultado.Carregando
            val resultado = repository.atualizarPassageiro(id, body)
            _dadosPassageiro.value = resultado
            tratarResultadoPassageiro(resultado, origemChamada = "atualizar")
            carregando.value = false
        }
    }

    fun carregarHistorico(id: String) {
        viewModelScope.launch {
            _historico.value = Resultado.Carregando
            _historico.value = repository.historicoCorridas(id)
        }
    }

    /**
     * Centraliza o tratamento de sucesso/erro para reaproveitar com carregarDados e salvarDados.
     */
    private fun tratarResultadoPassageiro(
        resultado: Resultado<PassageiroResponse>,
        origemChamada: String
    ) {
        when (resultado) {
            is Resultado.Sucesso -> {
                val dados = resultado.dado
                nomeExibicao.value = dados.nome ?: "Passageiro"
                // Se o backend não devolver "apelido", geramos um handle simples em minúsculo
                apelidoExibicao.value = "@${(dados.nome ?: "passageiro").lowercase()}"
                fotoUrl.value = dados.fotoUrl
                _mensagem.value = when (origemChamada) {
                    "atualizar" -> "Dados atualizados com sucesso"
                    else -> null
                }
            }

            is Resultado.Erro -> {
                _mensagem.value = resultado.mensagem
            }

            Resultado.Carregando -> {
                // Estado intermediário já tratado por carregando LiveData
            }
        }
    }

    fun limparMensagem() {
        _mensagem.value = null
    }
}
