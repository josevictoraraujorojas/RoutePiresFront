package com.example.routepiresfront.data.model.mapper

import com.example.routepiresfront.data.model.Corrida
import com.example.routepiresfront.data.model.dto.CorridaPassageiroCreateDTO
import com.example.routepiresfront.data.model.dto.CorridaPassageiroUpdateDTO
import com.example.routepiresfront.data.model.dto.CorridaFreteCreateDTO
import com.example.routepiresfront.data.model.dto.CorridaFreteUpdateDTO


object CorridaMapper {

    fun toPassageiroCreateDTO(corrida: Corrida): CorridaPassageiroCreateDTO {
        require(corrida.passageiro?.id != null) {
            "Passageiro ID é obrigatório para criar corrida"
        }
        require(corrida.pontoPartida != null) {
            "Ponto de partida é obrigatório"
        }
        require(corrida.pontoDestino != null) {
            "Ponto de destino é obrigatório"
        }

        return CorridaPassageiroCreateDTO(
            passageiroId = corrida.passageiro.id,
            mototaxistaId = corrida.mototaxista?.id,
            pontoPartida = corrida.pontoPartida,
            pontoDestino = corrida.pontoDestino,
            pontosIntermediarios = corrida.pontosIntermediarios,
            valorEstimado = corrida.valorEstimado,
            distanciaKm = corrida.distanciaKm,
            tempoEstimadoMin = corrida.tempoEstimadoMin
        )
    }

    fun toPassageiroUpdateDTO(corrida: Corrida): CorridaPassageiroUpdateDTO {
        return CorridaPassageiroUpdateDTO(
            mototaxistaId = corrida.mototaxista?.id,
            status = corrida.status.name,
            pontoPartida = corrida.pontoPartida,
            pontoDestino = corrida.pontoDestino,
            valorEstimado = corrida.valorEstimado,
            distanciaKm = corrida.distanciaKm,
            tempoEstimadoMin = corrida.tempoEstimadoMin,
            dataHoraInicio = corrida.dataHoraInicio,
            dataHoraFim = corrida.dataHoraFim
        )
    }


    fun toFreteCreateDTO(corrida: Corrida): CorridaFreteCreateDTO {
        require(corrida.passageiro?.id != null) {
            "Solicitante ID é obrigatório para criar frete"
        }
        require(corrida.pontoPartida != null) {
            "Ponto de partida é obrigatório"
        }
        require(corrida.pontoDestino != null) {
            "Ponto de destino é obrigatório"
        }

        return CorridaFreteCreateDTO(
            solicitanteId = corrida.passageiro.id,
            mototaxistaId = corrida.mototaxista?.id,
            pontoPartida = corrida.pontoPartida,
            pontoDestino = corrida.pontoDestino,
            pontosIntermediarios = corrida.pontosIntermediarios,
            valorEstimado = corrida.valorEstimado,
            distanciaKm = corrida.distanciaKm,
            tempoEstimadoMin = corrida.tempoEstimadoMin,
            descricaoEntrega = corrida.descricaoEntrega,
            pesoKg = corrida.pesoKg,
            fragil = corrida.fragil
        )
    }


    fun toFreteUpdateDTO(corrida: Corrida): CorridaFreteUpdateDTO {
        return CorridaFreteUpdateDTO(
            mototaxistaId = corrida.mototaxista?.id,
            status = corrida.status.name,
            pontoPartida = corrida.pontoPartida,
            pontoDestino = corrida.pontoDestino,
            valorEstimado = corrida.valorEstimado,
            distanciaKm = corrida.distanciaKm,
            tempoEstimadoMin = corrida.tempoEstimadoMin,
            descricaoEntrega = corrida.descricaoEntrega,
            pesoKg = corrida.pesoKg,
            fragil = corrida.fragil,
            dataHoraInicio = corrida.dataHoraInicio,
            dataHoraFim = corrida.dataHoraFim
        )
    }

    fun toAceitarCorridaDTO(mototaxistaId: String): CorridaPassageiroUpdateDTO {
        return CorridaPassageiroUpdateDTO(
            mototaxistaId = mototaxistaId,
            status = "ACEITA"
        )
    }

    fun toIniciarCorridaDTO(dataHoraInicio: String): CorridaPassageiroUpdateDTO {
        return CorridaPassageiroUpdateDTO(
            status = "EM_ANDAMENTO",
            dataHoraInicio = dataHoraInicio
        )
    }

    fun toFinalizarCorridaDTO(dataHoraFim: String): CorridaPassageiroUpdateDTO {
        return CorridaPassageiroUpdateDTO(
            status = "FINALIZADA",
            dataHoraFim = dataHoraFim
        )
    }

    fun toCancelarCorridaDTO(motivo: String?): CorridaPassageiroUpdateDTO {
        return CorridaPassageiroUpdateDTO(
            status = "CANCELADA",
            motivoCancelamento = motivo
        )
    }
}
