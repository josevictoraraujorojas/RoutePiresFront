package com.example.routepiresfront.data.model.passageiro

import java.util.Date

/**
 * Resposta de criação/consulta de corridas do passageiro.
 */
data class CorridaPassageiroResponse(
    val id: String? = null,
    val mototaxistaId: String? = null,
    val origem: Localizacao? = null,
    val destino: Localizacao? = null,
    val paradas: List<Localizacao>? = null,
    val dataHoraSolicitacao: Date? = null,
    val dataHoraFim: Date? = null,
    val status: String? = null,
    val avaliacaoPassageiroId: String? = null,
    val avaliacaoMototaxistaId: String? = null,
    val chatId: String? = null,
    val motivoCancelamento: String? = null,
    val passageiro: String? = null
)
