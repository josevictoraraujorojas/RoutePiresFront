package com.example.routepiresfront.chat

data class Mensagem(
    val texto: String,
    val remetenteId: String, // ID único de quem enviou
    val timestamp: Long // Para ordenar as mensagens
)