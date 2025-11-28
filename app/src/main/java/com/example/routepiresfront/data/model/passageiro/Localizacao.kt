package com.example.routepiresfront.data.model.passageiro

import java.util.Date

/**
 * Representa origem/destino/parada de uma corrida.
 */
data class Localizacao(
    val localizacao: GeoPonto? = null,
    val timestamp: Date? = null
)
