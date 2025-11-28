package com.example.routepiresfront.ui.comum.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.gov.ifgoiano.routepiresfront.repository.ChatRepository
import br.gov.ifgoiano.routepiresfront.repository.MensagemRepository
import com.example.routepiresfront.repository.UsuarioRepository

class NegociacaoViewModelFactory(
    private val chatRepository: ChatRepository,
    private val usuarioRepository: UsuarioRepository,
    private val mensagemRepository: MensagemRepository // ✅ novo
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NegociacaoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NegociacaoViewModel(chatRepository, usuarioRepository, mensagemRepository) as T
        }
        throw IllegalArgumentException("ViewModel desconhecido")
    }
}

