package com.example.routepiresfront

import android.os.Bundle
import android.widget.Toast
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnLogin: MaterialButton
    private lateinit var tvForgotPassword: TextView
    private lateinit var tvRegister: TextView
    private lateinit var btnGoogle: ImageView
    private lateinit var btnApple: ImageView
    private lateinit var btnFacebook: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initViews()
        setupListeners()
    }

    private fun initViews() {
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvForgotPassword = findViewById(R.id.tvForgotPassword)
        tvRegister = findViewById(R.id.tvRegister)
        btnGoogle = findViewById(R.id.btnGoogle)
        btnApple = findViewById(R.id.btnApple)
        btnFacebook = findViewById(R.id.btnFacebook)
    }


    private fun setupListeners() {
        btnLogin.setOnClickListener {
            performLogin()
        }

        tvForgotPassword.setOnClickListener {
            // Navegar para tela de recuperação de senha
            Toast.makeText(this, "Recuperar senha", Toast.LENGTH_SHORT).show()
        }

        tvRegister.setOnClickListener {
            // Navegar para tela de registro
            Toast.makeText(this, "Criar conta", Toast.LENGTH_SHORT).show()
        }

        btnGoogle.setOnClickListener {
            loginWithGoogle()
        }

        btnApple.setOnClickListener {
            loginWithApple()
        }

        btnFacebook.setOnClickListener {
            loginWithFacebook()
        }
    }

    private fun performLogin() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        // Validações básicas
        if (email.isEmpty()) {
            etEmail.error = "Digite seu email"
            etEmail.requestFocus()
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = "Email inválido"
            etEmail.requestFocus()
            return
        }

        if (password.isEmpty()) {
            etPassword.error = "Digite sua senha"
            etPassword.requestFocus()
            return
        }

        if (password.length < 6) {
            etPassword.error = "Senha deve ter no mínimo 6 caracteres"
            etPassword.requestFocus()
            return
        }

        //implementar a lógica de autenticação
        Toast.makeText(this, "Fazendo login...", Toast.LENGTH_SHORT).show()
    }

    private fun loginWithGoogle() {
        // Implementar Google Sign-In
        Toast.makeText(this, "Login com Google", Toast.LENGTH_SHORT).show()
    }

    private fun loginWithApple() {
        // Implementar Apple Sign-In
        Toast.makeText(this, "Login com Apple", Toast.LENGTH_SHORT).show()
    }

    private fun loginWithFacebook() {
        // Implementar Facebook Login
        Toast.makeText(this, "Login com Facebook", Toast.LENGTH_SHORT).show()
    }
}