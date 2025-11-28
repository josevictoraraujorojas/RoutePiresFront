package com.example.routepiresfront.core

/**
 * Wrapper simples para comunicar estados das requisições na camada de UI.
 * Mantém tudo em pt-BR para ficar alinhado com o restante do projeto.
 */
sealed class Resultado<out T> {
    data class Sucesso<T>(val dado: T) : Resultado<T>()
    data class Erro(val mensagem: String, val excecao: Throwable? = null) : Resultado<Nothing>()
    data object Carregando : Resultado<Nothing>()
}
