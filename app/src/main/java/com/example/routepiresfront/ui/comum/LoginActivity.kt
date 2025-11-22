package com.example.routepiresfront.ui.comum

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.routepiresfront.R
import com.example.routepiresfront.databinding.ActivityLoginBinding
import com.example.routepiresfront.ui.mototaxista.CadastroMototaxistaActivity
import com.example.routepiresfront.ui.mototaxista.MenubarMototaxistaActivity
import com.example.routepiresfront.ui.passageiro.CadastroPassageiroActivity
import com.example.routepiresfront.ui.passageiro.MenubarPassageiroActivity
import com.example.routepiresfront.viewModel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {
        binding.btnLogin.setOnClickListener {
            viewModel.performLogin()
        }

        binding.tvForgotPassword.setOnClickListener {
            Toast.makeText(this, "Recuperar senha", Toast.LENGTH_SHORT).show()
        }

        binding.tvRegister.setOnClickListener {
            binding.registrationOverlay.visibility = android.view.View.VISIBLE
        }

        binding.cancelButton.setOnClickListener {
            binding.registrationOverlay.visibility = android.view.View.GONE
        }

        binding.registrationScrim.setOnClickListener {
            binding.registrationOverlay.visibility = android.view.View.GONE
        }

        binding.passengerButton.setOnClickListener {
            binding.registrationOverlay.visibility = android.view.View.GONE
            startActivity(Intent(this, CadastroPassageiroActivity::class.java))
        }

        binding.mototaxiButton.setOnClickListener {
            binding.registrationOverlay.visibility = android.view.View.GONE
            startActivity(Intent(this, CadastroMototaxistaActivity::class.java))
        }

        binding.btnGoogle.setOnClickListener {
            Toast.makeText(this, "Login com Google", Toast.LENGTH_SHORT).show()
        }

        binding.btnApple.setOnClickListener {
            Toast.makeText(this, "Login com Apple", Toast.LENGTH_SHORT).show()
        }

        binding.btnFacebook.setOnClickListener {
            Toast.makeText(this, "Login com Facebook", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeViewModel() {
        viewModel.loginResult.observe(this) { result ->
            result.onSuccess { usuario ->
                val intent = if (usuario.tipo == "MOTOTAXISTA") {
                    Intent(this, MenubarMototaxistaActivity::class.java)
                } else {
                    Intent(this, MenubarPassageiroActivity::class.java)
                }
                intent.putExtra("USER_ID", usuario.id)
                startActivity(intent)
                finish()
            }.onFailure {
                Toast.makeText(this, "Erro ao fazer login", Toast.LENGTH_SHORT).show()
            }
        }
    }
}