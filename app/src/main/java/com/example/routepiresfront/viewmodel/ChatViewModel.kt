package com.example.routepiresfront.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.gov.ifgoiano.routepiresfront.data.model.MensagenDTOCreate
import br.gov.ifgoiano.routepiresfront.repository.ChatRepository
import br.gov.ifgoiano.routepiresfront.repository.MensagemRepository
import com.example.routepiresfront.data.model.Mensagem
import com.example.routepiresfront.data.model.Negociacao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class ChatViewModel(
    private val chatRepository: ChatRepository,
    private val mensagemRepository: MensagemRepository
) : ViewModel() {

    private var negociacao: Negociacao? = null

    private val _mensagens = MutableStateFlow<List<Mensagem>>(emptyList())
    val mensagens: StateFlow<List<Mensagem>> get() = _mensagens

    val mensagemDigitada = MutableStateFlow("")

    private val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())

    fun iniciarChat(negociacao: Negociacao) {
        this.negociacao = negociacao
        carregarMensagens()
    }

    private fun carregarMensagens() {
        val chatId = negociacao?.chat?.id ?: return

        viewModelScope.launch {
            try {
                val responseChat = chatRepository.getChatById(chatId)

                if (!responseChat.isSuccessful) return@launch

                val chat = responseChat.body()
                if (chat == null || chat.mensagens.isNullOrEmpty()) {
                    _mensagens.value = emptyList()
                    return@launch
                }

                val listaFinal = mutableListOf<Mensagem>()

                // PARA CADA ID → BUSCAR NO FIREBASE VIA API
                for (idMsg in chat.mensagens!!) {

                    val respMsg = mensagemRepository.getMensagemById(idMsg)

                    if (respMsg.isSuccessful) {
                        val dto = respMsg.body() ?: continue

                        val tipo = if (dto.remetente == negociacao?.usarioLogado)
                            Mensagem.TIPO_ENVIADA
                        else
                            Mensagem.TIPO_RECEBIDA

                        listaFinal += Mensagem(
                            texto = dto.conteudo ?: "",
                            hora = dto.horarioEnvio?.let { sdf.format(it) } ?: "--:--",
                            tipo = tipo
                        )
                    }
                }

                // Atualiza a UI
                _mensagens.value = listaFinal.reversed() // para aparecer do mais novo pro mais antigo

            } catch (_: Exception) {}
        }
    }

    fun enviarMensagem() {
        val texto = mensagemDigitada.value.trim()
        val negociacaoAtual = negociacao ?: return
        val chatId = negociacaoAtual.chat.id ?: return
        if (texto.isEmpty()) return

        viewModelScope.launch {
            try {

                val dto = MensagenDTOCreate(
                    remetente = negociacaoAtual.usarioLogado,
                    destinatario = negociacaoAtual.chat.participantes?.firstOrNull { it != negociacaoAtual.usarioLogado }
                        ?: "",
                    conteudo = texto,
                    chat = chatId
                )

                val response = mensagemRepository.createMensagem(dto)

                if (response.isSuccessful) {

                    val novaMsg = Mensagem(
                        texto = texto,
                        hora = sdf.format(System.currentTimeMillis()),
                        tipo = Mensagem.TIPO_ENVIADA
                    )

                    // Adiciona imediatamente na UI
                    _mensagens.value = listOf(novaMsg) + _mensagens.value

                    mensagemDigitada.value = ""
                }
            } catch (_: Exception) { }
        }
    }
}
