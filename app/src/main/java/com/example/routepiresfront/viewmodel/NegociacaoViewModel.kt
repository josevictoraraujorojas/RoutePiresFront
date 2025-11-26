package com.example.routepiresfront.ui.comum.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.gov.ifgoiano.routepiresfront.repository.ChatRepository
import br.gov.ifgoiano.routepiresfront.repository.MensagemRepository
import com.example.routepiresfront.data.model.Negociacao
import com.example.routepiresfront.repository.UsuarioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NegociacaoViewModel(
    private val chatRepository: ChatRepository,
    private val usuarioRepository: UsuarioRepository,
    private val mensagemRepository: MensagemRepository
) : ViewModel() {

    private val _negociacoes = MutableLiveData<List<Negociacao>>(emptyList())
    val negociacoes: LiveData<List<Negociacao>> = _negociacoes

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _erro = MutableLiveData<String?>()
    val erro: LiveData<String?> = _erro

    // Cópia para filtro local
    private var negociacoesOriginais: List<Negociacao> = emptyList()

    /**
     * Carrega chats do backend via ChatRepository e converte para model Negociacao.
     * Busca a última mensagem real via MensagemRepository.
     * Conta mensagens não lidas por chat.
     */
    fun carregarNegociacoes() {
        viewModelScope.launch {
            _loading.value = true
            _erro.value = null

            val userId = "cUKwPBdlmMt95OJuMleA" // ID do usuário logado

            try {
                val response = chatRepository.getChatsByUsuarioId(userId)

                if (response.isSuccessful) {
                    val chats = response.body().orEmpty()
                    val lista = mutableListOf<Negociacao>()

                    for (chat in chats) {

                        val outroId = chat.participantes?.firstOrNull { it != userId } ?: "Desconhecido"
                        val nomeReal = usuarioRepository.getNomeById(outroId) ?: outroId

                        // 🔥 último ID da lista
                        val ultimaMensagemId = chat.mensagens?.lastOrNull()

                        // 🔥 buscar o conteúdo real da última mensagem
                        val ultimaMensagem = if (ultimaMensagemId != null) {
                            withContext(Dispatchers.IO) {
                                val msgResponse = mensagemRepository.getMensagemById(ultimaMensagemId)
                                if (msgResponse.isSuccessful) msgResponse.body()?.conteudo ?: ""
                                else ""
                            }
                        } else ""

                        // 🔥 contar mensagens não lidas
                        val quantidadeNaoLida = withContext(Dispatchers.IO) {
                            val countResponse = mensagemRepository.contarNaoLidas(chat.id, userId)
                            if (countResponse.isSuccessful) countResponse.body() ?: 0 else 0
                        }

                        lista.add(
                            Negociacao(
                                nome = nomeReal,
                                mensagem = ultimaMensagem,
                                quantidadeNaoLida = quantidadeNaoLida,
                                chat = chat,
                                usarioLogado = userId
                            )
                        )
                    }

                    negociacoesOriginais = lista
                    _negociacoes.postValue(lista)

                } else if (response.code() == 404) {
                    negociacoesOriginais = emptyList()
                    _negociacoes.postValue(emptyList())
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
