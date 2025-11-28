package com.example.routepiresfront.data.model.passageiro

import java.util.Date

/**
 * Corpo para criar/atualizar uma corrida de passageiro.
 * Ajuda a manter o fluxo de Seleção de local/Cadastro coeso.
 */
data class CorridaPassageiroRequest(
    val passageiroId: String? = null,
    val mototaxistaId: String? = null,
    val origem: Localizacao? = null,
    val destino: Localizacao? = null,
    val paradasIds: List<Localizacao>? = null,
    val dataHoraSolicitacao: Date? = null,
    val status: String? = null,
    val motivoCancelamento: String? = null
)
