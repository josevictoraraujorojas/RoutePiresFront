package com.example.routepiresfront.data.model.dto

import com.example.routepiresfront.data.model.Localizacao
import com.google.gson.annotations.SerializedName


data class CorridaPassageiroCreateDTO(
    @SerializedName("passageiroId")
    val passageiroId: String,

    @SerializedName("mototaxistaId")
    val mototaxistaId: String? = null,

    @SerializedName("pontoPartida")
    val pontoPartida: Localizacao,

    @SerializedName("pontoDestino")
    val pontoDestino: Localizacao,

    @SerializedName("pontosIntermediarios")
    val pontosIntermediarios: List<Localizacao>? = null,

    @SerializedName("valorEstimado")
    val valorEstimado: Double? = null,

    @SerializedName("distanciaKm")
    val distanciaKm: Double? = null,

    @SerializedName("tempoEstimadoMin")
    val tempoEstimadoMin: Int? = null
)

data class CorridaPassageiroUpdateDTO(
    @SerializedName("mototaxistaId")
    val mototaxistaId: String? = null,

    @SerializedName("status")
    val status: String? = null,

    @SerializedName("pontoPartida")
    val pontoPartida: Localizacao? = null,

    @SerializedName("pontoDestino")
    val pontoDestino: Localizacao? = null,

    @SerializedName("valorEstimado")
    val valorEstimado: Double? = null,

    @SerializedName("distanciaKm")
    val distanciaKm: Double? = null,

    @SerializedName("tempoEstimadoMin")
    val tempoEstimadoMin: Int? = null,

    @SerializedName("dataHoraInicio")
    val dataHoraInicio: String? = null,

    @SerializedName("dataHoraFim")
    val dataHoraFim: String? = null,

    @SerializedName("motivoCancelamento")
    val motivoCancelamento: String? = null
)


data class CorridaFreteCreateDTO(
    @SerializedName("solicitanteId")
    val solicitanteId: String,

    @SerializedName("mototaxistaId")
    val mototaxistaId: String? = null,

    @SerializedName("pontoPartida")
    val pontoPartida: Localizacao,

    @SerializedName("pontoDestino")
    val pontoDestino: Localizacao,

    @SerializedName("pontosIntermediarios")
    val pontosIntermediarios: List<Localizacao>? = null,

    @SerializedName("valorEstimado")
    val valorEstimado: Double? = null,

    @SerializedName("distanciaKm")
    val distanciaKm: Double? = null,

    @SerializedName("tempoEstimadoMin")
    val tempoEstimadoMin: Int? = null,

    @SerializedName("descricaoEntrega")
    val descricaoEntrega: String? = null,

    @SerializedName("pesoKg")
    val pesoKg: Int? = null,

    @SerializedName("fragil")
    val fragil: Boolean? = null
)


data class CorridaFreteUpdateDTO(
    @SerializedName("mototaxistaId")
    val mototaxistaId: String? = null,

    @SerializedName("status")
    val status: String? = null,

    @SerializedName("pontoPartida")
    val pontoPartida: Localizacao? = null,

    @SerializedName("pontoDestino")
    val pontoDestino: Localizacao? = null,

    @SerializedName("valorEstimado")
    val valorEstimado: Double? = null,

    @SerializedName("distanciaKm")
    val distanciaKm: Double? = null,

    @SerializedName("tempoEstimadoMin")
    val tempoEstimadoMin: Int? = null,

    @SerializedName("descricaoEntrega")
    val descricaoEntrega: String? = null,

    @SerializedName("pesoKg")
    val pesoKg: Int? = null,

    @SerializedName("fragil")
    val fragil: Boolean? = null,

    @SerializedName("dataHoraInicio")
    val dataHoraInicio: String? = null,

    @SerializedName("dataHoraFim")
    val dataHoraFim: String? = null,

    @SerializedName("motivoCancelamento")
    val motivoCancelamento: String? = null
)
