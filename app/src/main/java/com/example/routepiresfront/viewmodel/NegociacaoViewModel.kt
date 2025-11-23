package com.example.routepiresfront.ui.comum.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.gov.ifgoiano.routepiresfront.repository.ChatRepository
import com.example.routepiresfront.data.model.Negociacao
import kotlinx.coroutines.launch

class NegociacaoViewModel(
    private val repository: ChatRepository
) : ViewModel() {

    private val _negociacoes = MutableLiveData<List<Negociacao>>(emptyList())
    val negociacoes: LiveData<List<Negociacao>> = _negociacoes

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _erro = MutableLiveData<String?>()
    val erro: LiveData<String?> = _erro

    // copia para filtro local
    private var negociacoesOriginais: List<Negociacao> = emptyList()

    /**
     * Carrega chats do backend via ChatRepository e converte para model Negociacao.
     */
    fun carregarNegociacoes() {
        viewModelScope.launch {
            _loading.value = true
            _erro.value = null

            try {
                val response = repository.getAllChats()
                if (response.isSuccessful) {
                    val chats = response.body().orEmpty()

                    // Mapeia ChatDTOResponse -> Negociacao
                    val lista = chats.map { chat ->
                        // Escolhe um nome representativo: primeiro participante ou "Desconhecido"
                        val nome = chat.participantes?.firstOrNull() ?: "Desconhecido"

                        // pega última mensagem textual se existir (ChatDTOResponse.mensagens é List<String> no seu model)
                        val ultimaMensagem = chat.mensagens?.lastOrNull() ?: ""

                        // quantidade de não lidas: não disponível no DTO, usa 0 (ou adapte se tiver)
                        val naoLidas = 0

                        Negociacao(
                            nome = nome,
                            mensagem = ultimaMensagem,
                            quantidadeNaoLida = naoLidas
                        )
                    }

                    negociacoesOriginais = lista
                    _negociacoes.postValue(lista)
                } else {
                    _erro.postValue("Erro ao carregar negociações (código ${response.code()})")
                }
            } catch (e: Exception) {
                _erro.postValue(e.message ?: "Erro desconhecido")
            } finally {
                _loading.postValue(false)
            }
        }
    }

    /**
     * Filtra localmente pela query (nome ou mensagem)
     */
    fun filtrar(query: String) {
        val q = query.trim()
        if (q.isEmpty()) {
            _negociacoes.value = negociacoesOriginais
            return
        }

        val filtrado = negociacoesOriginais.filter {
            it.nome.contains(q, ignoreCase = true) ||
                    it.mensagem.contains(q, ignoreCase = true)
        }
        _negociacoes.value = filtrado
    }
}
