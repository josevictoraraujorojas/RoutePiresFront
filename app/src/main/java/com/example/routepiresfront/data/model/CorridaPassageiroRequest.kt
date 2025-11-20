package com.example.routepiresfront.data.model

/**
 * REFLETE: br.gov.ifgoiano.routepires.dto.CorridaPassageiroDTOCreate da API
 * FINALIDADE: Estrutura os dados para enviar a requisição de criação de corrida (POST /corridas-passageiro).
 *
 * NOTA: O campo 'dataHoraSolicitacao' (Date na API Java) é representado por Long (timestamp em ms) no Kotlin.
 * O campo 'status' deve ser uma String que corresponde ao Enum Status da API (ex: "PENDENTE").
 */

data class CorridaPassageiroRequest(
    // ID do passageiro que está criando a corrida (obrigatório)
    val passageiroId: String,

    // ID do mototaxista que aceitará a corrida (pode ser nulo inicialmente)
    val mototaxistaId: String? = null,

    val origem: LocalizacaoRequest,
    val destino: LocalizacaoRequest,

    // Lista de paradas intermediárias (opcional)
    val paradasIds: List<LocalizacaoRequest>? = null,

    val dataHoraSolicitacao: Long,
    val status: String,
    val motivoCancelamento: String? = null
)