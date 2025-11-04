package com.example.routepiresfront

/**
 * Representa uma conversa de negociacao exibida na lista.
 */
data class Negociacao(
    val nome: String,
    val mensagem: String,
    val quantidadeNaoLida: Int = 0
)
