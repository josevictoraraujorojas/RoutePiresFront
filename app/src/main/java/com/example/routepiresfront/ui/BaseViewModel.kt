package com.example.routepiresfront.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel base que contém lógica comum para tratamento de estados de UI,
 * como carregamento (loading) e mensagens de erro.
 */
open class BaseViewModel : ViewModel() {

    // LiveData para controlar a exibição de um indicador de progresso (ex: ProgressBar)
    protected val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // LiveData para exibir mensagens de erro (ex: em um Toast ou Snackbar)
    protected val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    /**
     * Função para ser chamada quando um erro for exibido na UI.
     * Isso evita que o erro seja mostrado novamente (ex: após uma rotação de tela).
     */
    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}
