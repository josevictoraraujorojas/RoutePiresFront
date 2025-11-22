package br.gov.ifgoiano.routepiresfront.data.model

import com.example.routepiresfront.data.model.StatusMensagem

data class MensagemDTOUpdate(
    val conteudo: String? = null,
    val status: StatusMensagem? = null
)
