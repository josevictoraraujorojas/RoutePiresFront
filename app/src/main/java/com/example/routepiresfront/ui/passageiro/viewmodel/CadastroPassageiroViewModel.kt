package com.example.routepiresfront.ui.passageiro.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.routepiresfront.core.Resultado
import com.example.routepiresfront.data.model.passageiro.PassageiroCadastroRequest
import com.example.routepiresfront.data.model.passageiro.PassageiroResponse
import com.example.routepiresfront.data.repository.passageiro.PassageiroRepository
import kotlinx.coroutines.launch

/**
 * ViewModel dedicada ao fluxo de cadastro do passageiro.
 * Mantém o estado de forma reativa para a Activity observar.
 */
class CadastroPassageiroViewModel(
    private val repository: PassageiroRepository = PassageiroRepository()
) : ViewModel() {

    private val _estadoCadastro = MutableLiveData<Resultado<PassageiroResponse>>()
    val estadoCadastro: LiveData<Resultado<PassageiroResponse>> = _estadoCadastro

    fun cadastrar(passageiro: PassageiroCadastroRequest) {
        viewModelScope.launch {
            _estadoCadastro.value = Resultado.Carregando
            _estadoCadastro.value = repository.cadastrarPassageiro(passageiro)
        }
    }
}
