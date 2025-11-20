package com.example.routepiresfront.data.model

/**
 * REFLETE: br.gov.ifgoiano.routepires.dto.LocalizacaoDTOCreate da API
 * FINALIDADE: Representa um ponto de partida, destino ou parada intermediária.
 * O campo 'timestamp' (Date na API Java) é mapeado para Long (timestamp em ms) no Kotlin.
 */
data class LocalizacaoRequest(
    val localizacao: GeoPoint,
    val timestamp: Long? = null
)
