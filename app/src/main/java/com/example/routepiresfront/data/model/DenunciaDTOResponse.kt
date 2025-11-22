package br.gov.ifgoiano.routepiresfront.data.model

import com.example.routepiresfront.data.model.StatusDenuncia
import java.util.Date

data class DenunciaDTOResponse(
    val id: String?,
    val motivo: String?,
    val denuncianteId: String?,
    val denunciadoId: String?,
    val administradorId: String?,
    val descricao: String?,
    val dataHora: Date?,
    val status: StatusDenuncia?
)
