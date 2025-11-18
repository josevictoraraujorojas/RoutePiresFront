package com.example.routepiresfront.data.model

import java.util.Date

data class MensagemDTOResponse(
    val id: String?,
    val remetente: String?,
    val destinatario: String?,
    val conteudo: String?,
    val horarioEnvio: Date?,
    val status: StatusMensagem?,
    val chat: String?
)
