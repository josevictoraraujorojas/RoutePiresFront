package com.example.routepiresfront.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.routepiresfront.data.model.LoginDTO
import com.example.routepiresfront.data.model.UsuarioDTOResponse
import com.example.routepiresfront.repository.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _loginResult = MutableLiveData<Result<UsuarioDTOResponse>>()
    val loginResult: LiveData<Result<UsuarioDTOResponse>> = _loginResult

    private val repository = LoginRepository()

    fun performLogin() {
        val emailValue = email.value ?: ""
        val passwordValue = password.value ?: ""

        if (emailValue.isBlank()) {
            // Normalmente, usar LiveData<String> para mensagem de erro
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()) {
            return
        }
        if (passwordValue.isBlank() || passwordValue.length < 6) {
            return
        }

        viewModelScope.launch {
            val dto = LoginDTO(emailValue, passwordValue)
            _loginResult.value = repository.login(dto)
        }
    }
}
