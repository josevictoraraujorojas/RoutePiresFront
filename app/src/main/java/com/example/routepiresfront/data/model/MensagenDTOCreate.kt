package br.gov.ifgoiano.routepiresfront.data.model

data class MensagenDTOCreate(
    val remetente: String,
    val destinatario: String,
    val conteudo: String,
    val chat: String
)