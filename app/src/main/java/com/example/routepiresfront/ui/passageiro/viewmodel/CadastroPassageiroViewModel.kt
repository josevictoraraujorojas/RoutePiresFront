package com.example.routepiresfront.ui.passageiro.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.util.Patterns
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

    // Estado do formulário exposto ao Data Binding
    val nome = MutableLiveData("")
    val email = MutableLiveData("")
    val telefone = MutableLiveData("")
    val senha = MutableLiveData("")
    val confirmarSenha = MutableLiveData("")
    val aceitouTermos = MutableLiveData(false)
    val carregando = MutableLiveData(false)

    private val _mensagem = MutableLiveData<String?>()
    val mensagem: LiveData<String?> = _mensagem

    private val _estadoCadastro = MutableLiveData<Resultado<PassageiroResponse>>()
    val estadoCadastro: LiveData<Resultado<PassageiroResponse>> = _estadoCadastro
    val botaoHabilitado: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        value = false
        fun atualizar() {
            val camposOk = nome.value.orEmpty().isNotBlank() &&
                    email.value.orEmpty().isNotBlank() &&
                    telefone.value.orEmpty().isNotBlank() &&
                    senha.value.orEmpty().isNotBlank() &&
                    confirmarSenha.value.orEmpty().isNotBlank()
            val termosOk = aceitouTermos.value == true
            val carregandoAgora = carregando.value == true
            value = camposOk && termosOk && !carregandoAgora
        }
        addSource(nome) { atualizar() }
        addSource(email) { atualizar() }
        addSource(telefone) { atualizar() }
        addSource(senha) { atualizar() }
        addSource(confirmarSenha) { atualizar() }
        addSource(aceitouTermos) { atualizar() }
        addSource(carregando) { atualizar() }
    }

    /**
     * Acionado pelo botão (ver binding no XML). Valida e chama o repositório via corrotina.
     */
    fun onClickCadastrar() {
        val nomeVal = nome.value.orEmpty().trim()
        val emailVal = email.value.orEmpty().trim()
        val telefoneVal = telefone.value.orEmpty().trim()
        val senhaVal = senha.value.orEmpty()
        val confirmarSenhaVal = confirmarSenha.value.orEmpty()

        when {
            nomeVal.isBlank() || emailVal.isBlank() || telefoneVal.isBlank() ||
                    senhaVal.isBlank() || confirmarSenhaVal.isBlank() -> {
                _mensagem.value = "Preencha todos os campos"
                return
            }

            senhaVal != confirmarSenhaVal -> {
                _mensagem.value = "As senhas não coincidem"
                return
            }

            senhaVal.length < 8 || !senhaVal.any { it.isDigit() } || !senhaVal.any { it.isLetter() } -> {
                _mensagem.value = "A senha deve ter pelo menos 8 caracteres, com letras e números"
                return
            }

            !Patterns.EMAIL_ADDRESS.matcher(emailVal).matches() -> {
                _mensagem.value = "Informe um e-mail válido"
                return
            }

            telefoneVal.any { !it.isDigit() } || telefoneVal.length !in 10..15 -> {
                _mensagem.value = "Telefone deve ter apenas números (10 a 15 dígitos)"
                return
            }

            aceitouTermos.value != true -> {
                _mensagem.value = "Você deve aceitar os termos de uso"
                return
            }
        }

        val passageiro = PassageiroCadastroRequest(
            nome = nomeVal,
            email = emailVal,
            telefone = telefoneVal,
            senha = senhaVal
        )
        cadastrar(passageiro)
    }

    private fun cadastrar(passageiro: PassageiroCadastroRequest) {
        viewModelScope.launch {
            carregando.value = true
            _estadoCadastro.value = Resultado.Carregando
            val resultado = repository.cadastrarPassageiro(passageiro)
            _estadoCadastro.value = resultado

            when (resultado) {
                is Resultado.Sucesso -> _mensagem.value = "Cadastro concluído com sucesso!"
                is Resultado.Erro -> _mensagem.value = resultado.mensagem
                Resultado.Carregando -> {} // já tratado
            }
            carregando.value = false
        }
    }

    fun limparMensagem() {
        _mensagem.value = null
    }
}
