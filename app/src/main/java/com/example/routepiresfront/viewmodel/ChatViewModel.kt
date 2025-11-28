package com.example.routepiresfront.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.gov.ifgoiano.routepiresfront.repository.ChatRepository
import br.gov.ifgoiano.routepiresfront.repository.MensagemRepository
import com.example.routepiresfront.data.model.MensagemDTOResponse
import br.gov.ifgoiano.routepiresfront.data.model.MensagenDTOCreate
import br.gov.ifgoiano.routepiresfront.data.model.MensagemDTOUpdate
import com.example.routepiresfront.data.model.StatusMensagem
import kotlinx.coroutines.launch

class ChatViewModel(
    private val chatRepository: ChatRepository,
    private val mensagemRepository: MensagemRepository
) : ViewModel() {

    // Usuário logado e id do chat
    private lateinit var idUsuarioLogado: String
    private lateinit var chatIdInterno: String

    // *** Accessors seguros ***
    val chatId: String get() = chatIdInterno
    val usuarioId: String get() = idUsuarioLogado

    // LiveData mensagens
    private val _mensagens = MutableLiveData<List<MensagemDTOResponse>>()
    val mensagens: LiveData<List<MensagemDTOResponse>> get() = _mensagens

    // Estado
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _erro = MutableLiveData<String?>()
    val erro: LiveData<String?> get() = _erro

    /**
     * Inicializar dados vindos por Safe Args.
     */
    fun inicializar(chatId: String, usuarioId: String) {
        this.chatIdInterno = chatId
        this.idUsuarioLogado = usuarioId

        carregarMensagens()
        marcarComoLidas()
    }

    /**
     * Carregar mensagens do chat.
     */
    fun carregarMensagens() {
        viewModelScope.launch {
            try {
                _loading.value = true

                val resp = chatRepository.getMensagensByChatId(chatId)

                if (resp.isSuccessful) {
                    val lista = resp.body().orEmpty()

                    // Ordena pela data (ordem natural)
                    _mensagens.value = lista.sortedByDescending { it.horarioEnvio }
                } else {
                    _erro.value = "Erro ao buscar mensagens (${resp.code()})"
                }
            } catch (e: Exception) {
                _erro.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    /**
     * Enviar mensagem.
     */
    fun enviarMensagem(texto: String, destinatarioId: String) {
        if (texto.isBlank()) return

        viewModelScope.launch {
            try {
                val dto = MensagenDTOCreate(
                    remetente = usuarioId,
                    destinatario = destinatarioId,
                    conteudo = texto,
                    chat = chatId
                )

                val resp = mensagemRepository.createMensagem(dto)

                if (resp.isSuccessful) {
                    carregarMensagens()
                } else {
                    _erro.value = "Erro ao enviar mensagem (${resp.code()})"
                }
            } catch (e: Exception) {
                _erro.value = e.message
            }
        }
    }

    /**
     * Marca todas mensagens destinadas ao usuário logado como lidas.
     */
    fun marcarComoLidas() {
        viewModelScope.launch {
            try {
                val resp = chatRepository.getMensagensByChatId(chatId).body().orEmpty()

                val mensagensNaoLidas = resp.filter {
                    it.destinatario == usuarioId && it.status != StatusMensagem.VISUALIZADA
                }

                mensagensNaoLidas.forEach { msg ->
                    mensagemRepository.updateMensagem(
                        msg.id!!,
                        MensagemDTOUpdate(status = StatusMensagem.VISUALIZADA)
                    )
                }
            } catch (_: Exception) { }
        }
    }

    /**
     * Conta mensagens não lidas para exibir badge.
     */
    fun contarNaoLidas(callback: (Int) -> Unit) {
        viewModelScope.launch {
            try {
                val resp = mensagemRepository.contarNaoLidas(chatId, usuarioId)
                callback(resp.body() ?: 0)
            } catch (_: Exception) {
                callback(0)
            }
        }
    }
}
