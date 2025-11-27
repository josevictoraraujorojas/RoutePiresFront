package com.example.routepiresfront.ui.comum

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.routepiresfront.R
import com.example.routepiresfront.ui.mototaxista.CadastroMototaxistaActivity
import com.example.routepiresfront.ui.mototaxista.MenubarMototaxistaActivity
import com.example.routepiresfront.ui.passageiro.CadastroPassageiroActivity
import com.example.routepiresfront.ui.passageiro.MenubarPassageiroActivity
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

    // 🔹 Elementos do popup
    private lateinit var registrationOverlay: View
    private lateinit var registrationScrim: View
    private lateinit var passengerButton: MaterialButton
    private lateinit var mototaxiButton: MaterialButton
    private lateinit var cancelButton: MaterialButton

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

        // 🔹 Inicializando os elementos do popup
        registrationOverlay = findViewById(R.id.registrationOverlay)
        registrationScrim = findViewById(R.id.registrationScrim)
        passengerButton = findViewById(R.id.passengerButton)
        mototaxiButton = findViewById(R.id.mototaxiButton)
        cancelButton = findViewById(R.id.cancelButton)
    }

    private fun setupListeners() {
        btnLogin.setOnClickListener {
            performLogin()
        }

        tvForgotPassword.setOnClickListener {
            Toast.makeText(this, "Recuperar senha", Toast.LENGTH_SHORT).show()
        }

        // 🔹 Mostrar popup ao clicar em "Registre-se agora"
        tvRegister.setOnClickListener {
            registrationOverlay.visibility = View.VISIBLE
        }

        // 🔹 Botão cancelar / voltar
        cancelButton.setOnClickListener {
            registrationOverlay.visibility = View.GONE
        }

        // 🔹 Fechar ao clicar fora (no fundo escuro)
        registrationScrim.setOnClickListener {
            registrationOverlay.visibility = View.GONE
        }

        // 🔹 Clicar em "Quero ser Passageiro"
        passengerButton.setOnClickListener {
            registrationOverlay.visibility = View.GONE
            startActivity(Intent(this, CadastroPassageiroActivity::class.java))
        }

        // 🔹 Clicar em "Quero ser Mototaxista"
        mototaxiButton.setOnClickListener {
            registrationOverlay.visibility = View.GONE
            startActivity(Intent(this, CadastroMototaxistaActivity::class.java))
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
//        val email = etEmail.text.toString().trim()
//        val password = etPassword.text.toString().trim()
//
//        if (email.isEmpty()) {
//            etEmail.error = "Digite seu email"
//            etEmail.requestFocus()
//            return
//        }
//
//        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            etEmail.error = "Email inválido"
//            etEmail.requestFocus()
//            return
//        }
//
//        if (password.isEmpty()) {
//            etPassword.error = "Digite sua senha"
//            etPassword.requestFocus()
//            return
//        }
//
//        if (password.length < 6) {
//            etPassword.error = "Senha deve ter no mínimo 6 caracteres"
//            etPassword.requestFocus()
//            return
//        }
//        startActivity(Intent(this, MenubarPassageiroActivity::class.java))

        startActivity(Intent(this, MenubarMototaxistaActivity::class.java))
    }

    private fun loginWithGoogle() {
        Toast.makeText(this, "Login com Google", Toast.LENGTH_SHORT).show()
    }

    private fun loginWithApple() {
        Toast.makeText(this, "Login com Apple", Toast.LENGTH_SHORT).show()
    }

    private fun loginWithFacebook() {
        Toast.makeText(this, "Login com Facebook", Toast.LENGTH_SHORT).show()
    }
}