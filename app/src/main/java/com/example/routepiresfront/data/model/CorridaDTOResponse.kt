package com.example.routepiresfront.data.model

import com.google.gson.annotations.SerializedName

data class CorridaDTOResponse(
    @SerializedName("id") val id: String? = null,
    @SerializedName("passageiroId") val passageiroId: String? = null,
    @SerializedName("mototaxistaId") val mototaxistaId: String? = null,
    @SerializedName("dataInicio") val dataInicio: String? = null,
    @SerializedName("dataFim") val dataFim: String? = null,
    @SerializedName("dataHoraFim") val dataHoraFim: String? = null, // Novo campo conforme o banco: dataHoraFim (padrão usado no backend)
    @SerializedName("dataHoraSolicitacao") val dataHoraSolicitacao: String? = null,
    @SerializedName("localPartida") val localPartida: String? = null,
    @SerializedName("localDestino") val localDestino: String? = null,
    @SerializedName("valor") val valor: Float? = null,
    @SerializedName("status") val status: String? = null // "ATIVA", "FINALIZADA", "CANCELADA"
)
