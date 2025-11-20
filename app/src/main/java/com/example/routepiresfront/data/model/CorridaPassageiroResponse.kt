package com.example.routepiresfront.data.model

/**
 * REFLETE: br.gov.ifgoiano.routepires.dto.CorridaPassageiroDTOResponse da API
 * FINALIDADE: Capturar o objeto retornado pelo servidor após a criação da corrida,
 * contendo o ID gerado e o status.
 */

data class CorridaPassageiroResponse(
    val id: String,
    val passageiro: String,
    val mototaxistaId: String?,
    val origem: LocalizacaoRequest,
    val destino: LocalizacaoRequest,
    val dataHoraSolicitacao: Long,
    val status: String
)