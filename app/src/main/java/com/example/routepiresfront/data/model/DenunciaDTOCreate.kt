package br.gov.ifgoiano.routepiresfront.data.model

data class DenunciaDTOCreate(
    val motivo: String,
    val denuncianteId: String,
    val denunciadoId: String,
    val descricao: String
)
